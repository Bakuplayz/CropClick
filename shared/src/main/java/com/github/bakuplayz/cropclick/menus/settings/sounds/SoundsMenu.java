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
package com.github.bakuplayz.cropclick.menus.settings.sounds;

import com.cryptomorin.xseries.XSound;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.cropclick.common.MessageUtils;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Sounds menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SoundsMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, String> {

    private final Crop crop;

    private final SoundConfigSection soundSection;


    public SoundsMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(SOUNDS_TITLE.getTitle(plugin), plugin);
        this.soundSection = plugin.getCropsConfig().getSoundSection();
        this.crop = crop;
    }


    @Override
    public List<String> getPaginationItems() {
        return Arrays.stream(XSound.values())
                .map(XSound::name)
                .collect(Collectors.toList());
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler() {
        return new BasicPaginatedStateHandler(this);
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull String sound, int position) {
        return new SoundItem(sound);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull String sound, int position) {
        return (item, player) -> new SoundMenu(plugin, crop, sound).open(player);
    }


    @AllArgsConstructor
    private class SoundItem extends ClickableItem {

        @NotNull
        private final String sound;


        @Override
        public void create() {
            boolean isEnabled = soundSection.isEnabled(crop.getName(), sound);
            String status = MessageUtils.getStatusMessage(plugin, isEnabled);
            String name = MessageUtils.beautify(sound, true);

            setMaterial(XMaterial.NOTE_BLOCK);
            setName(SOUNDS_ITEM_NAME.get(plugin, name, status));
            setMaterial(isEnabled, XMaterial.LIME_STAINED_GLASS_PANE);

            if (isEnabled) {
                setLore(SOUNDS_ITEM_ORDER.get(plugin, soundSection.getOrder(crop.getName(), sound)));
            }
        }

    }

}
