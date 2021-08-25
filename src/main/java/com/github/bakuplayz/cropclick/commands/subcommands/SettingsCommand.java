package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.menu.menus.SettingsMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class SettingsCommand extends SubCommand {

    public SettingsCommand(@NotNull CropClick plugin) {
        super(plugin, "settings");
    }

    @Override
    public void perform(Player player, String[] args) {
        new SettingsMenu(plugin, player).open();
    }
}
