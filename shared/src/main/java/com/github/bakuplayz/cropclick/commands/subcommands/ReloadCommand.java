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

package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Command.*;
import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.FILE_RELOAD;


/**
 * A class representing the '/crop reload' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@AllArgsConstructor
public final class ReloadCommand implements Subcommand, LoggerContext {

    private final CropClick plugin;


    @NotNull
    @Override
    public String getName() {
        return "reload";
    }


    @NotNull
    @Override
    public String getDescription() {
        return RELOAD_DESCRIPTION.get(plugin);
    }


    /**
     * Performs the '/crop reload' command, reloading the plugin.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        try {
            plugin.reloadConfig();
            FILE_RELOAD.send(plugin.getLogger(), "config.yml");

            plugin.getCropsConfig().reload();
            plugin.getAddonsConfig().reload();
            plugin.getPlayersConfig().reload();
            plugin.getLanguageConfig().reload();
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
            RELOAD_FAILED.send(plugin, player);
        } finally {
            RELOAD_SUCCESS.send(plugin, player);
        }
    }

}