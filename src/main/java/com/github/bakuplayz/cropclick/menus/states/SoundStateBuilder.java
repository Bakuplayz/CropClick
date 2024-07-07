package com.github.bakuplayz.cropclick.menus.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.settings.sounds.SoundMenu;
import com.github.bakuplayz.cropclick.runnables.sounds.Sound;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link SoundMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SoundStateBuilder {


    @NotNull
    public static SoundMenuStateHandler createStateHandler(@NotNull SoundMenu menu, @NotNull CropClick plugin, @NotNull Crop crop, @NotNull String soundName) {
        return new SoundMenuStateHandler(menu, plugin, crop, soundName);
    }


    public static class SoundMenuStateHandler extends MenuStateHandler<SoundMenuState, SoundMenu> {

        private final Crop crop;

        private final String soundName;

        private final SoundConfigSection soundSection;


        private SoundMenuStateHandler(@NotNull SoundMenu observer, @NotNull CropClick plugin, @NotNull Crop crop, @NotNull String soundName) {
            super(observer, new SoundMenuState(plugin, crop, soundName));
            this.soundSection = plugin.getCropsConfig().getSoundSection();
            this.soundName = soundName;
            this.crop = crop;
        }


        public void decreaseDelay(int decrement) {
            updateState(state.delay, (state) -> Math.max(state - decrement, Sound.MIN_DELAY), SoundMenuStateFlag.DELAY);
            updateOrderStatus();
        }


        public void increaseDelay(int increment) {
            updateState(state.delay, (state) -> Math.min(state + increment, Sound.MAX_DELAY), SoundMenuStateFlag.DELAY);
            updateOrderStatus();
        }


        public void decreaseVolume(int decrement) {
            updateState(state.volume, (state) -> Math.max(state - decrement, Sound.MIN_VOLUME), SoundMenuStateFlag.VOLUME);
            updateOrderStatus();
        }


        public void increaseVolume(int increment) {
            updateState(state.volume, (state) -> Math.min(state + increment, Sound.MAX_VOLUME), SoundMenuStateFlag.VOLUME);
            updateOrderStatus();
        }


        public void decreasePitch(double decrement) {
            updateState(state.pitch, (state) -> Math.max(state - decrement, Sound.MIN_PITCH), SoundMenuStateFlag.PITCH);
            updateOrderStatus();
        }


        public void increasePitch(double increment) {
            updateState(state.pitch, (state) -> Math.min(state + increment, Sound.MAX_PITCH), SoundMenuStateFlag.PITCH);
            updateOrderStatus();
        }


        public void decreaseOrder() {
            updateState(state.order, (state) -> --state, SoundMenuStateFlag.ORDER);
        }


        public void increaseOrder() {
            updateState(state.order, (state) -> ++state, SoundMenuStateFlag.ORDER);
        }


        private void updateOrderStatus() {
            state.setOrder(soundSection.getOrder(crop.getName(), soundName));
            state.setMaxOrder(soundSection.getAmountOfSounds(crop.getName()) - 1);
            updateState(state.hasOrder, (state) -> soundSection.getOrder(crop.getName(), soundName) != -1, SoundMenuStateFlag.ORDER_STATE);
        }


        @Override
        protected <P> SoundMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == SoundMenuStateFlag.DELAY) {
                state.setDelay(infer(partial));
                soundSection.setDelay(crop.getName(), soundName, infer(partial));
            }

            if (flag == SoundMenuStateFlag.VOLUME) {
                state.setVolume(infer(partial));
                soundSection.setVolume(crop.getName(), soundName, infer(partial));
            }

            if (flag == SoundMenuStateFlag.PITCH) {
                state.setPitch(MathUtils.round(infer(partial)));
                soundSection.setPitch(crop.getName(), soundName, MathUtils.round(infer(partial)));
            }

            if (flag == SoundMenuStateFlag.ORDER) {
                soundSection.swapOrder(crop.getName(), state.order, infer(partial));
                state.setOrder(infer(partial));
            }

            if (flag == SoundMenuStateFlag.ORDER_STATE) {
                state.setHasOrder(infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class SoundMenuState implements MenuState {

        private double delay;

        private double volume;

        private double pitch;

        private int order;

        private int maxOrder;

        private boolean hasOrder;


        private SoundMenuState(@NotNull CropClick plugin, @NotNull Crop crop, @NotNull String soundName) {
            SoundConfigSection soundSection = plugin.getCropsConfig().getSoundSection();

            this.order = soundSection.getOrder(crop.getName(), soundName);
            this.pitch = soundSection.getPitch(crop.getName(), soundName);
            this.delay = soundSection.getDelay(crop.getName(), soundName);
            this.volume = soundSection.getVolume(crop.getName(), soundName);
            this.maxOrder = soundSection.getAmountOfSounds(crop.getName()) - 1;
            this.hasOrder = soundSection.getOrder(crop.getName(), soundName) != -1;
        }

    }

    public static final class SoundMenuStateFlag {

        public final static int DELAY = 0x1;

        public final static int VOLUME = 0x2;

        public final static int PITCH = 0x3;

        public final static int ORDER = 0x4;

        public final static int ORDER_STATE = 0x5;

    }

}
