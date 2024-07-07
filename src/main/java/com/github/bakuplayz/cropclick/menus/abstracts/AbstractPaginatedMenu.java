package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.menus.shared.CustomCurrentPageItem;
import com.github.bakuplayz.cropclick.menus.shared.CustomNextPageItem;
import com.github.bakuplayz.cropclick.menus.shared.CustomPreviousPageItem;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.paginated.CurrentPageItem;
import com.github.bakuplayz.spigotspin.menu.items.paginated.NextPageItem;
import com.github.bakuplayz.spigotspin.menu.items.paginated.PreviousPageItem;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPaginatedMenu<S extends PaginatedMenuState, SH extends PaginatedMenuStateHandler<S>, PI>
        extends com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPaginatedMenu<S, SH, PI> {

    protected final CropClick plugin;

    private final boolean showBackButton;


    public AbstractPaginatedMenu(@NotNull String title, @NotNull CropClick plugin, boolean showBackButton) {
        super(title);
        this.plugin = plugin;
        this.showBackButton = showBackButton;
    }


    public AbstractPaginatedMenu(@NotNull String title, @NotNull CropClick plugin) {
        this(title, plugin, true);
    }


    @Override
    public void setItems() {
        super.setItems();
        setItemIf(showBackButton, 46, new CustomBackItem(plugin));
        setItemIf(showBackButton, 52, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DYNAMIC;
    }


    @NotNull
    @Override
    public NextPageItem<S> createNextItem() {
        return new CustomNextPageItem<>(this);
    }


    @NotNull
    @Override
    public CurrentPageItem<S> createCurrentItem() {
        return new CustomCurrentPageItem<>(this);
    }


    @NotNull
    @Override
    public PreviousPageItem<S> createPreviousItem() {
        return new CustomPreviousPageItem<>(this);
    }

}
