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

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AutofarmAPI {

    /**
     * Finds the autofarm based on the provided block.
     *
     * @param block the block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    @Nullable
    Autofarm findAutofarm(@NotNull Block block);


    /**
     * Gets all the autofarms.
     *
     * @return the found autofarms.
     */
    @NotNull
    List<Autofarm> getAutofarms();


    /**
     * Checks whether the autofarms are enabled.
     *
     * @return true if they are, otherwise false.
     */
    boolean isEnabled();

}
