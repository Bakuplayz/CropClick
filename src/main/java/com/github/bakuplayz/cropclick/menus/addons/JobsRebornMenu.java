package com.github.bakuplayz.cropclick.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.JobsRebornAddon;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractAddonMenu;
import com.github.bakuplayz.cropclick.menus.addons.jobsreborn.CropsMenu;
import com.github.bakuplayz.cropclick.menus.addons.jobsreborn.WorldsMenu;
import com.github.bakuplayz.cropclick.menus.addons.states.AddonMenuStateBuilder.AddonMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

public class JobsRebornMenu extends AbstractAddonMenu {

    public JobsRebornMenu(@NotNull CropClick plugin) {
        super(JOBS_REBORN_TITLE.getTitle(plugin), plugin, JobsRebornAddon.NAME);
    }


    @Override
    public void setItems() {
        setItem(20, new CropsItem(), (item, player) -> new CropsMenu(plugin).open(player));
        setItem(22, new ToggleItem(), (item, player) -> stateHandler.toggleAddon(), AddonMenuStateFlag.ADDON_STATE);
        setItem(24, new WorldsItem(), (item, player) -> new WorldsMenu(plugin).open(player));
        setItem(49, new CustomBackItem(plugin));
    }


    private final class ToggleItem extends AbstractToggleItem {

        @Override
        public void create() {
            setName(getName());
            setMaterial(getMaterial());
            setLore(ADDON_JOBS_ITEM_TIPS.getAsList(plugin));
            setMaterial(!addon.isEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        @Override
        protected String getName() {
            return ADDON_JOBS_ITEM_NAME.get(plugin, MessageUtils.getStatusMessage(plugin, addon.isEnabled()));
        }


        @Override
        protected XMaterial getMaterial() {
            return XMaterial.STONE_HOE;
        }

    }

}

