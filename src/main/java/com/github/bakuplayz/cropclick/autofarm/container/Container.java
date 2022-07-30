package com.github.bakuplayz.cropclick.autofarm.container;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class Container {

    private final @Getter Inventory inventory;

    private final @Getter ContainerType type;


    public Container(@NotNull Inventory inventory, @NotNull ContainerType type) {
        this.inventory = inventory;
        this.type = type;
    }

}