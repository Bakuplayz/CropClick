package com.github.bakuplayz.cropclick.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractWorldsMenu;
import com.github.bakuplayz.cropclick.menus.settings.worlds.WorldMenu;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.WORLDS_ITEM_STATUS;

/**
 * A class representing the Worlds menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class WorldsMenu extends AbstractWorldsMenu {

    public WorldsMenu(@NotNull CropClick plugin) {
        super(plugin, (world) -> WORLDS_ITEM_STATUS.getAsList(plugin, world.isBanished()));
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull FarmWorld world, int position) {
        return (item, player) -> new WorldMenu(plugin, world).open(player);
    }

}
