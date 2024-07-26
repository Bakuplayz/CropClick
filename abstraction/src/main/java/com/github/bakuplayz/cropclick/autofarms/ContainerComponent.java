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
package com.github.bakuplayz.cropclick.autofarms;

import com.cryptomorin.xseries.XMaterial;
import com.github.bakuplayz.cropclick.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContainerComponent extends Component {

    static XMaterial @NotNull [] getTypes() {
        return new XMaterial[]{
                XMaterial.CHEST,
                XMaterial.BARREL,
                XMaterial.SHULKER_BOX,
                XMaterial.WHITE_SHULKER_BOX,
                XMaterial.ORANGE_SHULKER_BOX,
                XMaterial.MAGENTA_SHULKER_BOX,
                XMaterial.LIGHT_BLUE_SHULKER_BOX,
                XMaterial.YELLOW_SHULKER_BOX,
                XMaterial.LIME_SHULKER_BOX,
                XMaterial.PINK_SHULKER_BOX,
                XMaterial.GRAY_SHULKER_BOX,
                XMaterial.LIGHT_GRAY_SHULKER_BOX,
                XMaterial.CYAN_SHULKER_BOX,
                XMaterial.PURPLE_SHULKER_BOX,
                XMaterial.BLUE_SHULKER_BOX,
                XMaterial.BROWN_SHULKER_BOX,
                XMaterial.GREEN_SHULKER_BOX,
                XMaterial.RED_SHULKER_BOX,
                XMaterial.BLACK_SHULKER_BOX
        };
    }


    @NotNull
    Inventory getInventory();


    @NotNull
    ContainerComponent of(@NotNull Player player);


    @Nullable
    ContainerComponent of(@NotNull Block block);

}
