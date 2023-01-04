package com.github.bakuplayz.cropclick.events.autofarm.harvest;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.harvest.HarvestCropEvent;
import lombok.Getter;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Autofarm} harvests a {@link Crop crop}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmHarvestCropEvent extends HarvestCropEvent {

    private final @Getter Autofarm autofarm;


    public AutofarmHarvestCropEvent(@NotNull Crop crop, @NotNull Block block, @NotNull Autofarm autofarm) {
        super(crop, block);
        this.autofarm = autofarm;
    }

}