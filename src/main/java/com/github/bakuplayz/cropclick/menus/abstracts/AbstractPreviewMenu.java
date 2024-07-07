package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPlainMenu;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.utils.CollectionUtils;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class AbstractPreviewMenu extends AbstractPlainMenu {

    protected final Inventory previewInventory;


    public AbstractPreviewMenu(@NotNull String title, @NotNull Inventory previewInventory) {
        super(title);
        this.previewInventory = previewInventory;
    }


    @Override
    public void setItems() {
        Arrays.stream(previewInventory.getContents()).map(CollectionUtils.toIndexed())
                .forEach((indexed) -> setItem(indexed.getIndex(), new PreviewItem(indexed.getValue())));
    }


    @AllArgsConstructor
    private final static class PreviewItem extends Item {

        private final ItemStack item;


        @Override
        public void create() {
        }


        @NotNull
        @Override
        public ItemStack asItemStack() {
            return item;
        }

    }

}
