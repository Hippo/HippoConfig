/*
 * MIT License
 *
 * Copyright (c) 2020 Hippo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rip.hippo.config.adapter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import rip.hippo.config.map.Mappable;
import rip.hippo.config.serialization.manage.TypeSerializationManager;
import rip.hippo.config.unsafe.Unsafe;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Hippo
 * @version 1.0.2, 9/8/20
 * @since 1.0.0
 */
public final class ConfigAdapter {

    private final File configFile;
    private final TypeSerializationManager typeSerializationManager;
    private FileConfiguration fileConfiguration;
    private String header;
    private Mappable mappable;

    public ConfigAdapter(File configFile, TypeSerializationManager typeSerializationManager) {
        this.configFile = configFile;
        this.typeSerializationManager = typeSerializationManager;
    }

    public ConfigAdapter map(Mappable mappable) {
        this.mappable = mappable;
        return this;
    }

    public ConfigAdapter map(Class<? extends Mappable> mappableClass) {
        try {
            this.mappable = (Mappable) Unsafe.getUnsafe().allocateInstance(mappableClass);

            List<Field> fields = new LinkedList<>();
            Class<?> current = mappable.getClass();
            while (Mappable.class.isAssignableFrom(current)) {
                fields.addAll(Arrays.asList(current.getDeclaredFields()));
                current = current.getSuperclass();
            }

            for (Field field : fields) {
                if (!field.getType().isPrimitive()) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.set(Modifier.isStatic(field.getModifiers()) ? null : mappable, Unsafe.getUnsafe().allocateInstance(field.getType()));
                }
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ConfigAdapter header(String header) {
        this.header = header;
        return this;
    }

    public ConfigAdapter update() {
        if (mappable == null) {
            throw new IllegalStateException(String.format("Config adapter for file %s does not have a mappable object.", configFile.getAbsolutePath()));
        }
        if (ensureFileConfiguration()) {
            typeSerializationManager.getDeserializer(Mappable.class).deserialize(fileConfiguration, "", mappable);
        }
        return this;
    }

    public void save() {
        serialize();
        fileConfiguration.options().header(header);
        try {
            fileConfiguration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serialize() {
        if (mappable == null) {
            throw new IllegalStateException(String.format("Config adapter for file %s does not have a mappable object.", configFile.getAbsolutePath()));
        }
        ensureFileConfiguration();
        typeSerializationManager.getSerializer(Mappable.class).serialize(fileConfiguration, "", mappable);
    }

    private boolean ensureFileConfiguration() {
        try {
            File parentFile = configFile.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                throw new SecurityException(String.format("Unable to make directory %s (no permission?)", parentFile.getAbsolutePath()));
            }
            boolean exists = configFile.exists();
            if ((!exists && configFile.createNewFile()) || fileConfiguration == null) {
                fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
                return exists;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Mappable getMappable() {
        if (mappable == null) {
            throw new IllegalStateException(String.format("Config adapter for file %s does not have a mappable object.", configFile.getAbsolutePath()));
        }
        return mappable;
    }

    public <T extends Mappable> T getMappable(Class<T> actual) {
        return actual.cast(getMappable());
    }
}
