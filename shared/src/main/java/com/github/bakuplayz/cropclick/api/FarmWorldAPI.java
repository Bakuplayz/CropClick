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
package com.github.bakuplayz.cropclick.api;

import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FarmWorldAPI {


    /**
     * Finds a {@link FarmWorld farm world} based on the {@link World world}.
     *
     * @param world the world to base the findings on.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    @Nullable
    FarmWorld findByWorld(@NotNull World world);


    /**
     * Finds a {@link FarmWorld farm world} based on its name.
     *
     * @param name the name of the world to find.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    @Nullable
    FarmWorld findByName(@NotNull String name);


    /**
     * Finds a {@link FarmWorld farm world} based on the {@link Player player's position}.
     *
     * @param player the player to base the findings on.
     *
     * @return the found {@link FarmWorld}, otherwise null.
     */
    @Nullable
    FarmWorld findByPlayer(@NotNull Player player);

}
