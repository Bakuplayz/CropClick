package com.github.bakuplayz.cropclick.menus.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.settings.ToggleMenu;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class ToggleStateBuilder {

    @NotNull
    public static ToggleMenuStateHandler createStateHandler(@NotNull ToggleMenu menu, @NotNull CropClick plugin) {
        return new ToggleMenuStateHandler(menu, plugin);
    }


    public static class ToggleMenuStateHandler extends PaginatedMenuStateHandler<ToggleMenuState> {

        private final CropClick plugin;


        public ToggleMenuStateHandler(@NotNull ToggleMenu observer, @NotNull CropClick plugin) {
            super(observer, new ToggleMenuState());
            this.plugin = plugin;
        }


        public void togglePlayer(@NotNull String playerId, int flag) {
            updateState(0, (state) -> {
                plugin.getPlayersConfig().togglePlayer(playerId);
                return state;
            }, flag);
        }


    }

    @Getter
    @Setter
    public static final class ToggleMenuState extends PaginatedMenuState {
    }

}
