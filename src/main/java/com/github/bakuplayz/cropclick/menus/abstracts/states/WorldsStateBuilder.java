package com.github.bakuplayz.cropclick.menus.abstracts.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractWorldsMenu;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class WorldsStateBuilder {

    @NotNull
    public static WorldsMenuStateHandler createStateHandler(@NotNull AbstractWorldsMenu menu, @NotNull CropClick plugin) {
        return new WorldsMenuStateHandler(menu, plugin);
    }


    public static class WorldsMenuStateHandler extends PaginatedMenuStateHandler<WorldsMenuState> {

        private final CropClick plugin;


        public WorldsMenuStateHandler(@NotNull AbstractWorldsMenu observer, @NotNull CropClick plugin) {
            super(observer, new WorldsMenuState());
            this.plugin = plugin;
        }


        public void toggleWorld(@NotNull FarmWorld world, @NotNull String addonName, int flag) {
            updateState(0, (state) -> {
                world.toggleAddon(plugin.getAddonManager(), addonName);
                return state;
            }, flag);
        }


    }

    @Getter
    @Setter
    public static final class WorldsMenuState extends PaginatedMenuState {
    }

}
