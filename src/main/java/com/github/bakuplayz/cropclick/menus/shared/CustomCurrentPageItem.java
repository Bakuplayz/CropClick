package com.github.bakuplayz.cropclick.menus.shared;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.spigotspin.SpigotSpin;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginationContext;
import com.github.bakuplayz.spigotspin.menu.items.paginated.CurrentPageItem;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.GENERAL_CURRENT_PAGE_ITEM_NAME;

public final class CustomCurrentPageItem<S extends PaginatedMenuState> extends CurrentPageItem<S> {

    public CustomCurrentPageItem(@NotNull PaginationContext context) {
        super(context);
    }


    @Override
    public void create() {
        super.create();
        setName(getName(1));
    }


    public void update(@NotNull S state, int flag) {
        setName(getName(state.getDisplayPage()));
    }


    @NotNull
    private String getName(int displayPage) {
        return GENERAL_CURRENT_PAGE_ITEM_NAME.get((CropClick) SpigotSpin.Plugin.REF.getPlugin(), displayPage);
    }


}
