package com.github.bakuplayz.cropclick.menus.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPreviewMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.DISPENSER_PREVIEW_TITLE;

public final class PreviewDispenserMenu extends AbstractPreviewMenu {


    public PreviewDispenserMenu(@NotNull CropClick plugin, @NotNull Autofarm autofarm, @NotNull Inventory inventory) {
        super(DISPENSER_PREVIEW_TITLE.getTitle(plugin, autofarm.getShortenedID()), inventory);
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DISPENSER;
    }


}
