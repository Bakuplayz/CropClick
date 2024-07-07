package com.github.bakuplayz.cropclick.menus.addons.mcmmo;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder.CropMenuState;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder.CropMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder.CropMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.items.state.StateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

public final class CropMenu extends AbstractCropMenu<CropMenuState, CropMenuStateHandler> {

    private final AddonConfigSection addonSection;


    public CropMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(AURA_SKILLS_TITLE.getTitle(plugin), plugin, crop);
        this.addonSection = plugin.getCropsConfig().getAddonSection();
    }


    @NotNull
    @Override
    public CropMenuStateHandler createStateHandler() {
        return CropMenuStateBuilder.createStateHandler(this, plugin, crop);
    }


    @Override
    public void setItems() {
        setItem(19, new ExperienceDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseExperienceValue(MAX_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(20, new ExperienceDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseExperienceValue(MIN_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(22, new ExperienceItem(), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(24, new ExperienceIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseExperienceValue(MIN_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(25, new ExperienceIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseExperienceValue(MAX_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(49, new CustomBackItem(plugin));
    }


    private final class ExperienceDecreaseItem extends AbstractDecreaseItem {

        public ExperienceDecreaseItem(int change) {
            super(addonSection.getMcMMOExperience(cropName), change);
        }


        @NotNull
        @Override
        protected String getName() {
            return MCMMO_CROP_REMOVE_ITEM_NAME.get(plugin, change, "Experience");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return MCMMO_CROP_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getExperience();
        }


        @Override
        protected int getLowerBound() {
            return CropMenuStateBuilder.MIN_VALUE;
        }

    }

    private final class ExperienceItem extends StateItem<CropMenuState> {


        @Override
        public void create() {
            setMaterial(XMaterial.EXPERIENCE_BOTTLE);
            setName(MCMMO_CROP_EXPERIENCE_ITEM_NAME.get(plugin));
            setLore(getLore(addonSection.getMcMMOExperience(cropName)));
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.getExperience()));
        }


        private @NotNull List<String> getLore(int value) {
            return MCMMO_CROP_EXPERIENCE_ITEM_TIPS.getAsAppendList(plugin,
                    MCMMO_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, value)
            );
        }

    }

    private final class ExperienceIncreaseItem extends AbstractIncreaseItem {

        public ExperienceIncreaseItem(int change) {
            super(addonSection.getMcMMOExperience(cropName), change);
        }


        @NotNull
        @Override
        protected String getName() {
            return MCMMO_CROP_ADD_ITEM_NAME.get(plugin, change, "Experience");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return MCMMO_CROP_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getExperience();
        }


        @Override
        protected int getHigherBound() {
            return CropMenuStateBuilder.MAX_VALUE;
        }

    }

}
