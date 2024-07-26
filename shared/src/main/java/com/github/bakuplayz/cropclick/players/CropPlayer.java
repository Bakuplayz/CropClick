/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.players;

import com.cryptomorin.xseries.XMaterial;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.common.BlockUtils;
import com.github.bakuplayz.cropclick.common.CollectionUtils;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig.ConfigurationKey;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class CropPlayer {

    private final String playerId;

    private final PlayersConfig config;


    public CropPlayer(@NotNull Player player) {
        this.config = CropClick.getInstance().getPlayersConfig();
        this.playerId = player.getUniqueId().toString();
    }


    public CropPlayer(@NotNull String playerId) {
        this.config = CropClick.getInstance().getPlayersConfig();
        this.playerId = playerId;
    }


    /**
     * Toggles the plugin for this player.
     */
    public void togglePlugin() {
        config.set(ConfigurationKey.DISABLED_PLAYERS,
                CollectionUtils.toggleItem(config.getDisabledPlayers(), playerId)
        );
    }


    /**
     * Checks whether the provided player id is able to use {@link CropClick}.
     *
     * @return true if able, otherwise false.
     */
    public boolean isPluginEnabled() {
        return !config.getDisabledPlayers().contains(playerId);
    }


    /**
     * Checks whether the {@link Block provided crop block} is selected by the player.
     *
     * @param block the crop block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isCropSelected(@NotNull Block block) {
        if (CropClick.getInstance().getCropManager().isCrop(block)) {
            Location crop = config.getSelectedCrop(playerId);
            return crop != null && crop.equals(block.getLocation());
        }
        return false;
    }


    /**
     * Checks whether the {@link Block provided container block} is selected by the player.
     *
     * @param block the container block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isContainerSelected(@NotNull Block block) {
        if (BlockUtils.isAnyType(block, ContainerComponent.getTypes())) {
            Location container = config.getSelectedContainer(playerId);
            return container != null && container.equals(block.getLocation());
        }
        return false;
    }


    /**
     * Checks whether the {@link Block provided dispenser block} is selected by the player.
     *
     * @param block the dispenser block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isDispenserSelected(@NotNull Block block) {
        if (BlockUtils.isSameType(block, XMaterial.DISPENSER)) {
            Location dispenser = config.getSelectedDispenser(playerId);
            return dispenser != null && dispenser.equals(block.getLocation());
        }
        return false;
    }


}
