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
package com.github.bakuplayz.cropclick.addons.offlinegrowth;

import com.github.bakuplayz.cropclick.addons.AddonFunctionality;
import com.github.bakuplayz.cropclick.crops.Crop;
import es.yellowzaki.offlinegrowth.api.OfflineGrowthAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class OfflineGrowthFunctionality implements AddonFunctionality {


    /**
     * Registers the {@link Crop crop} at the {@link Location provided location}.
     *
     * @param location the location of the crop to register.
     */
    public void registerCrop(@NotNull Location location) {
        OfflineGrowthAPI.addPlant(location);
    }


    /**
     * Unregisters the {@link Crop crop} at the {@link Location provided location}.
     *
     * @param location the location of the crop to unregister.
     */
    public void unregisterCrop(@NotNull Location location) {
        OfflineGrowthAPI.removePlant(location);
    }

}
