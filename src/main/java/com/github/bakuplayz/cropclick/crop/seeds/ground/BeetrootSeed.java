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

package com.github.bakuplayz.cropclick.crop.seeds.ground;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents a beetroot seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Seed
 * @since 2.0.0
 */
public final class BeetrootSeed extends BaseSeed {

    public BeetrootSeed(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link BaseSeed seed}.
     *
     * @return the name of the seed.
     */
    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "beetrootSeed";
    }


    /**
     * Gets the {@link BaseSeed seed's} drop.
     *
     * @return the seed's drop.
     */
    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.BEETROOT_SEEDS,
                seedSection.getDropName(getName()),
                seedSection.getDropAmount(getName(), 2),
                seedSection.getDropChance(getName(), 80)
        );
    }


    /**
     * Gets the {@link Seed seed's} menu type.
     *
     * @return the seed's menu type.
     */
    @Override
    public @NotNull Material getMenuType() {
        return Material.BEETROOT_SEEDS;
    }

}