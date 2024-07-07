package com.github.bakuplayz.cropclick.menus.shared;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.spigotspin.menu.items.BackItem;
import lombok.AllArgsConstructor;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.GENERAL_BACK_ITEM_NAME;

@AllArgsConstructor
public final class CustomBackItem extends BackItem {

    private final CropClick plugin;


    @Override
    public void create() {
        super.create();
        setName(GENERAL_BACK_ITEM_NAME.get(plugin));
    }

}
