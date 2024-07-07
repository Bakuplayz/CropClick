package com.github.bakuplayz.cropclick.menus.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPreviewMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.CONTAINER_PREVIEW_TITLE;

public final class PreviewContainerMenu extends AbstractPreviewMenu {


    public PreviewContainerMenu(@NotNull CropClick plugin, @NotNull Autofarm autofarm, @NotNull Inventory inventory) {
        super(CONTAINER_PREVIEW_TITLE.getTitle(plugin, autofarm.getShortenedID()), inventory);
    }


    @Override
    public SizeType getSizeType() {
        if (previewInventory instanceof DoubleChestInventory) {
            return SizeType.DOUBLE_CHEST;
        }

        return SizeType.CHEST;
    }


}
