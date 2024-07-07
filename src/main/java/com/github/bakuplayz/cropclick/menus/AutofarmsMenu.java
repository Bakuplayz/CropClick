package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.cropclick.menus.links.DispenserLinkMenu;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Autofarms menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class AutofarmsMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, Autofarm> {


    public AutofarmsMenu(@NotNull CropClick plugin, boolean shouldShowBack) {
        super(AUTOFARMS_TITLE.getTitle(plugin), plugin, shouldShowBack);
    }


    @Override
    public List<Autofarm> getPaginationItems() {
        return plugin.getAutofarmManager().getAutofarms().stream()
                .filter(autofarm -> {
                    boolean canClaim = PermissionUtils.canClaimAutofarm(viewers.get(0));
                    boolean canUnlinkOthers = PermissionUtils.canUnlinkOthersFarm(viewers.get(0), autofarm);
                    return canUnlinkOthers || canClaim;
                })
                .collect(Collectors.toList());
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler() {
        return new BasicPaginatedStateHandler(this);
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull Autofarm autofarm, int position) {
        return new AutofarmItem(autofarm);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull Autofarm autofarm, int position) {
        return (item, player) -> new DispenserLinkMenu(
                plugin,
                autofarm,
                autofarm.getDispenserLocation().getBlock(),
                true
        ).open(player);
    }


    @AllArgsConstructor
    private final class AutofarmItem extends ClickableItem {

        private final Autofarm autofarm;


        @Override
        public void create() {
            String status = MessageUtils.getStatusMessage(plugin, autofarm.isEnabled());
            OfflinePlayer player = Bukkit.getOfflinePlayer(autofarm.getOwnerID());

            setMaterial(XMaterial.DISPENSER);
            setLore(AUTOFARMS_ITEM_OWNER.get(plugin, getName(player)));
            setName(AUTOFARMS_ITEM_NAME.get(plugin, autofarm.getShortenedID(), status));
        }


        private String getName(@NotNull OfflinePlayer player) {
            if (player.getUniqueId().equals(Autofarm.UNKNOWN_OWNER)) {
                return AUTOFARMS_ITEM_OWNER_UNCLAIMED.get(plugin);
            }

            return player.getName();
        }

    }

}
