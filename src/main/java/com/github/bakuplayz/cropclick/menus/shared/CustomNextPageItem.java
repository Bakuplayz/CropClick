package com.github.bakuplayz.cropclick.menus.shared;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.spigotspin.SpigotSpin;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginationContext;
import com.github.bakuplayz.spigotspin.menu.items.paginated.NextPageItem;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.GENERAL_NEXT_PAGE_ITEM_NAME;

public final class CustomNextPageItem<S extends PaginatedMenuState> extends NextPageItem<S> {


    public CustomNextPageItem(@NotNull PaginationContext context) {
        super(context);
    }


    @Override
    public void create() {
        super.create();
        setName(GENERAL_NEXT_PAGE_ITEM_NAME.get((CropClick) SpigotSpin.Plugin.REF.getPlugin()));
    }

}
