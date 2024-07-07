package com.github.bakuplayz.cropclick.menus.crops.chance;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.crops.states.ChanceStateBuilder;
import com.github.bakuplayz.cropclick.menus.crops.states.ChanceStateBuilder.ChanceMenuState;
import com.github.bakuplayz.cropclick.menus.crops.states.ChanceStateBuilder.ChanceMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

public final class ChanceMenu extends AbstractCropMenu<ChanceMenuState, ChanceMenuStateHandler> {


    public ChanceMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(DROP_CHANCE_TITLE.getTitle(plugin), plugin, crop);
    }


    @NotNull
    @Override
    public ChanceMenuStateHandler createStateHandler() {
        return ChanceStateBuilder.createStateHandler(this, plugin, crop);
    }


    @Override
    public void setItems() {
        setItem(hasSeed ? 10 : 19, new CropDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseCropValue(MAX_CHANGE), AbstractMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 11 : 20, new CropDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseCropValue(MIN_CHANGE), AbstractMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 13 : 22, new CropItem(), AbstractMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 15 : 24, new CropIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseCropValue(MIN_CHANGE), AbstractMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 16 : 25, new CropIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseCropValue(MAX_CHANGE), AbstractMenuStateFlag.CROP_VALUE);

        if (hasSeed) {
            setItem(28, new SeedDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseSeedValue(MAX_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
            setItem(29, new SeedDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseSeedValue(MIN_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
            setItem(31, new SeedItem(), AbstractMenuStateFlag.SEED_VALUE);
            setItem(33, new SeedIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseSeedValue(MIN_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
            setItem(34, new SeedIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseSeedValue(MAX_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
        }

        setItem(49, new CustomBackItem(plugin));
    }


    private final class CropDecreaseItem extends AbstractCropDecreaseItem {

        public CropDecreaseItem(int change) {
            super(cropSection.getDropChanceDecimal(cropName), change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, change, "Crop");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getLowerBound() {
            return ChanceStateBuilder.MIN_VALUE;
        }

    }

    private final class CropItem extends AbstractCropItem {

        public CropItem() {
            super(cropSection.getDropChanceDecimal(cropName));
        }


        @Override
        protected @NotNull String getName(boolean isHarvestable) {
            String name = MessageUtils.beautify(cropName, false);
            String status = isHarvestable
                    ? CROP_STATUS_ENABLED.get(plugin)
                    : CROP_STATUS_DISABLED.get(plugin);

            return DROP_CHANCE_CROP_ITEM_NAME.get(plugin, name, status);
        }


        @Override
        protected @NotNull List<String> getLore(int value) {
            return DROP_CHANCE_CROP_ITEM_TIPS.getAsAppendList(plugin,
                    DROP_CHANCE_CROP_ITEM_DROP_CHANCE.get(plugin, value)
            );
        }

    }

    private final class CropIncreaseItem extends AbstractCropIncreaseItem {

        public CropIncreaseItem(int change) {
            super(cropSection.getDropChanceDecimal(cropName), change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_ADD_ITEM_NAME.get(plugin, change, "Crop");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getHigherBound() {
            return ChanceStateBuilder.MAX_VALUE;
        }

    }

    private final class SeedDecreaseItem extends AbstractSeedDecreaseItem {

        public SeedDecreaseItem(int change) {
            super(seedSection.getDropChanceDecimal(seed.getName()), change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, change, "Seed");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getLowerBound() {
            return ChanceStateBuilder.MIN_VALUE;
        }

    }

    private final class SeedItem extends AbstractSeedItem {

        public SeedItem() {
            super(seedSection.getDropChanceDecimal(seed.getName()));
        }


        @Override
        protected @NotNull String getName(boolean state) {
            return DROP_CHANCE_CROP_ITEM_NAME.get(plugin, cropName, state);
        }


        @Override
        protected @NotNull List<String> getLore(int value) {
            return DROP_CHANCE_CROP_ITEM_TIPS.getAsAppendList(plugin,
                    DROP_CHANCE_CROP_ITEM_DROP_CHANCE.get(plugin, value)
            );
        }

    }

    private final class SeedIncreaseItem extends AbstractSeedIncreaseItem {

        public SeedIncreaseItem(int change) {
            super(seedSection.getDropChanceDecimal(seed.getName()), change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_ADD_ITEM_NAME.get(plugin, change, "Seed");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getHigherBound() {
            return ChanceStateBuilder.MAX_VALUE;
        }

    }

}
