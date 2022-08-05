package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.autofarm.container.ContainerType;
import com.github.bakuplayz.cropclick.autofarm.metadata.AutofarmMetadata;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AutofarmUtils {

    public static final String FARMERID_META = "farmerID";


    /**
     * If the block is a chest or shulker box, return true.
     *
     * @param block The block to check.
     *
     * @return A boolean value.
     */
    public static boolean isContainer(@NotNull Block block) {
        if (BlockUtils.isSameType(block, Material.CHEST)) {
            return true;
        }
        return block.getType().name().contains("SHULKER");
    }


    /**
     * Returns true if the block is a container, false otherwise.
     *
     * @param block           The block to check.
     * @param onlyDoubleChest If true, only double chests will be considered containers.
     *
     * @return A boolean value.
     */
    public static boolean isContainer(@NotNull Block block, boolean onlyDoubleChest) {
        if (onlyDoubleChest) {
            return BlockUtils.isDoubleChest(block);
        }
        return isContainer(block);
    }


    /**
     * If the block is a chest, double chest, or shulker box, return a new Container object with the block's inventory and
     * the appropriate container type.
     *
     * @param block The block to get the container from.
     *
     * @return A Container object
     */
    public static @Nullable Container getContainer(@NotNull Block block) {
        BlockState blockState = block.getState();

        if (BlockUtils.isDoubleChest(block)) {
            return new Container(((Chest) blockState).getInventory(), ContainerType.DOUBLE_CHEST);
        }

        if (blockState instanceof Chest) {
            return new Container(((Chest) blockState).getInventory(), ContainerType.CHEST);
        }

        if (!VersionUtils.supportsShulkers()) {
            return null;
        }

        if (blockState instanceof ShulkerBox) {
            return new Container(((ShulkerBox) blockState).getInventory(), ContainerType.SHULKER);
        }

        return null;
    }


    /**
     * Returns true if the given block is a dispenser.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public static boolean isDispenser(@NotNull Block block) {
        return BlockUtils.isSameType(block, Material.DISPENSER);
    }


    /**
     * Returns the Dispenser object associated with the given Block.
     *
     * @param block The block to get the dispenser from.
     *
     * @return A Dispenser object.
     */
    public static @NotNull Dispenser getDispenser(@NotNull Block block) {
        return (Dispenser) block.getState();
    }


    /**
     * Returns true if the block is a crop.
     *
     * @param manager The CropManager instance.
     * @param block   The block to check
     *
     * @return A boolean value.
     */
    public static boolean isCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.isCrop(block);
    }


    /**
     * Returns the crop that the given block is a part of, or null if the block is not a part of a crop.
     *
     * @param manager The CropManager instance.
     * @param block   The block that you want to get the crop from.
     *
     * @return A Crop object
     */
    public static @Nullable Crop getCrop(@NotNull CropManager manager, @NotNull Block block) {
        return manager.findByBlock(block);
    }


    /**
     * It sets the metadata of the dispenser, container, and crop to the farmer's id.
     *
     * @param plugin   The plugin instance
     * @param autofarm The autofarm object that you want to set the metadata for.
     */
    public static void setMeta(@NotNull CropClick plugin, @NotNull Autofarm autofarm) {
        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        AutofarmMetadata farmerMeta = new AutofarmMetadata(plugin, autofarm::getFarmerID);

        DoublyLocation doublyLocation = LocationUtils.getAsDoubly(container);
        if (doublyLocation != null) {
            Block singly = doublyLocation.getSingly().getBlock();
            Block doubly = doublyLocation.getDoubly().getBlock();

            singly.setMetadata(FARMERID_META, farmerMeta);
            doubly.setMetadata(FARMERID_META, farmerMeta);

            autofarm.setContainerLocation(doublyLocation);
        } else {
            container.setMetadata(FARMERID_META, farmerMeta);
        }

        dispenser.setMetadata(FARMERID_META, farmerMeta);
        crop.setMetadata(FARMERID_META, farmerMeta);
    }


    /**
     * Remove the farmerID metadata from the dispenser, container, and crop.
     *
     * @param plugin   The main class of the plugin.
     * @param autofarm The autofarm object that you want to remove the metadata from.
     */
    public static void removeMeta(@NotNull CropClick plugin, @NotNull Autofarm autofarm) {
        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        DoublyLocation doublyContainer = LocationUtils.getAsDoubly(container);
        if (doublyContainer != null) {
            Block singly = doublyContainer.getSingly().getBlock();
            Block doubly = doublyContainer.getSingly().getBlock();

            singly.removeMetadata(FARMERID_META, plugin);
            doubly.removeMetadata(FARMERID_META, plugin);
        } else {
            container.removeMetadata(FARMERID_META, plugin);
        }

        dispenser.removeMetadata(FARMERID_META, plugin);
        crop.removeMetadata(FARMERID_META, plugin);
    }


    /**
     * If the dispenser, container, and crop all have the farmerID present, return true.
     *
     * @param autofarm The autofarm object that you want to check.
     *
     * @return A boolean value.
     */
    public static boolean hasIDPresent(@NotNull Autofarm autofarm) {
        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        if (!componentHasFarmerID(dispenser)) {
            return false;
        }

        if (!componentHasFarmerID(crop)) {
            return false;
        }

        DoublyLocation doublyContainer = LocationUtils.getAsDoubly(container);
        if (doublyContainer != null) {
            Block singly = doublyContainer.getSingly().getBlock();
            Block doubly = doublyContainer.getSingly().getBlock();

            if (!componentHasFarmerID(singly)) {
                return false;
            }

            if (!componentHasFarmerID(doubly)) {
                return false;
            }
        }

        return componentHasFarmerID(container);
    }


    /**
     * Checks if the given component (i.e. chest) has the cached farmerID.
     *
     * @param block The block to check
     *
     * @return A boolean value.
     */
    public static boolean componentHasFarmerID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(FARMERID_META);
        return block.hasMetadata(FARMERID_META) && !metas.isEmpty();
    }


    /**
     * Get the farmerID of the given block, or null if it doesn't have one.
     *
     * @param block The block to get the farmer ID from.
     *
     * @return The farmerID of the block.
     */
    public static @Nullable String getFarmerID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(FARMERID_META);
        return metas.isEmpty() ? null : metas.get(0).asString();
    }

}