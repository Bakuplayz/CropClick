package com.github.bakuplayz.cropclick.menus.settings.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder.WorldMenuState;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder.WorldMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder.WorldMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.common.ViewerMap;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;


/**
 * A class representing the World menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class WorldMenu extends AbstractStateMenu<WorldMenuState, WorldMenuStateHandler> {

    @Setter(AccessLevel.PRIVATE)
    private CropClick plugin;

    @Setter(AccessLevel.PRIVATE)
    private FarmWorld world;


    public WorldMenu(@NotNull CropClick plugin, @NotNull FarmWorld world) {
        super(WORLD_TITLE.getTitle(plugin));
        setWorld(world);
        setPlugin(plugin);
    }


    @NotNull
    @Override
    public WorldMenuStateHandler createStateHandler() {
        return WorldStateBuilder.createStateHandler(this, world);
    }


    @Override
    public void setItems() {
        setItem(20, new PlayersItem(viewers), (i, player) -> stateHandler.togglePlayersState(), WorldMenuStateFlag.PLAYERS);
        setItem(22, new WorldItem(), (i, player) -> stateHandler.toggleBanishedState(), WorldMenuStateFlag.BANISHED);
        setItem(24, new AutofarmsItem(), (i, player) -> stateHandler.toggleAutofarmsState(), WorldMenuStateFlag.AUTOFARMS);
        setItem(49, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @AllArgsConstructor
    private final class PlayersItem extends ClickableStateItem<WorldMenuState> {

        @NotNull
        private final ViewerMap viewers;


        @Override
        public void create() {
            setPlayer(viewers.get(0));
            setMaterial(XMaterial.PLAYER_HEAD);
            setLore(getLore(world.allowsPlayers()));
            setName(WORLD_PLAYERS_ITEM_NAME.get(plugin));
        }


        @Override
        public void update(@NotNull WorldMenuState state, int flag) {
            setLore(getLore(state.isPlayersAllowed()));
        }


        @NotNull
        private List<String> getLore(boolean state) {
            return WORLD_PLAYERS_ITEM_TIPS.getAsAppendList(plugin,
                    WORLD_PLAYERS_ITEM_STATUS.get(plugin, state)
            );
        }

    }

    private final class WorldItem extends ClickableStateItem<WorldMenuState> {

        @Override
        public void create() {
            String name = MessageUtils.beautify(world.getName(), true);

            setMaterial(XMaterial.GRASS_BLOCK);
            setName(WORLDS_ITEM_NAME.get(plugin, name));
            setLore(getLore(world.isBanished()));
            setMaterial(world.getName().equals("world_the_end"), XMaterial.END_STONE);
            setMaterial(world.getName().equals("world_nether"), XMaterial.NETHERRACK);
        }


        @Override
        public void update(@NotNull WorldMenuState state, int flag) {
            setLore(getLore(state.isBanished()));
        }


        @NotNull
        private List<String> getLore(boolean state) {
            return WORLD_WORLD_ITEM_TIPS.getAsAppendList(plugin,
                    WORLD_WORLD_ITEM_STATUS.get(plugin, state)
            );
        }

    }


    private final class AutofarmsItem extends ClickableStateItem<WorldMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.DISPENSER);
            setName(WORLD_AUTOFARMS_ITEM_NAME.get(plugin));
            setLore(getLore(world.allowsAutofarms()));
        }


        @Override
        public void update(@NotNull WorldMenuState state, int flag) {
            setLore(getLore(state.isAutofarmsAllowed()));
        }


        @NotNull
        private List<String> getLore(boolean state) {
            return WORLD_AUTOFARMS_ITEM_TIPS.getAsAppendList(plugin,
                    WORLD_AUTOFARMS_ITEM_STATUS.get(plugin, state)
            );
        }


    }

}
