package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropsMenu;
import com.github.bakuplayz.cropclick.menus.crops.CropMenu;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.CROPS_ITEM_DROP_VALUE;

/**
 * A class representing the Crops menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
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
        return CROPS_ITEM_DROP_VALUE.getAsList(plugin, crop.getDrop().getAmount());
    }

}
