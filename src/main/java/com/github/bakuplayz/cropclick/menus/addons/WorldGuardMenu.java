package com.github.bakuplayz.cropclick.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.WorldGuardAddon;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractAddonMenu;
import com.github.bakuplayz.cropclick.menus.addons.states.AddonMenuStateBuilder.AddonMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.addons.worldguard.WorldsMenu;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

public final class WorldGuardMenu extends AbstractAddonMenu {

    public WorldGuardMenu(@NotNull CropClick plugin) {
        super(WORLD_GUARD_TITLE.getTitle(plugin), plugin, WorldGuardAddon.NAME);
    }


    @Override
    public void setItems() {
        setItem(21, new ToggleItem(), (item, player) -> stateHandler.toggleAddon(), AddonMenuStateFlag.ADDON_STATE);
        setItem(23, new WorldsItem(), (item, player) -> new WorldsMenu(plugin).open(player));
        setItem(49, new CustomBackItem(plugin));
    }


    private final class ToggleItem extends AbstractToggleItem {


        @Override
        public void create() {
            setName(getName());
            setMaterial(getMaterial());
            setLore(ADDON_GUARD_ITEM_TIPS.getAsList(plugin));
            setMaterial(!addon.isEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        @Override
        protected String getName() {
            return ADDON_GUARD_ITEM_NAME.get(plugin, MessageUtils.getStatusMessage(plugin, addon.isEnabled()));
        }


        @Override
        protected XMaterial getMaterial() {
            return XMaterial.GRASS_BLOCK;
        }

    }

}
