package com.github.bakuplayz.cropclick.menus.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.UpdatesMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class UpdatesStateBuilder {

    @NotNull
    public static UpdatesMenuStateHandler createStateHandler(@NotNull UpdatesMenu menu, @NotNull CropClick plugin) {
        return new UpdatesMenuStateHandler(menu, plugin);
    }


    public static class UpdatesMenuStateHandler extends MenuStateHandler<UpdatesMenuState, UpdatesMenu> {

        private final CropClick plugin;


        private UpdatesMenuStateHandler(@NotNull UpdatesMenu observer, @NotNull CropClick plugin) {
            super(observer, new UpdatesMenuState(plugin));
            this.plugin = plugin;
        }


        public void togglePlayerState() {
            updateState(state.isPlayerEnabled, (state) -> !state, UpdatesMenuStateFlag.PLAYER);
        }


        public void toggleConsoleState() {
            updateState(state.isConsoleEnabled, (state) -> !state, UpdatesMenuStateFlag.CONSOLE);
        }


        @Override
        protected <P> UpdatesMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == UpdatesMenuStateFlag.PLAYER) {
                state.setPlayerEnabled(infer(partial));
                plugin.getUpdateManager().setPlayerReceiveUpdates(infer(partial));
            } else if (flag == UpdatesMenuStateFlag.CONSOLE) {
                state.setConsoleEnabled(infer(partial));
                plugin.getUpdateManager().setConsoleReceiveUpdates(infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class UpdatesMenuState implements MenuState {

        private boolean isPlayerEnabled;

        private boolean isConsoleEnabled;


        private UpdatesMenuState(@NotNull CropClick plugin) {
            this.isPlayerEnabled = plugin.getUpdateManager().canPlayerReceiveUpdates();
            this.isConsoleEnabled = plugin.getUpdateManager().canConsoleReceiveUpdates();
        }

    }

    public static final class UpdatesMenuStateFlag {

        public final static int PLAYER = 0x00000001;

        public final static int CONSOLE = 0x00000002;

    }

}

