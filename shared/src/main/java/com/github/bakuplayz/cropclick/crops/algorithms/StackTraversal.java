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
package com.github.bakuplayz.cropclick.crops.algorithms;

import lombok.AllArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;

public final class StackTraversal implements TraversalAlgorithm<Integer, StackTraversal.Input> {


    @NotNull
    @Override
    public Integer traverse(@NotNull Input input) {
        Stack<Block> stack = new Stack<>();
        stack.push(input.block);

        while (!stack.isEmpty()) {
            Block lastItem = stack.pop();

            if (!input.filter.apply(lastItem)) {
                continue;
            }

            input.resulting.add(lastItem);
            stack.push(lastItem.getRelative(BlockFace.UP));
            stack.push(lastItem.getRelative(BlockFace.EAST));
            stack.push(lastItem.getRelative(BlockFace.SOUTH));
            stack.push(lastItem.getRelative(BlockFace.WEST));
            stack.push(lastItem.getRelative(BlockFace.NORTH));
        }

        return input.resulting.size() + 1;
    }


    @AllArgsConstructor
    public static class Input {

        @NotNull
        private final Block block;

        @NotNull
        private final Function<Block, Boolean> filter;

        @NotNull
        private final List<Block> resulting;

    }

}
