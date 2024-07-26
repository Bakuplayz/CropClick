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

import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AddonAPI {

    /**
     * Finds the {@link AbstractAddon addon} based on the provided name.
     *
     * @param name the name of the addon.
     *
     * @return the found addon, otherwise null.
     */
    @Nullable
    AbstractAddon findByName(@NotNull String name);


    /**
     * Gets all the registered addons.
     *
     * @return the registered addons.
     */
    @NotNull
    List<AbstractAddon> getRegisteredAddons();

}
