/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
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

package com.github.bakuplayz.cropclick.crops.ground;

import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractGroundCrop;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class that represents the carrot crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @see AbstractCrop
 * @since 2.0.0
 */
public final class Carrot extends AbstractGroundCrop {

    public Carrot(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link Crop crop}.
     *
     * @return the crop's name.
     */
    @NotNull
    @Override
    public String getName() {
        return "carrot";
    }


    /**
     * Gets the drop of the {@link Crop crop}.
     *
     * @return the crop's drop.
     */
    @NotNull
    @Override
    public Drop getDrop() {
        return new Drop(XMaterial.CARROT,
                cropSection.getDropName(getName()),
                cropSection.getDropAmount(getName(), 4),
                cropSection.getDropChance(getName(), 80)
        );
    }


    /**
     * Gets the seed of the {@link Crop crop}.
     *
     * @return the crop's seed (default: null).
     */
    @Nullable
    @Override
    public Seed getSeed() {
        return null;
    }


    /**
     * Gets the clickable type of the {@link Crop crop}.
     *
     * @return the crop's clickable type.
     */
    @NotNull
    @Override
    public XMaterial getClickableType() {
        return XMaterial.CARROTS;
    }


    /**
     * Gets the menu type of the {@link Crop crop}.
     *
     * @return the crop's menu type.
     */
    @NotNull
    @Override
    public XMaterial getMenuType() {
        return XMaterial.CARROT;
    }


}