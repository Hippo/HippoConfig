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

package rip.hippo.api.config.adapter.pool.impl;

import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.api.config.map.Mappable;
import rip.hippo.api.config.adapter.ConfigAdapter;
import rip.hippo.api.config.adapter.pool.ConfigAdapterPool;
import rip.hippo.api.config.serialization.manage.TypeSerializationManager;
import rip.hippo.api.config.serialization.manage.impl.StandardTypeSerializationManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hippo
 * @version 1.0.0, 9/9/20
 * @since 1.0.0
 */
public final class StandardConfigAdapterPool implements ConfigAdapterPool {

    private final File parentDirectory;
    private final TypeSerializationManager typeSerializationManager;
    private final Map<String, ConfigAdapter> configAdapterPool;

    public StandardConfigAdapterPool(JavaPlugin plugin) {
        this(plugin.getDataFolder());
    }

    public StandardConfigAdapterPool(File parentDirectory) {
        this(parentDirectory, new StandardTypeSerializationManager());
    }

    public StandardConfigAdapterPool(File parentDirectory, TypeSerializationManager typeSerializationManager) {
        if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
            throw new SecurityException(String.format("Unable to make directory %s (no permission?)", parentDirectory.getAbsolutePath()));
        }
        this.parentDirectory = parentDirectory;
        this.typeSerializationManager = typeSerializationManager;
        this.configAdapterPool = new HashMap<>();
    }

    @Override
    public void registerMappable(Class<? extends Mappable> mappableClass) {
        typeSerializationManager.registerMappable(mappableClass);
    }

    @Override
    public ConfigAdapter getAdapter(String... adapter) {
        String path = transformPath(adapter);
        return configAdapterPool.computeIfAbsent(path, ignored -> new ConfigAdapter(new File(parentDirectory, path), typeSerializationManager));
    }

    @Override
    public Mappable getMappedConfig(String... config) {
        return getAdapter(config).getMappable();
    }

    private String transformPath(String... path) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String segment : path) {
            stringBuilder.append(segment).append(File.separator);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        String builtPath = stringBuilder.toString();

        return builtPath + (builtPath.toLowerCase().endsWith(".yml") ? "" : ".yml");
    }
}
