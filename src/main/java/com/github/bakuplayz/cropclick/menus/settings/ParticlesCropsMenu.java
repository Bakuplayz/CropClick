package com.github.bakuplayz.cropclick.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropsMenu;
import com.github.bakuplayz.cropclick.menus.settings.particles.ParticlesMenu;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.CROPS_ITEM_PARTICLES;

/*
 * A class representing the Particles menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ParticlesCropsMenu extends AbstractCropsMenu {

    public ParticlesCropsMenu(@NotNull CropClick plugin) {
        super(plugin, (crop) -> getItemLore(plugin, crop));
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull Crop crop, int position) {
        return (item, player) -> new ParticlesMenu(plugin, crop).open(player);
    }


    @NotNull
    @Unmodifiable
    private static List<String> getItemLore(@NotNull CropClick plugin, @NotNull Crop crop) {
        return CROPS_ITEM_PARTICLES.getAsList(plugin,
                plugin.getCropsConfig().getParticleSection().getAmountOfParticles(crop.getName())
        );
    }


}
