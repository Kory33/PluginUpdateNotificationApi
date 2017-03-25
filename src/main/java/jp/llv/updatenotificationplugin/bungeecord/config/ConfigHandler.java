/*
 * The MIT License
 *
 * Copyright 2016 toyblocks.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.llv.updatenotificationplugin.bungeecord.config;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import jp.llv.updatenotificationplugin.bungeecord.UpdateNotificationPlugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * Bungeecord-configuration handler
 *
 * @author Kory33, Kory33
 *
 */
public class ConfigHandler {
    private static final String UPDATE_CHECK_ENABLED_PATH = "enableUpdateCheck";
    private static final String LOG_UP_TO_DATE_PATH = "logUpToDate";
    private static final String LOG_UPDATES_TO_SERVER = "logUpdatesToServer";
    private static final String UPDATE_CHECK_FREQUENT_PATH = "updateCheckFrequent";
    private static final String LOG_UPDATES_TO_NON_OP = "logUpdatesToNonOp";

    /**
     * Path to the configuration file
     */
    private final Configuration fConfiguration;

    public ConfigHandler(UpdateNotificationPlugin plugin, String configPath) {
        plugin.getDataFolder().mkdirs();
        File file = new File(plugin.getDataFolder(), configPath);
        try (InputStream is = plugin.getResourceAsStream(configPath);
                OutputStream os = new FileOutputStream(file)) {
            file.createNewFile();
            ByteStreams.copy(is, os);
            this.fConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public boolean isUpdateCheckEnabled() {
        return this.fConfiguration.getBoolean(UPDATE_CHECK_ENABLED_PATH, false);
    }
    
    /**
     * Return true, in reference to the configuration file, if the plugin should
     * log up-to-date status
     *
     * @return value of {@value #LOG_UP_TO_DATE_PATH} in the config file
     */
    public boolean shouldLogUpToDate() {
        return this.fConfiguration.getBoolean(LOG_UP_TO_DATE_PATH, false);
    }

    /**
     * Return true, in reference to the configuration file, if the plugin should
     * check for update frequently.
     *
     * @return value of {@value #UPDATE_CHECK_FREQUENT_PATH} in the config file
     */
    public boolean isUpdateCheckFrequent() {
        return this.fConfiguration.getBoolean(UPDATE_CHECK_FREQUENT_PATH, false);
    }

    /**
     * Return true, in reference to the configuration file, if the plugin should
     * log updates to the server console
     *
     * @return value of {@value #UPDATE_CHECK_FREQUENT_PATH} in the config file
     */
    public boolean shouldLogToServer() {
        return this.fConfiguration.getBoolean(LOG_UPDATES_TO_SERVER, true);
    }

    /**
     * Return true, in reference to the configuration file, if the plugin should
     * log updates to non-op players
     *
     * @return value of {@value #LOG_UPDATES_TO_NON_OP} in the config file
     */
    public boolean shouldLogToNonOp() {
        return this.fConfiguration.getBoolean(LOG_UPDATES_TO_NON_OP, true);
    }
}
