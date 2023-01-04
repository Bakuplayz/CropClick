package com.github.bakuplayz.cropclick.menu.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.AddonMenu;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.AddonsMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the JobsReborn menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class JobsRebornMenu extends AddonMenu {

    public JobsRebornMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.JOBS_REBORN_TITLE, "JobsReborn");
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(20, getCropsSettingsItem());

        setToggleItem();
        setWorldsItem();
        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new AddonsMenu(plugin, player));
        handleWorlds(clicked, new WorldsMenu(plugin, player, WorldMenuState.JOBS_REBORN));
        handleToggle(clicked, getToggleItem());

        if (clicked.equals(getCropsSettingsItem())) {
            new CropsMenu(plugin, player, CropMenuState.JOBS_REBORN).openMenu();
        }

        this.refreshMenu();
    }


    /**
     * Gets the crop settings {@link ItemStack item}.
     *
     * @return the crop settings item.
     */
    private @NotNull ItemStack getCropsSettingsItem() {
        return new ItemBuilder(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.ADDON_CROP_SETTINGS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDON_CROP_SETTINGS_ITEM_TIPS.getAsList(plugin))
                .toItemStack();
    }


    /**
     * Gets the toggle {@link ItemStack item}.
     *
     * @return the toggle item.
     */
    @Override
    protected @NotNull ItemStack getToggleItem() {
        return new ItemBuilder(Material.STONE_HOE)
                .setName(LanguageAPI.Menu.ADDON_JOBS_ITEM_NAME.get(plugin,
                        MessageUtils.getStatusMessage(plugin, isEnabled())
                ))
                .setLore(LanguageAPI.Menu.ADDON_JOBS_ITEM_TIPS.getAsList(plugin))
                .setMaterial(!isEnabled(), Material.GRAY_STAINED_GLASS_PANE)
                .toItemStack();
    }

}