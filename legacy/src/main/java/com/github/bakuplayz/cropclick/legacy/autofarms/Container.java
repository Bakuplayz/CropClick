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
package com.github.bakuplayz.cropclick.legacy.autofarms;

import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Getter
@NoArgsConstructor
public final class Container implements ContainerComponent {

    private Inventory inventory;

    Container(@NotNull Inventory inventory) {
        this.inventory = inventory;
    }

    @NotNull
    @Override
    public ContainerComponent of(@NotNull Player player) {
        return new Container(player.getInventory());
    }


    @Nullable
    @Override
    public ContainerComponent of(@NotNull Block block) {
          /*
        if (BlockUtils.isDoubleChest(block)) {
            return new Container(
                    ((Chest) blockState).getInventory(),
                    ContainerType.DOUBLE_CHEST
            );
        } */
        BlockState state = block.getState();

        if (state instanceof Chest) {
            return new Container(((Chest) state).getInventory());
        }

        // TODO: Add version check before this...
        if (state instanceof org.bukkit.block.ShulkerBox) {
            return new Container(((org.bukkit.block.ShulkerBox) state).getInventory());
        }

       return null;
    }
}
