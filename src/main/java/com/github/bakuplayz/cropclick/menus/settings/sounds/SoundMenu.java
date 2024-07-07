package com.github.bakuplayz.cropclick.menus.settings.sounds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.menus.states.SoundStateBuilder;
import com.github.bakuplayz.cropclick.menus.states.SoundStateBuilder.SoundMenuState;
import com.github.bakuplayz.cropclick.menus.states.SoundStateBuilder.SoundMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.states.SoundStateBuilder.SoundMenuStateHandler;
import com.github.bakuplayz.cropclick.runnables.sounds.Sound;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.menu.items.utils.ViewState;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;


/**
 * A class representing the Sound menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SoundMenu extends AbstractStateMenu<SoundMenuState, SoundMenuStateHandler> {

    /**
     * A variable measured in milliseconds.
     */
    private static final int DELAY_MIN_CHANGE = 100;

    /**
     * A variable measured in milliseconds.
     */
    private static final int DELAY_MAX_CHANGE = 500;

    /**
     * A variable measured in range-of-blocks.
     */
    private static final int VOLUME_MIN_CHANGE = 1;

    /**
     * A variable measured in range-of-blocks.
     */
    private static final int VOLUME_MAX_CHANGE = 5;

    private static final double PITCH_MIN_CHANGE = 0.1;

    private static final double PITCH_MAX_CHANGE = 0.2;

    private static final int ORDER_MIN = 0;


    @Setter(AccessLevel.PRIVATE)
    private CropClick plugin;

    @Setter(AccessLevel.PRIVATE)
    private Crop crop;

    @Setter(AccessLevel.PRIVATE)
    private String soundName;

    @Setter(AccessLevel.PRIVATE)
    private SoundConfigSection soundSection;


    public SoundMenu(@NotNull CropClick plugin, @NotNull Crop crop, @NotNull String soundName) {
        super(SOUND_TITLE.getTitle(plugin));
        setCrop(crop);
        setPlugin(plugin);
        setSoundName(soundName);
        setSoundSection(plugin.getCropsConfig().getSoundSection());
    }


    @NotNull
    @Override
    public SoundMenuStateHandler createStateHandler() {
        return SoundStateBuilder.createStateHandler(this, plugin, crop, soundName);
    }


    @Override
    public void setItems() {
        setItem(10, new DelayDecreaseItem(DELAY_MAX_CHANGE), (item, player) -> stateHandler.decreaseDelay(DELAY_MAX_CHANGE), SoundMenuStateFlag.DELAY);
        setItem(11, new DelayDecreaseItem(DELAY_MIN_CHANGE), (item, player) -> stateHandler.decreaseDelay(DELAY_MIN_CHANGE), SoundMenuStateFlag.DELAY);
        setItem(13, new DelayItem(), SoundMenuStateFlag.DELAY);
        setItem(15, new DelayIncreaseItem(DELAY_MIN_CHANGE), (item, player) -> stateHandler.increaseDelay(DELAY_MIN_CHANGE), SoundMenuStateFlag.DELAY);
        setItem(16, new DelayIncreaseItem(DELAY_MAX_CHANGE), (item, player) -> stateHandler.increaseDelay(DELAY_MAX_CHANGE), SoundMenuStateFlag.DELAY);

        setItem(19, new VolumeDecreaseItem(VOLUME_MAX_CHANGE), (item, player) -> stateHandler.decreaseVolume(VOLUME_MAX_CHANGE), SoundMenuStateFlag.VOLUME);
        setItem(20, new VolumeDecreaseItem(VOLUME_MIN_CHANGE), (item, player) -> stateHandler.decreaseVolume(VOLUME_MIN_CHANGE), SoundMenuStateFlag.VOLUME);
        setItem(22, new VolumeItem(), SoundMenuStateFlag.VOLUME);
        setItem(24, new VolumeIncreaseItem(VOLUME_MIN_CHANGE), (item, player) -> stateHandler.increaseVolume(VOLUME_MIN_CHANGE), SoundMenuStateFlag.VOLUME);
        setItem(25, new VolumeIncreaseItem(VOLUME_MAX_CHANGE), (item, player) -> stateHandler.increaseVolume(VOLUME_MAX_CHANGE), SoundMenuStateFlag.VOLUME);

        setItem(28, new PitchDecreaseItem(PITCH_MAX_CHANGE), (item, player) -> stateHandler.decreasePitch(PITCH_MAX_CHANGE), SoundMenuStateFlag.PITCH);
        setItem(29, new PitchDecreaseItem(PITCH_MIN_CHANGE), (item, player) -> stateHandler.decreasePitch(PITCH_MIN_CHANGE), SoundMenuStateFlag.PITCH);
        setItem(31, new PitchItem(), SoundMenuStateFlag.PITCH);
        setItem(33, new PitchIncreaseItem(PITCH_MIN_CHANGE), (item, player) -> stateHandler.increasePitch(PITCH_MIN_CHANGE), SoundMenuStateFlag.PITCH);
        setItem(34, new PitchIncreaseItem(PITCH_MAX_CHANGE), (item, player) -> stateHandler.increasePitch(PITCH_MAX_CHANGE), SoundMenuStateFlag.PITCH);

        setItem(47, new DecreaseOrderItem(), (item, player) -> stateHandler.decreaseOrder(), Arrays.asList(SoundMenuStateFlag.ORDER, SoundMenuStateFlag.ORDER_STATE));
        setItem(49, new CustomBackItem(plugin));
        setItem(51, new IncreaseOrderItem(), (item, player) -> stateHandler.increaseOrder(), Arrays.asList(SoundMenuStateFlag.ORDER, SoundMenuStateFlag.ORDER_STATE));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @AllArgsConstructor
    private final class DelayDecreaseItem extends ClickableStateItem<SoundMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
            setName(SOUND_REMOVE_ITEM_NAME.get(plugin, change, "Delay"));
            setLore(getLore(soundSection.getDelay(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getDelay()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double delay) {
            return SOUND_REMOVE_ITEM_AFTER.getAsList(plugin, getAfterValue(delay));
        }


        private double getAfterValue(double beforeValue) {
            return Math.max(beforeValue - change, Sound.MIN_DELAY);
        }

    }

    private final class DelayItem extends ClickableStateItem<SoundMenuState> {


        @Override
        public void create() {
            setMaterial(XMaterial.CLOCK);
            setName(SOUND_DELAY_ITEM_NAME.get(plugin));
            setLore(getLore(soundSection.getDelay(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getDelay()));
        }


        @NotNull
        private List<String> getLore(double delay) {
            return SOUND_DELAY_ITEM_TIPS.getAsAppendList(plugin,
                    SOUND_DELAY_ITEM_VALUE.get(plugin, delay)
            );
        }

    }

    @AllArgsConstructor
    private final class DelayIncreaseItem extends ClickableStateItem<SoundMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
            setName(SOUND_ADD_ITEM_NAME.get(plugin, change, "Delay"));
            setLore(getLore(soundSection.getDelay(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getDelay()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double delay) {
            return SOUND_ADD_ITEM_AFTER.getAsList(plugin, getAfterValue(delay));
        }


        private double getAfterValue(double beforeValue) {
            return Math.min(beforeValue + change, Sound.MAX_DELAY);
        }

    }

    @AllArgsConstructor
    private final class VolumeDecreaseItem extends ClickableStateItem<SoundMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
            setName(SOUND_REMOVE_ITEM_NAME.get(plugin, change, "Volume"));
            setLore(getLore(soundSection.getVolume(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getVolume()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double volume) {
            return SOUND_REMOVE_ITEM_AFTER.getAsList(plugin, getAfterValue(volume));
        }


        private double getAfterValue(double beforeValue) {
            return Math.max(beforeValue - change, Sound.MIN_VOLUME);
        }

    }

    private final class VolumeItem extends ClickableStateItem<SoundMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.NOTE_BLOCK);
            setName(SOUND_VOLUME_ITEM_NAME.get(plugin));
            setLore(getLore(soundSection.getVolume(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getVolume()));
        }


        @NotNull
        private List<String> getLore(double volume) {
            return SOUND_VOLUME_ITEM_TIPS.getAsAppendList(plugin,
                    SOUND_VOLUME_ITEM_VALUE.get(plugin, volume)
            );
        }

    }

    @AllArgsConstructor
    private final class VolumeIncreaseItem extends ClickableStateItem<SoundMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
            setName(SOUND_ADD_ITEM_NAME.get(plugin, change, "Volume"));
            setLore(getLore(soundSection.getVolume(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getVolume()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double volume) {
            return SOUND_ADD_ITEM_AFTER.getAsList(plugin, getAfterValue(volume));
        }


        private double getAfterValue(double beforeValue) {
            return Math.min(beforeValue + change, Sound.MAX_VOLUME);
        }

    }

    @AllArgsConstructor
    private final class PitchDecreaseItem extends ClickableStateItem<SoundMenuState> {

        private final double change;


        @Override
        public void create() {
            setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
            setName(SOUND_REMOVE_ITEM_NAME.get(plugin, change, "Pitch"));
            setLore(getLore(soundSection.getPitch(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getPitch()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double pitch) {
            return SOUND_REMOVE_ITEM_AFTER.getAsList(plugin, getAfterValue(pitch));
        }


        private double getAfterValue(double beforeValue) {
            return MathUtils.round(Math.max(beforeValue - change, Sound.MIN_PITCH));
        }

    }

    private final class PitchItem extends ClickableStateItem<SoundMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.TRIPWIRE_HOOK);
            setName(SOUND_PITCH_ITEM_NAME.get(plugin));
            setLore(getLore(soundSection.getPitch(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getPitch()));
        }


        @NotNull
        private List<String> getLore(double pitch) {
            return SOUND_PITCH_ITEM_TIPS.getAsAppendList(plugin,
                    SOUND_PITCH_ITEM_VALUE.get(plugin, pitch)
            );
        }

    }

    @AllArgsConstructor
    private final class PitchIncreaseItem extends ClickableStateItem<SoundMenuState> {

        private final double change;


        @Override
        public void create() {
            setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
            setName(SOUND_ADD_ITEM_NAME.get(plugin, change, "Pitch"));
            setLore(getLore(soundSection.getPitch(crop.getName(), soundName)));
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getPitch()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double pitch) {
            return SOUND_ADD_ITEM_AFTER.getAsList(plugin, getAfterValue(pitch));
        }


        private double getAfterValue(double beforeValue) {
            return MathUtils.round(Math.min(beforeValue + change, Sound.MAX_PITCH));
        }

    }

    private final class IncreaseOrderItem extends ClickableStateItem<SoundMenuState> {

        @Override
        public void create() {
            int order = soundSection.getOrder(crop.getName(), soundName);
            int maxOrder = soundSection.getAmountOfSounds(crop.getName()) - 1;

            setLore(getLore(order, maxOrder));
            setViewState(getViewState(order, maxOrder));
            setName(SOUND_INCREASE_ORDER_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE);
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getOrder(), state.getMaxOrder()));
            setViewState(getViewState(state.getOrder(), state.getMaxOrder()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(int order, int maxOrder) {
            return SOUND_INCREASE_ORDER_ITEM_AFTER.getAsList(plugin, getAfterValue(order, maxOrder));
        }


        private int getAfterValue(int beforeOrder, int maxOrder) {
            return Math.min(beforeOrder + 1, maxOrder);
        }


        private ViewState getViewState(int order, int maxOrder) {
            if (order == -1) {
                return ViewState.INVISIBLE;
            }

            return maxOrder > order ? ViewState.VISIBLE : ViewState.INVISIBLE;
        }

    }

    private final class DecreaseOrderItem extends ClickableStateItem<SoundMenuState> {

        @Override
        public void create() {
            int order = soundSection.getOrder(crop.getName(), soundName);

            setLore(getLore(order));
            setViewState(getViewState(order));
            setName(SOUND_DECREASE_ORDER_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE);
        }


        @Override
        public void update(@NotNull SoundMenuState state, int flag) {
            setLore(getLore(state.getOrder()));
            setViewState(getViewState(state.getOrder()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(int order) {
            return SOUND_DECREASE_ORDER_ITEM_AFTER.getAsList(plugin, getAfterValue(order));
        }


        private int getAfterValue(int afterOrder) {
            return Math.max(afterOrder - 1, ORDER_MIN);
        }


        private ViewState getViewState(int order) {
            return order > 0 ? ViewState.VISIBLE : ViewState.INVISIBLE;
        }

    }

}
