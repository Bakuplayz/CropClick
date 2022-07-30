package com.github.bakuplayz.cropclick.crop.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.templates.GroundCrop;
import com.github.bakuplayz.cropclick.crop.crops.templates.VanillaGroundCrop;
import com.github.bakuplayz.cropclick.crop.seeds.WheatSeed;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see GroundCrop
 * @since 1.6.0
 */
public final class Wheat extends VanillaGroundCrop {

    public Wheat(@NotNull CropsConfig config) {
        super(config);
    }


    @Override
    @Contract(pure = true)
    public @NotNull String getName() {
        return "wheat";
    }


    @Override
    public @NotNull Drop getDrop() {
        return new Drop(Material.WHEAT,
                cropsConfig.getCropDropName(getName()),
                cropsConfig.getCropDropAmount(getName()),
                cropsConfig.getCropDropChance(getName())
        );
    }


    @Override
    @Contract(value = " -> new", pure = true)
    public @NotNull Seed getSeed() {
        return new WheatSeed(cropsConfig);
    }


    @Override
    public @NotNull Material getClickableType() {
        return Material.CROPS;
    }


    @Override
    public @NotNull Material getMenuType() {
        return Material.WHEAT;
    }

}