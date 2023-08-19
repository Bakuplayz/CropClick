/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
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

package com.github.bakuplayz.cropclick.listeners.entity;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
import com.github.bakuplayz.cropclick.menu.menus.links.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * A listener handling all the {@link Entity} destroy {@link Autofarm} events.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class EntityDestroyAutofarmListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final AutofarmManager autofarmManager;


    public EntityDestroyAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.isDebugging = plugin.isDebugging();
        this.logger = plugin.getLogger();
    }


    /**
     * Handles all the {@link Entity entity} explode {@link Autofarm autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplodeAutofarm(@NotNull EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            return;
        }

        List<Block> explodedBlocks = event.blockList();
        List<Block> explodedComponents = getExplodedComponents(explodedBlocks);
        for (Block component : explodedComponents) {
            Autofarm autofarm = autofarmManager.findAutofarm(component);

            if (autofarm == null) {
                continue;
            }

            if (isDebugging) {
                logger.info(String.format(
                        "%s (Entity): Called the destroy autofarm event!",
                        event.getEntity().getName())
                );
            }

            Bukkit.getPluginManager().callEvent(
                    new AutofarmUnlinkEvent(autofarm)
            );
        }
    }


    /**
     * Gets all the exploded {@link Component autofarm components}.
     *
     * @param explodedBlocks the list of exploded blocks.
     *
     * @return the components that exploded.
     */
    private @NotNull List<Block> getExplodedComponents(@NotNull List<Block> explodedBlocks) {
        return explodedBlocks.stream()
                             .filter(autofarmManager::isComponent)
                             .collect(Collectors.toList());
    }

}