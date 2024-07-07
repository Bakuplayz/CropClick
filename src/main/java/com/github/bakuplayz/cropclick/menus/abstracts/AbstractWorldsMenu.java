package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.abstracts.states.WorldsStateBuilder;
import com.github.bakuplayz.cropclick.menus.abstracts.states.WorldsStateBuilder.WorldsMenuState;
import com.github.bakuplayz.cropclick.menus.abstracts.states.WorldsStateBuilder.WorldsMenuStateHandler;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.WORLDS_ITEM_NAME;
import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.WORLDS_TITLE;

public abstract class AbstractWorldsMenu extends AbstractPaginatedMenu<WorldsMenuState, WorldsMenuStateHandler, FarmWorld> {

    @Setter(AccessLevel.PRIVATE)
    private ItemLoreSupplier loreSupplier;


    protected AbstractWorldsMenu(@NotNull CropClick plugin, @NotNull ItemLoreSupplier supplier) {
        super(WORLDS_TITLE.getTitle(plugin), plugin);
        setLoreSupplier(supplier);
    }


    @Override
    public List<FarmWorld> getPaginationItems() {
        return new ArrayList<>(plugin.getWorldManager().getWorlds().values());
    }


    @Override
    public WorldsMenuStateHandler createStateHandler() {
        return WorldsStateBuilder.createStateHandler(this, plugin);
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull FarmWorld world, int position) {
        return new WorldItem(world, position);
    }


    @AllArgsConstructor
    private final class WorldItem extends ClickableStateItem<WorldsMenuState> {

        @Setter(AccessLevel.PRIVATE)
        private FarmWorld world;

        private int position;


        @Override
        public void create() {
            String name = MessageUtils.beautify(world.getName(), true);

            setFlags(Collections.singletonList(position));
            setName(WORLDS_ITEM_NAME.get(plugin, name));
            setLore(loreSupplier.getLore(world));
            setMaterial(XMaterial.GRASS_BLOCK);
            setMaterial(world.getName().equals("world_the_end"), XMaterial.END_STONE);
            setMaterial(world.getName().equals("world_nether"), XMaterial.NETHERRACK);
        }


        @Override
        public void update(@NotNull WorldsMenuState state, int flag) {
            setLore(loreSupplier.getLore(world));
        }


    }

    @FunctionalInterface
    protected interface ItemLoreSupplier {

        List<String> getLore(@NotNull FarmWorld world);

    }

}
