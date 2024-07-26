/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.bakuplayz.cropclick.menus.abstracts.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractWorldsMenu;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link AbstractWorldsMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
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
