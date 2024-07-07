package com.github.bakuplayz.cropclick.menus.crops.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.crops.CropMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link CropMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CropStateBuilder {

    public final static int MIN_VALUE = 0;

    public final static int MAX_VALUE = 576;


    @NotNull
    public static CropMenuStateHandler createStateHandler(@NotNull CropMenu menu, @NotNull CropClick plugin, @NotNull Crop crop) {
        return new CropMenuStateHandler(menu, plugin, crop);
    }


    public static class CropMenuStateHandler extends MenuStateHandler<CropMenuState, CropMenu> {

        private final Crop crop;

        private final CropConfigSection cropSection;

        private final SeedConfigSection seedSection;


        private CropMenuStateHandler(@NotNull CropMenu observer, @NotNull CropClick plugin, @NotNull Crop crop) {
            super(observer, new CropMenuState(plugin, crop));
            this.cropSection = plugin.getCropsConfig().getCropSection();
            this.seedSection = plugin.getCropsConfig().getSeedSection();
            this.crop = crop;
        }


        public void decreaseCropValue(int decrement) {
            updateState(state.getCropValue(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.CROP_VALUE);
        }


        public void incrementCropValue(int increment) {
            updateState(state.getCropValue(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.CROP_VALUE);
        }


        public void toggleCropHarvestState() {
            updateState(state.isCropHarvestable, (state) -> !state, CropMenuStateFlag.CROP_STATE);
        }


        public void decreaseSeedValue(int decrement) {
            updateState(state.getSeedValue(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.SEED_VALUE);
        }


        public void incrementSeedValue(int increment) {
            updateState(state.getSeedValue(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.SEED_VALUE);
        }


        public void toggleSeedEnabledState() {
            updateState(state.isSeedEnabled, (state) -> !state, CropMenuStateFlag.SEED_STATE);
        }


        public void toggleLinkableState() {
            updateState(state.isLinkable, (state) -> !state, CropMenuStateFlag.LINKABLE_STATE);
        }


        public void toggleReplantState() {
            updateState(state.isReplantable, (state) -> !state, CropMenuStateFlag.REPLANT_STATE);
        }


        public void toggleAtLeastOneState() {
            updateState(state.isDropAtLeastOne, (state) -> !state, CropMenuStateFlag.AT_LEAST_ONE_STATE);
        }


        @Override
        protected <P> CropMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == CropMenuStateFlag.CROP_VALUE) {
                state.setCropValue(infer(partial));
                cropSection.setDropAmount(crop.getName(), infer(partial));
            }

            if (flag == CropMenuStateFlag.CROP_STATE) {
                state.setCropHarvestable(infer(partial));
                cropSection.setHarvestable(crop.getName(), infer(partial));
            }

            if (flag == CropMenuStateFlag.SEED_VALUE) {
                state.setSeedValue(infer(partial));
                seedSection.setDropAmount(crop.getSeed().getName(), infer(partial));
            }

            if (flag == CropMenuStateFlag.SEED_STATE) {
                state.setSeedEnabled(infer(partial));
                seedSection.setEnabled(crop.getSeed().getName(), infer(partial));
            }

            if (flag == CropMenuStateFlag.LINKABLE_STATE) {
                state.setLinkable(infer(partial));
                cropSection.setLinkable(crop.getName(), infer(partial));
            }

            if (flag == CropMenuStateFlag.REPLANT_STATE) {
                state.setReplantable(infer(partial));
                cropSection.setReplant(crop.getName(), infer(partial));
            }

            if (flag == CropMenuStateFlag.AT_LEAST_ONE_STATE) {
                state.setDropAtLeastOne(infer(partial));
                cropSection.setDropAtLeastOne(crop.getName(), infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class CropMenuState extends AbstractCropMenu.AbstractCropMenuState {

        private boolean isSeedEnabled;

        private boolean isCropHarvestable;

        private boolean isLinkable;

        private boolean isReplantable;

        private boolean isDropAtLeastOne;


        private CropMenuState(@NotNull CropClick plugin, @NotNull Crop crop) {
            this.isLinkable = crop.isLinkable();
            this.isReplantable = crop.shouldReplant();
            this.isCropHarvestable = crop.isHarvestable();
            this.isDropAtLeastOne = crop.dropAtLeastOne();
            this.isSeedEnabled = crop.hasSeed() && crop.getSeed().isEnabled();
            this.cropValue = plugin.getCropsConfig().getCropSection().getDropAmount(crop.getName());
            this.seedValue = plugin.getCropsConfig().getSeedSection().getDropAmount(crop.hasSeed() ? crop.getSeed().getName() : "");
        }

    }

    public static final class CropMenuStateFlag {

        public final static int CROP_VALUE = 0x1;

        public final static int SEED_VALUE = 0x2;

        public final static int CROP_STATE = 0x3;

        public final static int SEED_STATE = 0x4;

        public final static int LINKABLE_STATE = 0x5;

        public final static int REPLANT_STATE = 0x6;

        public static final int AT_LEAST_ONE_STATE = 0x7;

    }

}
