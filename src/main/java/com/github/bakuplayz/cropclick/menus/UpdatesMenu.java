package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.menus.states.UpdatesStateBuilder;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractSharedMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;
import static com.github.bakuplayz.cropclick.menus.states.UpdatesStateBuilder.*;

/**
 * A class representing the Updates menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class UpdatesMenu extends AbstractSharedMenu<UpdatesMenuState, UpdatesMenuStateHandler> {

    private final CropClick plugin;

    private final UpdateManager updateManager;


    public UpdatesMenu(@NotNull CropClick plugin) {
        super(UPDATES_TITLE.getTitle(plugin));
        this.updateManager = plugin.getUpdateManager();
        this.plugin = plugin;
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @NotNull
    @Override
    public UpdatesMenuStateHandler createStateHandler() {
        return UpdatesStateBuilder.createStateHandler(this, plugin);
    }


    @Override
    public void setItems() {
        setItem(20, new PlayerItem(), (item, player) -> stateHandler.togglePlayerState(), UpdatesMenuStateFlag.PLAYER);
        setItem(22, new UpdateItem(), (item, player) -> { // TODO: Fix this mess...
            if (updateManager.isUpdated()) {
                player.sendMessage(MessageUtils.colorize("&7No new updates."));
                return;
            }

            String updateURL = updateManager.getUpdateURL();
            if (updateURL.isEmpty()) return;

            String updateMessage = updateManager.getUpdateMessage();
            if (updateMessage.isEmpty()) return;

            player.sendMessage(MessageUtils.colorize(updateMessage));
            player.sendMessage(MessageUtils.colorize("&7Get the new update on Spigot!"));
            player.sendMessage(MessageUtils.colorize("&7" + updateURL));
        });
        setItem(24, new ConsoleItem(), (item, player) -> stateHandler.toggleConsoleState(), UpdatesMenuStateFlag.CONSOLE);
        setItem(49, new CustomBackItem(plugin));
    }


    private final class PlayerItem extends ClickableStateItem<UpdatesMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.ITEM_FRAME);
            setName(UPDATES_PLAYER_ITEM_NAME.get(plugin));
            setLore(getLore(updateManager.canPlayerReceiveUpdates()));
        }


        @Override
        public void update(@NotNull UpdatesMenuState state, int flag) {
            setLore(getLore(updateManager.canPlayerReceiveUpdates()));
        }


        @NotNull
        private List<String> getLore(boolean state) {
            return UPDATES_PLAYER_ITEM_TIPS.getAsAppendList(plugin,
                    UPDATES_PLAYER_ITEM_STATUS.get(plugin, MessageUtils.getStatusMessage(plugin, state))
            );
        }

    }

    private final class ConsoleItem extends ClickableStateItem<UpdatesMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.ITEM_FRAME);
            setName(UPDATES_CONSOLE_ITEM_NAME.get(plugin));
            setLore(getLore(updateManager.canConsoleReceiveUpdates()));
        }


        @Override
        public void update(@NotNull UpdatesMenuState state, int flag) {
            setLore(getLore(updateManager.canConsoleReceiveUpdates()));
        }


        @NotNull
        private List<String> getLore(boolean state) {
            return UPDATES_CONSOLE_ITEM_TIPS.getAsAppendList(plugin,
                    UPDATES_CONSOLE_ITEM_STATUS.get(plugin, MessageUtils.getStatusMessage(plugin, state))
            );
        }

    }

    private final class UpdateItem extends ClickableItem {

        @Override
        public void create() {
            setMaterial(XMaterial.ANVIL);
            setName(UPDATES_UPDATES_ITEM_NAME.get(plugin));
            setLore(UPDATES_UPDATES_ITEM_TIPS.getAsAppendList(plugin,
                    UPDATES_UPDATES_ITEM_STATE.get(plugin, updateManager.getUpdateStateMessage())
            ));
        }

    }

}
