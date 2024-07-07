package com.github.bakuplayz.cropclick.menus.addons.residence;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.ResidenceAddon;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractWorldsMenu;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.WORLDS_ITEM_RESIDENCE_TIPS;
import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.WORLDS_ITEM_STATUS;

public final class WorldsMenu extends AbstractWorldsMenu {


    public WorldsMenu(@NotNull CropClick plugin) {
        super(plugin, (world) -> getItemLore(plugin, world));
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull FarmWorld world, int position) {
        return (item, player) -> stateHandler.toggleWorld(world, ResidenceAddon.NAME, position);
    }


    @NotNull
    @Unmodifiable
    private static List<String> getItemLore(@NotNull CropClick plugin, @NotNull FarmWorld world) {
        boolean isBanished = world.isBanishedAddon(plugin.getAddonManager(), ResidenceAddon.NAME);
        return WORLDS_ITEM_RESIDENCE_TIPS.getAsAppendList(plugin, WORLDS_ITEM_STATUS.get(plugin, isBanished));
    }

}
