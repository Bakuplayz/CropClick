package com.github.bakuplayz.cropclick.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractLinkMenu;
import com.github.bakuplayz.cropclick.menus.links.states.LinkMenuStateBuilder.LinkContext;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.CROP_LINK_TITLE;

public class CropLinkMenu extends AbstractLinkMenu {

    public CropLinkMenu(@NotNull CropClick plugin, Autofarm autofarm, @NotNull Block block, boolean showBackButton) {
        super(plugin, CROP_LINK_TITLE.getTitle(plugin), autofarm, block, showBackButton);
    }


    @Override
    public LinkContext getContext() {
        return LinkContext.CROP;
    }

}
