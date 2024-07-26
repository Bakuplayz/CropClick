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
package com.github.bakuplayz.cropclick.menus.settings.particles;

import com.cryptomorin.xseries.particles.XParticle;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.sections.crops.ParticleConfigSection;
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
 * A class representing the Particles menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ParticlesMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, String> {

    private final Crop crop;

    private final ParticleConfigSection particleSection;


    public ParticlesMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(SOUNDS_TITLE.getTitle(plugin), plugin);
        this.particleSection = plugin.getCropsConfig().getParticleSection();
        this.crop = crop;
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler() {
        return new BasicPaginatedStateHandler(this);
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull String particle, int position) {
        return new ParticleItem(particle);
    }


    @Override
    public List<String> getPaginationItems() {
        return Arrays.stream(XParticle.values())
                .map(XParticle::name)
                .collect(Collectors.toList());
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull String particle, int position) {
        return (item, player) -> new ParticleMenu(plugin, crop, particle).open(player);
    }


    @AllArgsConstructor
    private class ParticleItem extends ClickableItem {

        @NotNull
        private final String particle;


        @Override
        public void create() {
            boolean isEnabled = particleSection.isEnabled(crop.getName(), particle);
            String status = MessageUtils.getStatusMessage(plugin, isEnabled);
            String name = MessageUtils.beautify(particle, true);

            setMaterial(XMaterial.FIREWORK_ROCKET);
            setName(PARTICLES_ITEM_NAME.get(plugin, name, status));
            setMaterial(isEnabled, XMaterial.LIME_STAINED_GLASS_PANE);

            if (isEnabled) {
                setLore(PARTICLES_ITEM_ORDER.get(plugin, particleSection.getOrder(crop.getName(), particle)));
            }
        }

    }

}
