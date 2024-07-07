package com.github.bakuplayz.cropclick.menus.addons.auraskills.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu.AbstractCropMenuState;
import com.github.bakuplayz.cropclick.menus.addons.auraskills.CropMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class CropMenuStateBuilder {

    public final static int MIN_VALUE = 0;

    public final static int MAX_VALUE = 10_000;


    @NotNull
    public static CropMenuStateHandler createStateHandler(@NotNull CropMenu menu, @NotNull CropClick plugin, @NotNull Crop crop) {
        return new CropMenuStateHandler(menu, plugin, crop);
    }


    public static class CropMenuStateHandler extends MenuStateHandler<CropMenuState, CropMenu> {

        private final Crop crop;

        private final AddonConfigSection addonSection;


        private CropMenuStateHandler(@NotNull CropMenu observer, @NotNull CropClick plugin, @NotNull Crop crop) {
            super(observer, new CropMenuState(plugin, crop));
            this.addonSection = plugin.getCropsConfig().getAddonSection();
            this.crop = crop;
        }


        public void decreaseExperienceValue(int decrement) {
            updateState(state.getExperience(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.EXPERIENCE_VALUE);
        }


        public void increaseExperienceValue(int increment) {
            updateState(state.getExperience(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.EXPERIENCE_VALUE);
        }


        @Override
        protected <P> CropMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == CropMenuStateFlag.EXPERIENCE_VALUE) {
                state.setExperience(infer(partial));
                addonSection.setSkillsExperience(crop.getName(), infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class CropMenuState extends AbstractCropMenuState {

        private int experience;


        private CropMenuState(@NotNull CropClick plugin, @NotNull Crop crop) {
            this.isCropHarvestable = crop.isHarvestable();
            this.isSeedEnabled = crop.hasSeed() && crop.getSeed().isEnabled();
            this.experience = plugin.getCropsConfig().getAddonSection().getSkillsExperience(crop.getName());
        }

    }

    public static final class CropMenuStateFlag {

        public final static int EXPERIENCE_VALUE = 0x1;

    }

}
