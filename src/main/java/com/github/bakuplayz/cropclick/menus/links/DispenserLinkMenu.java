package com.github.bakuplayz.cropclick.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractLinkMenu;
import com.github.bakuplayz.cropclick.menus.links.states.LinkMenuStateBuilder.LinkContext;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.DISPENSER_LINK_TITLE;

public final class DispenserLinkMenu extends AbstractLinkMenu {

    public DispenserLinkMenu(@NotNull CropClick plugin, Autofarm autofarm, @NotNull Block block, boolean showBackButton) {
        super(plugin, DISPENSER_LINK_TITLE.getTitle(plugin), autofarm, block, showBackButton);
    }


    @Override
    public LinkContext getContext() {
        return LinkContext.DISPENSER;
    }

}
