package com.github.bakuplayz.cropclick.menus.settings.states;

import com.github.bakuplayz.cropclick.menus.settings.worlds.WorldMenu;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class WorldStateBuilder {

    @NotNull
    public static WorldMenuStateHandler createStateHandler(@NotNull WorldMenu menu, @NotNull FarmWorld world) {
        return new WorldMenuStateHandler(menu, world);
    }


    public static class WorldMenuStateHandler extends MenuStateHandler<WorldMenuState, WorldMenu> {

        private final FarmWorld world;


        private WorldMenuStateHandler(@NotNull WorldMenu observer, @NotNull FarmWorld world) {
            super(observer, new WorldMenuState(world));
            this.world = world;
        }


        public void toggleAutofarmsState() {
            updateState(state.isAutofarmsAllowed, (state) -> !state, WorldMenuStateFlag.AUTOFARMS);
        }


        public void toggleBanishedState() {
            updateState(state.isBanished, (state) -> !state, WorldMenuStateFlag.BANISHED);
        }


        public void togglePlayersState() {
            updateState(state.isPlayersAllowed, (state) -> !state, WorldMenuStateFlag.PLAYERS);
        }


        @Override
        protected <P> WorldMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == WorldMenuStateFlag.AUTOFARMS) {
                state.setAutofarmsAllowed(infer(partial));
                world.allowsAutofarms(infer(partial));
            }

            if (flag == WorldMenuStateFlag.BANISHED) {
                state.setBanished(infer(partial));
                world.isBanished(infer(partial));
            }

            if (flag == WorldMenuStateFlag.PLAYERS) {
                state.setPlayersAllowed(infer(partial));
                world.allowsPlayers(infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class WorldMenuState implements MenuState {

        private boolean isAutofarmsAllowed;

        private boolean isPlayersAllowed;

        private boolean isBanished;


        private WorldMenuState(@NotNull FarmWorld world) {
            this.isAutofarmsAllowed = world.allowsAutofarms();
            this.isPlayersAllowed = world.allowsPlayers();
            this.isBanished = world.isBanished();
        }

    }

    public static final class WorldMenuStateFlag {

        public final static int AUTOFARMS = 0x01;

        public final static int PLAYERS = 0x02;

        public final static int BANISHED = 0x03;

    }

}
