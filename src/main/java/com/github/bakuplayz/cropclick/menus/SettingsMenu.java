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

package com.github.bakuplayz.cropclick.menus;

import com.cryptomorin.xseries.particles.XParticle;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.settings.*;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.menus.states.SettingsStateBuilder;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;
import static com.github.bakuplayz.cropclick.menus.states.SettingsStateBuilder.*;

/**
 * A class representing the Settings menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SettingsMenu extends AbstractStateMenu<SettingsMenuState, SettingsMenuStateHandler> {

    private final CropClick plugin;

    private final boolean showBackButton;


    public SettingsMenu(@NotNull CropClick plugin, boolean showBackButton) {
        super(SETTINGS_TITLE.getTitle(plugin));
        this.showBackButton = showBackButton;
        this.plugin = plugin;
    }


    @NotNull
    @Override
    public SettingsMenuStateHandler createStateHandler() {
        return SettingsStateBuilder.createStateHandler(this, plugin);
    }


    @Override
    public void setItems() {
        setItem(10, new ToggleItem(), (i, player) -> new ToggleMenu(plugin).open(player));
        setItem(13, new ParticlesItem(), (i, player) -> new ParticlesCropsMenu(plugin).open(player));
        setItem(16, new SoundsItem(), (i, player) -> new SoundsCropsMenu(plugin).open(player));
        setItem(28, new NameItem(), (i, player) -> new NamesCropsMenu(plugin).open(player));
        setItem(31, new AutoFarmsItem(), (i, i2) -> stateHandler.toggleAutofarmState(), SettingsMenuStateFlag.AUTOFARM_TOGGLE);
        setItem(34, new WorldItem(), (i, player) -> new WorldsMenu(plugin).open(player));
        setItemIf(showBackButton, 49, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    private final class ToggleItem extends ClickableItem {

        private final static int MIN_PLAYER_COUNT = 0;

        private final static int MAX_PLAYERS_COUNT = 999_999;


        @Override
        public void create() {
            setPlayer(viewers.get(0));
            setMaterial(XMaterial.PLAYER_HEAD);
            setName(SETTINGS_TOGGLE_ITEM_NAME.get(plugin));
            setLore(SETTINGS_TOGGLE_ITEM_TIPS.getAsAppendList(plugin,
                    SETTINGS_TOGGLE_ITEM_STATUS.get(plugin, getAmountOfEnabled())
            ));
        }


        /**
         * Gets the amount of {@link OfflinePlayer enabled players} allowed to use {@link CropClick}.
         *
         * @return the amount of enabled players.
         */
        private int getAmountOfEnabled() {
            int amountOfPlayers = Bukkit.getOfflinePlayers().length;
            int amountOfDisabled = plugin.getPlayersConfig().getDisabledPlayers().size();
            return MathUtils.clamp(amountOfPlayers - amountOfDisabled, MIN_PLAYER_COUNT, MAX_PLAYERS_COUNT);
        }

    }

    private final class ParticlesItem extends ClickableItem {

        @Override
        public void create() {
            setMaterial(XMaterial.FIREWORK_ROCKET);
            setName(SETTINGS_PARTICLES_ITEM_NAME.get(plugin));
            setLore(SETTINGS_PARTICLES_ITEM_TIPS.getAsAppendList(plugin,
                    SETTINGS_PARTICLES_ITEM_STATUS.get(plugin, getAmountOfParticles()))
            );
        }


        /**
         * Gets the amount of {@link XParticle#values() particles}.
         *
         * @return the amount of particles.
         */
        private int getAmountOfParticles() {
            return XParticle.values().length;
        }

    }

    private final class SoundsItem extends ClickableItem {

        @Override
        public void create() {
            setMaterial(XMaterial.NOTE_BLOCK);
            setName(SETTINGS_SOUNDS_ITEM_NAME.get(plugin));
            setLore(SETTINGS_SOUNDS_ITEM_TIPS.getAsAppendList(plugin,
                    SETTINGS_SOUNDS_ITEM_STATUS.get(plugin, getAmountOfSounds()))
            );
        }


        /**
         * Gets the amount of {@link Sound#values() sounds}.
         *
         * @return the amount of sounds.
         */
        private int getAmountOfSounds() {
            return Sound.values().length;
        }

    }

    private final class NameItem extends ClickableItem {

        @Override
        public void create() {
            setMaterial(XMaterial.NAME_TAG);
            setName(SETTINGS_NAME_ITEM_NAME.get(plugin));
            setLore(SETTINGS_NAME_ITEM_TIPS.getAsAppendList(plugin,
                    SETTINGS_NAME_ITEM_STATUS.get(plugin, getAmountOfRenamed()))
            );
        }


        /**
         * Gets the amount of {@link Crop renamed crops}.
         *
         * @return the amount of renamed crops.
         */
        private int getAmountOfRenamed() {
            return (int) plugin.getCropManager().getCrops().stream()
                    .filter(crop -> !crop.getDrop().getName().equals(crop.getName()))
                    .count();
        }

    }

    private final class AutoFarmsItem extends ClickableStateItem<SettingsMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.DISPENSER);
            setName(SETTINGS_AUTOFARMS_ITEM_NAME.get(plugin));
            setLore(getLore(plugin.getAutofarmManager().isEnabled()));
        }


        @Override
        public void update(@NotNull SettingsMenuState state, int flag) {
            setLore(getLore(state.isAutoFarmEnabled()));
        }


        @NotNull
        private List<String> getLore(boolean state) {
            return SETTINGS_AUTOFARMS_ITEM_TIPS.getAsAppendList(plugin,
                    SETTINGS_AUTOFARMS_ITEM_STATUS.get(plugin, MessageUtils.getStatusMessage(plugin, state))
            );
        }

    }

    private final class WorldItem extends ClickableItem {

        @Override
        public void create() {
            setMaterial(XMaterial.GRASS_BLOCK);
            setName(SETTINGS_WORLDS_ITEM_NAME.get(plugin));
            setLore(SETTINGS_WORLDS_ITEM_TIPS.getAsAppendList(plugin,
                    SETTINGS_WORLDS_ITEM_STATUS.get(plugin, getAmountOfBanished()))
            );
        }


        /**
         * Gets the amount of {@link FarmWorld banished worlds}.
         *
         * @return the amount of banished worlds.
         */
        private int getAmountOfBanished() {
            return (int) plugin.getWorldManager().getWorlds().values().stream()
                    .filter(FarmWorld::isBanished)
                    .count();
        }

    }

}
