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
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.addons.auraskills.AuraSkillsAddon;
import com.github.bakuplayz.cropclick.addons.jobsreborn.JobsRebornAddon;
import com.github.bakuplayz.cropclick.addons.mcmmo.MCMMOAddon;
import com.github.bakuplayz.cropclick.addons.residence.ResidenceAddon;
import com.github.bakuplayz.cropclick.addons.towny.TownyAddon;
import com.github.bakuplayz.cropclick.addons.worldguard.WorldGuardAddon;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.common.BlockUtils;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A class managing the {@link Player player} interactions, with
 * different {@link AbstractAddon addons} and {@link Autofarm autofarms}.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public class PlayerInteractionManager {

    private final CropClick plugin;

    private final AddonManager addonManager;

    private final PlayersConfig playersConfig;


    public PlayerInteractionManager(@NotNull CropClick plugin) {
        this.playersConfig = plugin.getPlayersConfig();
        this.addonManager = plugin.getAddonManager();
        this.plugin = plugin;
    }


    /**
     * Checks if the {@link Player provided player} is allowed to modify the current region.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canModifyRegion(@NotNull Player player) {
        TownyAddon townyAddon = addonManager.getTownyAddon();
        ResidenceAddon residenceAddon = addonManager.getResidenceAddon();
        WorldGuardAddon worldGuardAddon = addonManager.getWorldGuardAddon();

        if (addonManager.isInstalledAndEnabled(townyAddon)) {
            return townyAddon.getFunctionality().canDestroyCrop(player);
        }

        if (addonManager.isInstalledAndEnabled(residenceAddon)) {
            boolean isRegionMember = residenceAddon.getFunctionality().isMemberOfRegion(player);
            boolean hasRegionFlag = residenceAddon.getFunctionality().hasRegionFlag(player.getLocation());
            return isRegionMember || hasRegionFlag;
        }

        if (addonManager.isInstalledAndEnabled(worldGuardAddon)) {
            return worldGuardAddon.getFunctionality().regionAllowsPlayer(player);
        }

        return true;
    }


    /**
     * Updates the stats of all the {@link AbstractAddon addons}.
     *
     * @param player the player to receive the effects.
     * @param crop   the crop to find the effects with.
     */
    public void updateStats(@NotNull Player player, @NotNull Crop crop) {
        MCMMOAddon mcMMOAddon = addonManager.getMcMMOAddon();
        JobsRebornAddon jobsRebornAddon = addonManager.getJobsRebornAddon();
        AuraSkillsAddon auraSkillsAddon = addonManager.getAuraSkillsAddon();

        if (addonManager.isInstalledAndEnabled(jobsRebornAddon)) {
            jobsRebornAddon.getFunctionality().updateStats(player, crop);
        }

        if (addonManager.isInstalledAndEnabled(mcMMOAddon)) {
            mcMMOAddon.getFunctionality().addExperience(player, crop);
        }

        if (addonManager.isInstalledAndEnabled(auraSkillsAddon)) {
            auraSkillsAddon.getFunctionality().addExperience(player, crop);
        }
    }


    /**
     * Selects the {@link Block provided block}, iff it is a {@link Crop crop} block.
     *
     * @param playerId the player who selected the block.
     * @param block    the block that was selected.
     */
    public void selectCrop(@NotNull String playerId, @NotNull Block block) {
        if (!plugin.getCropManager().isCrop(block)) return;
        playersConfig.set(ConfigurationKey.SELECTED_CROP, block.getLocation(), playerId);
    }


    /**
     * Selects the {@link Block provided block}, iff it is a container block.
     *
     * @param playerId the player who selected the block.
     * @param block    the block that was selected.
     */
    public void selectContainer(@NotNull String playerId, @NotNull Block block) {
        if (!BlockUtils.isAnyType(block, ContainerComponent.getTypes())) return;
        playersConfig.set(ConfigurationKey.SELECTED_CONTAINER, block.getLocation(), playerId);
    }


    /**
     * Selects the {@link Block provided block}, iff it is a {@link Dispenser dispenser} block.
     *
     * @param playerId the player who selected the block.
     * @param block    the block that was selected.
     */
    public void selectDispenser(@NotNull String playerId, @NotNull Block block) {
        if (BlockUtils.isSameType(block, XMaterial.DISPENSER)) return;
        playersConfig.set(ConfigurationKey.SELECTED_DISPENSER, block.getLocation(), playerId);
    }


    /**
     * Deselects the {@link Block provided block}, iff it is a {@link Crop crop} block.
     *
     * @param playerId the player who deselected the block.
     * @param block    the block that was deselected.
     */
    public void deselectCrop(@NotNull String playerId, @NotNull Block block) {
        if (!plugin.getCropManager().isCrop(block)) return;
        playersConfig.set(PlayersConfig.ConfigurationKey.SELECTED_CROP, null, playerId);
    }


    /**
     * Deselects the {@link Block provided block}, iff it is a container block.
     *
     * @param playerId the player who deselected the block.
     * @param block    the block that was deselected.
     */
    public void deselectContainer(@NotNull String playerId, @NotNull Block block) {
        if (!BlockUtils.isAnyType(block, ContainerComponent.getTypes())) return;
        playersConfig.set(PlayersConfig.ConfigurationKey.SELECTED_CONTAINER, null, playerId);
    }


    /**
     * Deselects the {@link Block provided block}, iff it is a {@link Dispenser dispenser} block.
     *
     * @param playerId the player who deselected the block.
     * @param block    the block that was deselected.
     */
    public void deselectDispenser(@NotNull String playerId, @NotNull Block block) {
        if (!BlockUtils.isSameType(block, XMaterial.DISPENSER)) return;
        playersConfig.set(PlayersConfig.ConfigurationKey.SELECTED_DISPENSER, null, playerId);
    }


    /**
     * Deselects all the provided player's id selected autofarm components,
     * and deselects the components for every other player that also has any
     * of these components selected.
     *
     * @param playerId the player to deselect all the components for.
     */
    public void deselectComponents(@NotNull String playerId) {
        Location crop = playersConfig.getSelectedCrop(playerId);
        Location container = playersConfig.getSelectedContainer(playerId);
        Location dispenser = playersConfig.getSelectedDispenser(playerId);

        playersConfig.setWithoutSave(PlayersConfig.ConfigurationKey.SELECTED_PLAYER, null, playerId);

        playersConfig.getKeys(PlayersConfig.ConfigurationKey.ALL_PLAYERS).forEach(keys -> {
            String otherId = keys.split("\\.")[0];

            if (crop != null && crop == playersConfig.getSelectedCrop(otherId)) {
                deselectCrop(otherId, crop.getBlock());
            }

            if (container != null && container == playersConfig.getSelectedContainer(otherId)) {
                deselectContainer(otherId, container.getBlock());
            }

            if (dispenser != null && dispenser == playersConfig.getSelectedDispenser(otherId)) {
                deselectDispenser(otherId, dispenser.getBlock());
            }
        });

        playersConfig.save();
    }

}
