package com.github.bakuplayz.cropclick.menus.addons.auraskills;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropsMenu;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.CROPS_ITEM_AURA_SKILLS_EXPERIENCE;

public final class CropsMenu extends AbstractCropsMenu {


    public CropsMenu(@NotNull CropClick plugin) {
        super(plugin, (crop) -> getItemLore(plugin, crop));
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
        return CROPS_ITEM_AURA_SKILLS_EXPERIENCE.getAsList(plugin, section.getSkillsExperience(crop.getName()));
    }

}
