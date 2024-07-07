package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Abstract Crops menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractCropsMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, Crop> {

    @Setter(AccessLevel.PRIVATE)
    private ItemLoreSupplier loreSupplier;


    public AbstractCropsMenu(@NotNull CropClick plugin, @NotNull ItemLoreSupplier supplier) {
        super(CROPS_TITLE.getTitle(plugin), plugin);
        setLoreSupplier(supplier);
    }


    @Override
    public List<Crop> getPaginationItems() {
        return plugin.getCropManager().getCrops();
    }


    @Override
    public BasicPaginatedStateHandler createStateHandler() {
        return new BasicPaginatedStateHandler(this);
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull Crop crop, int position) {
        return new CropItem(crop);
    }


    @AllArgsConstructor
    private class CropItem extends ClickableItem {

        private final Crop crop;


        @Override
        public void create() {
            String name = MessageUtils.beautify(crop.getName(), false);
            String status = crop.isHarvestable()
                    ? CROPS_STATUS_ENABLED.get(plugin)
                    : CROPS_STATUS_DISABLED.get(plugin);

            setMaterial(crop.getMenuType());
            setLore(loreSupplier.getLore(crop));
            setName(CROPS_ITEM_NAME.get(plugin, name, status));
            setMaterial(!crop.isHarvestable(), XMaterial.GRAY_STAINED_GLASS_PANE);
        }
    }

    @FunctionalInterface
    public interface ItemLoreSupplier {

        List<String> getLore(@NotNull Crop crop);

    }

}
