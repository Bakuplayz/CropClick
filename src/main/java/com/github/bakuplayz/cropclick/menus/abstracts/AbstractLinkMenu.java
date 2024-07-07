package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.menus.commands.PreviewContainerMenu;
import com.github.bakuplayz.cropclick.menus.commands.PreviewDispenserMenu;
import com.github.bakuplayz.cropclick.menus.links.states.LinkMenuStateBuilder;
import com.github.bakuplayz.cropclick.menus.links.states.LinkMenuStateBuilder.LinkContext;
import com.github.bakuplayz.cropclick.menus.links.states.LinkMenuStateBuilder.LinkMenuState;
import com.github.bakuplayz.cropclick.menus.links.states.LinkMenuStateBuilder.LinkMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.links.states.LinkMenuStateBuilder.LinkMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ClickableAction;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.menu.items.state.StateItem;
import com.github.bakuplayz.spigotspin.menu.items.utils.ViewState;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

public abstract class AbstractLinkMenu extends AbstractStateMenu<LinkMenuState, LinkMenuStateHandler> {

    private final CropClick plugin;

    private final Block block;

    private final Autofarm autofarm;

    private final boolean showBackButton;


    public AbstractLinkMenu(
            @NotNull CropClick plugin,
            @NotNull String title,
            @Nullable Autofarm autofarm,
            @NotNull Block block,
            boolean showBackButton
    ) {
        super(title);
        this.block = block;
        this.plugin = plugin;
        this.autofarm = autofarm;
        this.showBackButton = showBackButton;
        // TODO: Add cached ID?
    }


    protected abstract LinkContext getContext();


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @NotNull
    @Override
    public final LinkMenuStateHandler createStateHandler() {
        return LinkMenuStateBuilder.createStateHandler(this, plugin, autofarm, block, viewers, getContext());
    }


    @Override
    public void setItems() {
        boolean isUnlinked = stateHandler.getState().isUnlinked();
        boolean isUnclaimed = stateHandler.getState().isUnclaimed();

        setItemIf(isUnclaimed, 31, new ClaimItem(), getClaimAction());
        setItemIf(!isUnclaimed, 13, new ToggleItem(), (item, player) -> stateHandler.toggleAutofarm(), LinkMenuStateFlag.ENABLED_STATE);
        setItemIf(!isUnclaimed, isUnlinked ? 20 : 29, new CropItem(), getCropAction(), LinkMenuStateFlag.CROP_SELECTED_STATE);
        setItemIf(!isUnclaimed, isUnlinked ? 22 : 31, new DispenserItem(), getDispenserAction(), LinkMenuStateFlag.DISPENSER_SELECTED_STATE);
        setItemIf(!isUnclaimed, isUnlinked ? 24 : 33, new ContainerItem(), getContainerAction(), LinkMenuStateFlag.CONTAINER_SELECTED_STATE);
        setItemIf(showBackButton, 49, new CustomBackItem(plugin));
    }


    @NotNull
    private ClickableAction<ClaimItem> getClaimAction() {
        return (item, player) -> {
            stateHandler.claimAutofarm();
            forceRerender();
        };
    }


    @NotNull
    private ClickableAction<CropItem> getCropAction() {
        return (item, player) -> {
            if (item.getState().isUnlinked() && getContext() == LinkContext.CROP) {
                stateHandler.toggleCropSelect();
            }
        };
    }


    @NotNull
    private ClickableAction<DispenserItem> getDispenserAction() {
        return (item, player) -> {
            if (item.getState().isUnlinked() && getContext() == LinkContext.DISPENSER) {
                stateHandler.toggleDispenserSelect();
                return;
            }
            if (autofarm == null) return;
            new PreviewDispenserMenu(
                    plugin,
                    autofarm,
                    ((Dispenser) item.getState().getDispenserLocation().getBlock().getState()).getInventory()
            ).open(player);
        };
    }


    @NotNull
    private ClickableAction<ContainerItem> getContainerAction() {
        return (item, player) -> {
            if (item.getState().isUnlinked() && getContext() == LinkContext.CONTAINER) {
                stateHandler.toggleContainerSelect();
                return;
            }
            if (autofarm == null) return;
            new PreviewContainerMenu(
                    plugin,
                    autofarm,
                    ((Container) item.getState().getContainerLocation().getBlock().getState()).getInventory()
            ).open(player);
        };
    }


    @NotNull
    @Unmodifiable
    private List<String> getUnlinkedLore() {
        return LINK_FORMAT_STATE.getAsList(plugin, LINK_STATES_UNLINKED.get(plugin));
    }


    // TODO: Find a better way to construct this...
    @NotNull
    private List<String> getSelectedLore(@NotNull Location location) {
        List<String> selectedPart = new ArrayList<>(getBaseLore(location));
        selectedPart.add("");
        selectedPart.addAll(LINK_FORMAT_STATE.getAsList(plugin, LINK_STATES_SELECTED.get(plugin)));
        return selectedPart;
    }


    @NotNull
    private List<String> getBaseLore(@NotNull Location location) {
        return Arrays.asList(
                LINK_FORMAT_X.get(plugin, location.getBlockX()),
                LINK_FORMAT_Y.get(plugin, location.getBlockY()),
                LINK_FORMAT_Z.get(plugin, location.getBlockZ())
        );
    }


