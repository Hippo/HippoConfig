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

package rip.hippo.api.config.serialization.impl.serializers;

import org.bukkit.configuration.file.FileConfiguration;
import rip.hippo.api.config.annotation.SerializedKey;
import rip.hippo.api.config.map.Mappable;
import rip.hippo.api.config.serialization.ConfigSerializer;
import rip.hippo.api.config.serialization.manage.TypeSerializationManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Hippo
 * @version 1.0.0, 9/8/20
 * @since 1.0.0
 */
public final class MappableConfigSerializer implements ConfigSerializer {

    private final TypeSerializationManager typeSerializationManager;

    public MappableConfigSerializer(TypeSerializationManager typeSerializationManager) {
        this.typeSerializationManager = typeSerializationManager;
    }

    @Override
    public void serialize(FileConfiguration fileConfiguration, String key, Object value) {
        List<Field> fields = new LinkedList<>();
        Class<?> current = value.getClass();
        while (Mappable.class.isAssignableFrom(current)) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }

        for (Field field : fields) {
            try {
                if (Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                boolean accessible = field.isAccessible();
                field.setAccessible(true);


                SerializedKey serializedKey = field.getAnnotation(SerializedKey.class);
                String keyField = serializedKey == null ? field.getName() : serializedKey.value();
                ConfigSerializer serializer = typeSerializationManager.getSerializer(field.getType());
                serializer.serialize(fileConfiguration, (key.isEmpty() ? "" : key + ".") + keyField, field.get(value));

                field.setAccessible(accessible);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }
}
