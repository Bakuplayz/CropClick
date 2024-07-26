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
package com.github.bakuplayz.cropclick.menus.crops.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configurations.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu.AbstractCropMenuState;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu.AbstractMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.crops.chance.ChanceMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link ChanceMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ChanceStateBuilder {

    public final static int MIN_VALUE = 0;

    public final static int MAX_VALUE = 100;


    @NotNull
    public static ChanceMenuStateHandler createStateHandler(@NotNull ChanceMenu menu, @NotNull CropClick plugin, @NotNull Crop crop) {
        return new ChanceMenuStateHandler(menu, plugin, crop);
    }


    public static class ChanceMenuStateHandler extends MenuStateHandler<ChanceMenuState, ChanceMenu> {

        private final Crop crop;

        private final CropConfigSection cropSection;

        private final SeedConfigSection seedSection;


        private ChanceMenuStateHandler(@NotNull ChanceMenu observer, @NotNull CropClick plugin, @NotNull Crop crop) {
            super(observer, new ChanceMenuState(plugin, crop));
            this.cropSection = plugin.getCropsConfig().getCropSection();
            this.seedSection = plugin.getCropsConfig().getSeedSection();
            this.crop = crop;
        }


        public void decreaseCropValue(int decrement) {
            updateState(state.getCropValue(), (state) -> Math.max(state - decrement, MIN_VALUE), AbstractMenuStateFlag.CROP_VALUE);
        }


        public void increaseCropValue(int increment) {
            updateState(state.getCropValue(), (state) -> Math.min(state + increment, MAX_VALUE), AbstractMenuStateFlag.CROP_VALUE);
        }


        public void decreaseSeedValue(int decrement) {
            updateState(state.getSeedValue(), (state) -> Math.max(state - decrement, MIN_VALUE), AbstractMenuStateFlag.SEED_VALUE);
        }


        public void increaseSeedValue(int increment) {
            updateState(state.getSeedValue(), (state) -> Math.min(state + increment, MAX_VALUE), AbstractMenuStateFlag.SEED_VALUE);
        }


        public void toggleCropHarvestState() {
            updateState(state.isCropHarvestable(), (state) -> !state, AbstractMenuStateFlag.CROP_STATE);
        }


        public void toggleSeedEnabledState() {
            updateState(state.isSeedEnabled(), (state) -> !state, AbstractMenuStateFlag.SEED_STATE);
        }


        @Override
        protected <P> ChanceMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == AbstractMenuStateFlag.CROP_STATE) {
                state.setCropHarvestable(infer(partial));
                cropSection.setHarvestable(crop.getName(), infer(partial));
            }

            if (flag == AbstractMenuStateFlag.SEED_STATE) {
                state.setSeedEnabled(infer(partial));
                seedSection.setEnabled(crop.getSeed().getName(), infer(partial));
            }

            if (flag == AbstractMenuStateFlag.CROP_VALUE) {
                state.setCropValue(infer(partial));
                cropSection.setDropChance(crop.getName(), (int) partial / 100.0d);
            }

            if (flag == AbstractMenuStateFlag.SEED_VALUE) {
                state.setSeedValue(infer(partial));
                seedSection.setDropChance(crop.getSeed().getName(), (int) partial / 100.0d);
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class ChanceMenuState extends AbstractCropMenuState {

        private ChanceMenuState(@NotNull CropClick plugin, @NotNull Crop crop) {
            this.isCropHarvestable = crop.isHarvestable();
            this.isSeedEnabled = crop.hasSeed() && crop.getSeed().isEnabled();
            this.cropValue = plugin.getCropsConfig().getCropSection().getDropChanceDecimal(crop.getName());
            this.seedValue = plugin.getCropsConfig().getSeedSection().getDropChanceDecimal(crop.hasSeed() ? crop.getSeed().getName() : "");
        }

    }


}
