package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Crop
 * @since 2.0.0
 */
public abstract class TallCrop extends BaseCrop {

    public TallCrop(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig);
    }


    @Override
    public int getHarvestAge() {
        return 2;
    }


    @Override
    public int getCurrentAge(@NotNull Block clickedBlock) {
        int minHeight = clickedBlock.getLocation().getBlockY();
        int maxHeight = clickedBlock.getWorld().getMaxHeight();

        int height = 0;
        for (int y = minHeight; y <= maxHeight; ++y) {
            Block currentBlock = clickedBlock.getWorld().getBlockAt(
                    clickedBlock.getX(),
                    y,
                    clickedBlock.getZ()
            );

            if (BlockUtils.isAir(currentBlock)) {
                break;
            }

            if (BlockUtils.isSameType(clickedBlock, currentBlock)) {
                ++height;
            }
        }

        return height;
    }


    @Override
    public @Nullable Seed getSeed() {
        return null;
    }


    @Override
    public boolean hasSeed() {
        return false;
    }


    @Override
    public void replant(@NotNull Block clickedBlock) {
        int height = getCurrentAge(clickedBlock);
        for (int i = height; i > 0; --i) {
            Block currentBlock = clickedBlock.getWorld().getBlockAt(
                    clickedBlock.getX(),
                    clickedBlock.getY() + i,
                    clickedBlock.getZ()
            );
            currentBlock.setType(Material.AIR);
        }

        if (!shouldReplant()) {
            clickedBlock.setType(Material.AIR);
        }
    }


    /**
     * Harvest all the crops, stacked on each other, starting from the top.
     *
     * @param player       The player who is harvesting the crop
     * @param clickedBlock The block that was clicked.
     * @param crop         The crop that is being harvested.
     */
    public void harvestAll(@NotNull Player player, @NotNull Block clickedBlock, @NotNull Crop crop) {
        int height = getCurrentAge(clickedBlock);
        int actualHeight = getActualHeight(crop, height);
        for (int i = actualHeight; i > 0; --i) {
            crop.harvest(player);
        }
    }


    /**
     * Harvest all the crops, stacked on each other, starting from the top.
     *
     * @param container    The container that the crop is in.
     * @param clickedBlock The block that was clicked.
     * @param crop         The crop that is being harvested.
     */
    public void harvestAll(@NotNull Container container, @NotNull Block clickedBlock, @NotNull Crop crop) {
        int height = getCurrentAge(clickedBlock);
        int actualHeight = getActualHeight(crop, height);
        for (int i = actualHeight; i > 0; --i) {
            crop.harvest(container);
        }
    }


    /**
     * If the crop should be replanted, return the height minus one, otherwise return the height.
     *
     * @param crop   The crop to be planted
     * @param height The height of the crop.
     *
     * @return The height of the crop.
     */
    public int getActualHeight(@NotNull Crop crop, int height) {
        return crop.shouldReplant() ? height - 1 : height;
    }

}