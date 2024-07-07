package com.github.bakuplayz.cropclick.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropsMenu;
import com.github.bakuplayz.cropclick.menus.settings.names.NameMenu;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.CROPS_ITEM_DROP_NAME;

/**
 * A class representing the Names menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class NamesCropsMenu extends AbstractCropsMenu {

    public NamesCropsMenu(@NotNull CropClick plugin) {
        super(plugin, (crop) -> getItemLore(plugin, crop));
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull Crop crop, int position) {
        return (item, player) -> new NameMenu(plugin, crop).open(player);
    }


    @NotNull
    @Unmodifiable
    private static List<String> getItemLore(@NotNull CropClick plugin, @NotNull Crop crop) {
        return CROPS_ITEM_DROP_NAME.getAsList(plugin,
                plugin.getCropsConfig().getCropSection().getDropName(crop.getName())
        );
    }

}
