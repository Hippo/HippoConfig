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

package rip.hippo.config;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.config.version.SemanticVersion;

/**
 * @author Hippo
 * @version 1.0.0, 9/8/20
 * @since 1.0.0
 */
public final class HippoConfigPlugin extends JavaPlugin {

    private static final SemanticVersion PLUGIN_VERSION = SemanticVersion
            .builder()
            .major(1)
            .minor(4)
            .build();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(String.format("Running Hippo Config version %s", PLUGIN_VERSION));
        super.onEnable();
    }
}
