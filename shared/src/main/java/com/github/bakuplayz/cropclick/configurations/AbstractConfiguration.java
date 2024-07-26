/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.configurations;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.common.StringUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Set;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.*;


/**
 * A class representing a YAML file.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */

public abstract class AbstractConfiguration implements Configuration, LoggerContext {

    protected final CropClick plugin;

    protected final String fileName;

    @Setter(AccessLevel.PRIVATE)
    private File file;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private FileConfiguration configuration;


    public AbstractConfiguration(@NotNull CropClick plugin, @NotNull String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NotNull IConfigurationKey key, @NotNull String... args) {
        String path = StringUtils.replace(key.getPath(), "%s", args);
        return (T) getConfiguration().get(path, key.getDefaultValue());
    }


    @NotNull
    @Override
    public Set<String> getKeys(@NotNull IConfigurationKey key, @NotNull String... args) {
        String path = StringUtils.replace(key.getPath(), "%s", args);
        ConfigurationSection section = getConfiguration().getConfigurationSection(path);
        return section == null ? Collections.emptySet() : section.getKeys(true);
    }


    @Override
    public <T> void set(@NotNull IConfigurationKey key, T data, @NotNull String... args) {
        setWithoutSave(key, data, args);
        save();
    }


    @Override
    public <T> void setWithoutSave(@NotNull IConfigurationKey key, T data, @NotNull String... args) {
        String path = StringUtils.replace(key.getPath(), "%s", args);
        getConfiguration().set(path, data);
    }


    /**
     * Creates the configuration, iff not present.
     */
    @Override
    public void create() {
        setFile(getNewFileInstance());
        setConfiguration(YamlConfiguration.loadConfiguration(file));
        
        try {
            if (file.createNewFile()) {
                plugin.saveResource(fileName, true);
            }
        } catch (IOException exception) {
            FILE_SETUP_FAILED.send(plugin.getLogger(), fileName, exception);
        } finally {
            FILE_SETUP_LOAD.send(plugin.getLogger(), fileName);
        }
    }


    /**
     * Reloads the configuration.
     */
    @Override
    public void reload() {
        setFile(getNewFileInstance());
        setConfiguration(YamlConfiguration.loadConfiguration(file));
        FILE_RELOAD.send(getLogger(), fileName);
    }


    /**
     * Saves the configuration to it's dedicated file.
     */
    @Override
    public void save() {
        try {
            getConfiguration().save(file);
        } catch (IOException exception) {
            FILE_SAVE_FAILED.send(getLogger(), fileName, exception);
        }
    }


    /**
     * Resets the configuration.
     */
    @Override
    public void reset() {
        try {
            Files.deleteIfExists(file.toPath());
            create();
        } catch (IOException exception) {
            FILE_RESET_FAILED.send(getLogger(), fileName, exception);
        }
    }


    @NotNull
    private File getNewFileInstance() {
        return new File(plugin.getDataFolder(), fileName);
    }

}