package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.menus.addons.states.AddonMenuStateBuilder;
import com.github.bakuplayz.cropclick.menus.addons.states.AddonMenuStateBuilder.AddonMenuState;
import com.github.bakuplayz.cropclick.menus.addons.states.AddonMenuStateBuilder.AddonMenuStateHandler;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

public abstract class AbstractAddonMenu extends AbstractStateMenu<AddonMenuState, AddonMenuStateHandler> {


    protected final CropClick plugin;


    protected final Addon addon;


    public AbstractAddonMenu(@NotNull String title, @NotNull CropClick plugin, @NotNull String addonName) {
        super(title);

        this.plugin = plugin;
        this.addon = plugin.getAddonManager().findByName(addonName);
    }


    @Override
    public AddonMenuStateHandler createStateHandler() {
        return AddonMenuStateBuilder.createStateHandler(this, plugin, addon);
    }


    public final class WorldsItem extends ClickableItem {

        @Override
        public void create() {
            setMaterial(XMaterial.GRASS_BLOCK);
            setName(ADDON_WORLDS_ITEM_NAME.get(plugin));
            setLore(ADDON_WORLDS_ITEM_TIPS.getAsAppendList(plugin, ADDON_WORLDS_ITEM_STATUS.get(
                    plugin, addon == null ? 0 : addon.getAmountOfBanished()))
            );
        }

    }

    protected abstract static class AbstractToggleItem extends ClickableStateItem<AddonMenuState> {

        @Override
        public void update(@NotNull AddonMenuState state, int flag) {
            setMaterial(state.isAddonEnabled() ? getMaterial() : XMaterial.GRAY_STAINED_GLASS_PANE);
            setName(getName());
        }


        protected abstract String getName();


        protected abstract XMaterial getMaterial();

    }

    public final class CropsItem extends ClickableItem {


        @Override
        public void create() {
            setMaterial(XMaterial.WHEAT);
            setName(ADDON_CROP_SETTINGS_ITEM_NAME.get(plugin));
            setLore(ADDON_CROP_SETTINGS_ITEM_TIPS.getAsList(plugin));
        }

    }


}
