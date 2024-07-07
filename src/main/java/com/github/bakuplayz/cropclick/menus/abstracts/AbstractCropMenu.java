package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A class representing the Crop menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractCropMenu<S extends AbstractCropMenu.AbstractCropMenuState, SH extends MenuStateHandler<S, ?>> extends AbstractStateMenu<S, SH> {

    protected final static int MIN_CHANGE = 1;

    protected final static int MAX_CHANGE = 5;


    protected final Crop crop;

    protected final Seed seed;

    protected final String cropName;

    protected final boolean hasSeed;

    protected final CropClick plugin;

    protected final CropConfigSection cropSection;

    protected final SeedConfigSection seedSection;


    public AbstractCropMenu(@NotNull String title, @NotNull CropClick plugin, @NotNull Crop crop) {
        super(title);
        this.crop = crop;
        this.plugin = plugin;
        this.seed = crop.getSeed();
        this.hasSeed = crop.hasSeed();
        this.cropName = crop.getName();
        this.cropSection = plugin.getCropsConfig().getCropSection();
        this.seedSection = plugin.getCropsConfig().getSeedSection();
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @Getter
    @Setter
    public static abstract class AbstractCropMenuState implements MenuState {

        protected int cropValue;

        protected int seedValue;

        protected boolean isSeedEnabled;

        protected boolean isCropHarvestable;

    }

    public static class AbstractMenuStateFlag {

        public final static int CROP_VALUE = 0x1;

        public final static int SEED_VALUE = 0x2;

    }

    @AllArgsConstructor
    protected abstract class AbstractDecreaseItem extends ClickableStateItem<S> {

        protected final int change;

        private final int initialValue;


        @Override
        public void create() {
            setName(getName());
            setLore(getLore(getAfterValue(initialValue)));
            setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
        }


        @Override
        public final void update(@NotNull S state, int flag) {
            setLore(getLore(getAfterValue(getStateValue(state))));
        }


        protected abstract String getName();


        protected abstract List<String> getLore(int value);


        protected abstract int getStateValue(@NotNull S state);


        protected abstract int getLowerBound();


        private int getAfterValue(int beforeValue) {
            return Math.max(beforeValue - change, getLowerBound());
        }

    }

    @AllArgsConstructor
    protected abstract class AbstractIncreaseItem extends ClickableStateItem<S> {

        protected final int change;

        private final int initialValue;


        @Override
        public void create() {
            setName(getName());
            setLore(getLore(getAfterValue(initialValue)));
            setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
        }


        @Override
        public final void update(@NotNull S state, int flag) {
            setLore(getLore(getAfterValue(getStateValue(state))));
        }


        protected abstract String getName();


        protected abstract List<String> getLore(int value);


        protected abstract int getStateValue(@NotNull S state);


        protected abstract int getHigherBound();


        private int getAfterValue(int beforeValue) {
            return Math.min(beforeValue + change, getHigherBound());
        }
    }

    protected abstract class AbstractCropDecreaseItem extends AbstractDecreaseItem {

        public AbstractCropDecreaseItem(int initialValue, int change) {
            super(initialValue, change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getCropValue();
        }

    }

    @AllArgsConstructor
    protected abstract class AbstractCropItem extends ClickableStateItem<S> {

        private final int initialValue;


        @Override
        public void create() {
            setLore(getLore(initialValue));
            setMaterial(crop.getMenuType());
            setName(getName(crop.isHarvestable()));
            setMaterial(!crop.isHarvestable(), XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @Override
        public final void update(@NotNull AbstractCropMenu.AbstractCropMenuState state, int flag) {
            setLore(getLore(state.getCropValue()));
            setName(getName(state.isCropHarvestable()));
            setMaterial(state.isCropHarvestable() ? crop.getMenuType() : XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        protected abstract String getName(boolean state);


        @NotNull
        protected abstract List<String> getLore(int value);

    }

    protected abstract class AbstractCropIncreaseItem extends AbstractIncreaseItem {

        public AbstractCropIncreaseItem(int initialValue, int change) {
            super(initialValue, change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getCropValue();
        }

    }

    protected abstract class AbstractSeedDecreaseItem extends AbstractDecreaseItem {

        public AbstractSeedDecreaseItem(int initialValue, int change) {
            super(initialValue, change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getSeedValue();
        }

    }

    @AllArgsConstructor
    protected abstract class AbstractSeedItem extends ClickableStateItem<S> {

        private final int initialValue;


        @Override
        public void create() {
            setLore(getLore(initialValue));
            setMaterial(seed.getMenuType());
            setName(getName(seed.isEnabled()));
            setMaterial(!seed.isEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @Override
        public final void update(@NotNull AbstractCropMenu.AbstractCropMenuState state, int flag) {
            setName(getName(state.isSeedEnabled()));
            setLore(getLore(state.getSeedValue()));
            setMaterial(state.isSeedEnabled() ? seed.getMenuType() : XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        protected abstract String getName(boolean state);


        @NotNull
        protected abstract List<String> getLore(int value);

    }

    protected abstract class AbstractSeedIncreaseItem extends AbstractIncreaseItem {

        public AbstractSeedIncreaseItem(int initialValue, int change) {
            super(initialValue, change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getSeedValue();
        }

    }

}
