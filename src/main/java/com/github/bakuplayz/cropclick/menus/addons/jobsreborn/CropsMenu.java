package com.github.bakuplayz.cropclick.menus.addons.jobsreborn;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropsMenu;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

public final class CropsMenu extends AbstractCropsMenu {

    public CropsMenu(@NotNull CropClick plugin) {
        super(plugin, crop -> getItemLore(plugin, crop));
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull Crop crop, int position) {
        return (item, player) -> new CropMenu(plugin, crop).open(player);
    }


    @NotNull
    @Unmodifiable
    private static List<String> getItemLore(@NotNull CropClick plugin, @NotNull Crop crop) {
        AddonConfigSection section = plugin.getCropsConfig().getAddonSection();

        return Arrays.asList(
                CROPS_ITEM_JOBS_MONEY.get(plugin, section.getJobsMoney(crop.getName())),
                CROPS_ITEM_JOBS_POINTS.get(plugin, section.getJobsPoints(crop.getName())),
                CROPS_ITEM_JOBS_EXPERIENCE.get(plugin, section.getJobsExperience(crop.getName()))
        );
    }

}
