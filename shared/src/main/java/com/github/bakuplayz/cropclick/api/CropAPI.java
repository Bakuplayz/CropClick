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

import com.github.bakuplayz.cropclick.common.exceptions.CropTypeDuplicateException;
import com.github.bakuplayz.cropclick.crops.Crop;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CropAPI {

    /**
     * Registers the provided crop.
     *
     * @param crop the crop to register.
     *
     * @throws CropTypeDuplicateException the exception thrown when the crop is already registered.
     */
    void registerCrop(@NotNull Crop crop) throws CropTypeDuplicateException;


    /**
     * Unregister the provided crop.
     *
     * @param crop the crop to unregister.
     */
    void unregisterCrop(@NotNull Crop crop);


    /**
     * Finds the crop based on the provided block.
     *
     * @param block the block to base the findings on.
     *
     * @return the found crop, otherwise false.
     */
    @Nullable
    Crop findByBlock(@NotNull Block block);


    /**
     * Finds the crop based on the provided name.
     *
     * @param name the name to base the findings on.
     *
     * @return the found crop, otherwise false.
     */
    @Nullable
    Crop findByName(@NotNull String name);


    /**
     * Gets the registered crops.
     *
     * @return the registered crops.
     */
    @NotNull
    List<Crop> getRegisteredCrops();


    /**
     * Checks whether the provided block is a crop.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    boolean isCrop(@NotNull Block block);

}
