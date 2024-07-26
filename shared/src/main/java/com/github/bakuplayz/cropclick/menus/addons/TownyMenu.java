/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.bakuplayz.cropclick.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.TownyAddon;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractAddonMenu;
import com.github.bakuplayz.cropclick.menus.abstracts.states.AddonMenuStateBuilder.AddonMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.addons.towny.WorldsMenu;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.common.MessageUtils;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Towny menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class TownyMenu extends AbstractAddonMenu {

    public TownyMenu(@NotNull CropClick plugin) {
        super(TOWNY_TITLE.getTitle(plugin), plugin, TownyAddon.NAME);
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
            setLore(ADDON_TOWNY_ITEM_TIPS.getAsList(plugin));
            setMaterial(!getState().isAddonEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        @Override
        protected String getName() {
            return ADDON_TOWNY_ITEM_NAME.get(plugin, MessageUtils.getStatusMessage(plugin, getState().isAddonEnabled()));
        }


        @Override
        protected XMaterial getMaterial() {
            return XMaterial.OAK_FENCE_GATE;
        }

    }

}