    @Override
    public boolean isFramePosition(int position) {
        boolean isLeft = position % 9 == 0;
        boolean isRight = position % 9 == 8;
        boolean isTop = (position / 9.0d) <= 1.0d;
        return isLeft || isRight || isTop;
    }


    @Override
    public Item getFrameItem(int position) {
        return new GlassItem();
    }


    private final class CropItem extends ClickableStateItem<LinkMenuState> {

        // TODO: Make this adaptive to the crop using the location.


        @Override
        public void create() {
            handleState(getState());
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            handleState(state);
        }


        private void handleState(@NotNull LinkMenuState state) {
            setMaterial(XMaterial.WHEAT);
            setLore(getLore(state));
            setName(LINK_CROP_NAME.get(plugin));
            setMaterial(state.isUnlinked(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(state.isCropSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
        }


        @NotNull
        private List<String> getLore(@NotNull LinkMenuState state) {
            Location location = state.getCropLocation();

            if (location == null) {
                return getUnlinkedLore();
            }

            return state.isCropSelected() ? getSelectedLore(location) : getLinkedLore(location);
        }


        @NotNull
        private List<String> getLinkedLore(@NotNull Location location) {
            return getBaseLore(location);
        }

    }

    private final class ContainerItem extends ClickableStateItem<LinkMenuState> {


        @Override
        public void create() {
            handleState(getState());
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            handleState(state);
        }


        private void handleState(@NotNull LinkMenuState state) {
            Location location = state.getContainerLocation();

            setLore(getLore(state));
            setName(LINK_CONTAINER_NAME.get(plugin));
            setMaterial(XMaterial.matchXMaterial(location == null ? Material.CHEST : location.getBlock().getType()));
            setMaterial(state.isUnlinked(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(state.isContainerSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
        }


        @NotNull
        private List<String> getLore(@NotNull LinkMenuState state) {
            Location location = state.getContainerLocation();

            if (location == null) {
                return getUnlinkedLore();
            }

            if (location instanceof DoublyLocation) {
                DoublyLocation doubly = LocationUtils.findDoubly(location);
                location = doubly == null ? location : doubly;
            }

            return state.isContainerSelected() ? getSelectedLore(location) : getLinkedLore(location);
        }


        @NotNull
        private List<String> getLinkedLore(@NotNull Location location) {
            return LINK_CONTAINER_TIPS.getAsAppendList(plugin, getBaseLore(location));
        }

    }

    private final class DispenserItem extends ClickableStateItem<LinkMenuState> {

        @Override
        public void create() {
            handleState(getState());
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            handleState(state);
        }


        private void handleState(@NotNull LinkMenuState state) {
            setMaterial(XMaterial.DISPENSER);
            setLore(getLore(state));
            setName(LINK_DISPENSER_NAME.get(plugin));
            setMaterial(state.isUnlinked(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(state.isDispenserSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
        }


        @NotNull
        private List<String> getLore(@NotNull LinkMenuState state) {
            Location location = state.getDispenserLocation();

            if (location == null) {
                return getUnlinkedLore();
            }

            return state.isDispenserSelected() ? getSelectedLore(location) : getLinkedLore(location);
        }


        @NotNull
        private List<String> getLinkedLore(@NotNull Location location) {
            return LINK_DISPENSER_TIPS.getAsAppendList(plugin, getBaseLore(location));
        }

    }

    private final class ClaimItem extends ClickableItem {

        @Override
        public void create() {
            setName(LINK_CLAIM_NAME.get(plugin));
            setLore(LINK_CLAIM_STATUS.getAsList(plugin));
            setMaterial(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
            setViewState(PermissionUtils.canClaimAutofarm(viewers.get(0)) ? ViewState.VISIBLE : ViewState.INVISIBLE);
        }

    }

    private final class ToggleItem extends ClickableStateItem<LinkMenuState> {

        @Override
        public void create() {
            handleState(getState());
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            handleState(state);
        }


        private void handleState(@NotNull LinkMenuState state) {
            setLore(getLore(state));
            setName(LINK_TOGGLE_NAME.get(plugin));
            setMaterial(state.isEnabled() ? getMaterial() : XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(@NotNull LinkMenuState state) {
            return LINK_TOGGLE_STATUS.getAsList(plugin, MessageUtils.getStatusMessage(plugin, state.isEnabled()));
        }


        private XMaterial getMaterial() {
            return XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE;
        }

    }

    private final class GlassItem extends StateItem<LinkMenuState> {

        @Override
        public void create() {
            setChanges(getState());
            setFlags(LinkMenuStateFlag.CLICKED_SELECTED);
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            setChanges(state);
        }


        private void setChanges(@NotNull LinkMenuState state) {
            setMaterial(XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isUnlinked(), XMaterial.YELLOW_STAINED_GLASS_PANE);
            setMaterial(state.isUnclaimed(), XMaterial.WHITE_STAINED_GLASS_PANE);
            setMaterial(state.isClickedSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
            setName(LINK_GLASS_ITEM_NAME_LINKED.get(plugin));
            setName(state.isUnlinked(), LINK_GLASS_ITEM_NAME_UNLINKED.get(plugin));
            setName(state.isUnclaimed(), LINK_GLASS_ITEM_NAME_UNCLAIMED.get(plugin));
            setName(state.isClickedSelected(), LINK_GLASS_ITEM_NAME_SELECTED.get(plugin));
        }

    }

}