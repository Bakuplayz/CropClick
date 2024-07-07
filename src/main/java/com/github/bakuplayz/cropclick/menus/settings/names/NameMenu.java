package com.github.bakuplayz.cropclick.menus.settings.names;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPlainMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Name menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class NameMenu extends AbstractPlainMenu {

    @Setter(AccessLevel.PRIVATE)
    private CropClick plugin;

    @Setter(AccessLevel.PRIVATE)
    private Crop crop;

    @Setter(AccessLevel.PRIVATE)
    private CropConfigSection cropSection;

    @Setter(AccessLevel.PRIVATE)
    private SeedConfigSection seedSection;


    public NameMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(NAME_TITLE.getTitle(plugin));
        setCropSection(plugin.getCropsConfig().getCropSection());
        setSeedSection(plugin.getCropsConfig().getSeedSection());
        setPlugin(plugin);
        setCrop(crop);
    }


    @Override
    public void setItems() {
        if (crop.hasSeed()) {
            setItem(13, new CropItem(), (item, player) -> {
                DropNameSetter setter = (text) -> cropSection.setDropName(crop.getName(), text);
                createChangeNameMenu(item, cropSection.getDropName(crop.getName()), setter).open(player);
            });
            setItem(31, new SeedItem(), (item, player) -> {
                DropNameSetter setter = (text) -> seedSection.setDropName(crop.getSeed().getName(), text);
                createChangeNameMenu(item, seedSection.getDropName(crop.getSeed().getName()), setter).open(player);
            });
        } else {
            setItem(22, new CropItem(), (item, player) -> {
                DropNameSetter setter = (text) -> cropSection.setDropName(crop.getName(), text);
                createChangeNameMenu(item, cropSection.getDropName(crop.getName()), setter).open(player);
            });
        }

        setItem(47, new ColorItem(plugin, 0));
        setItem(49, new CustomBackItem(plugin));
        setItem(51, new ColorItem(plugin, 11));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    /**
     * Creates the change name {@link AnvilGUI anvil menu}.
     *
     * @return the change name menu.
     */
    @NotNull
    private AnvilGUI.Builder createChangeNameMenu(@NotNull Item item, @NotNull String currentName, @NotNull DropNameSetter setter) {
        return new AnvilGUI.Builder()
                .itemLeft(item.asItemStack())
                .text(ChatColor.stripColor(currentName))
                .onClick((player, stateSnapshot) -> {
                    setter.setDropName(stateSnapshot.getText());
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                }).onClose((stateSnapshot) -> {
                    Player player = stateSnapshot.getPlayer();

                    if (currentName.equals(stateSnapshot.getText())) {
                        player.sendMessage(NAME_RESPONSE_UNCHANGED.get(plugin));
                        return;
                    }

                    player.sendMessage(NAME_RESPONSE_CHANGED.get(plugin, stateSnapshot.getText()));
                }).plugin(plugin);
    }


    @FunctionalInterface
    private interface DropNameSetter {

        void setDropName(@NotNull String newName);

    }


    private final class CropItem extends ClickableItem {

        @Override
        public void create() {
            String name = MessageUtils.beautify(crop.getName(), false);
            String status = crop.isHarvestable()
                    ? CROP_STATUS_ENABLED.get(plugin)
                    : CROP_STATUS_DISABLED.get(plugin);

            setMaterial(crop.getMenuType());
            setMaterial(!crop.isHarvestable(), XMaterial.RED_STAINED_GLASS_PANE);
            setName(NAME_CROP_ITEM_NAME.get(plugin, name, status));
            setLore(NAME_CROP_ITEM_TIPS.getAsAppendList(plugin,
                    NAME_CROP_ITEM_DROP_NAME.get(plugin, cropSection.getDropName(crop.getName())))
            );
        }

    }

    private final class SeedItem extends ClickableItem {

        @Override
        public void create() {
            Seed seed = crop.getSeed();
            String name = MessageUtils.beautify(seed.getName(), false);
            String status = MessageUtils.getStatusMessage(plugin, seed.isEnabled());

            setMaterial(seed.getMenuType());
            setMaterial(!seed.isEnabled(), XMaterial.RED_STAINED_GLASS_PANE);
            setName(NAME_SEED_ITEM_NAME.get(plugin, name, status));
            setLore(NAME_SEED_ITEM_TIPS.getAsAppendList(plugin,
                    NAME_SEED_ITEM_DROP_NAME.get(plugin, seedSection.getDropName(seed.getName())))
            );
        }

    }

    @AllArgsConstructor
    private static final class ColorItem extends Item {

        private final static Map<Character, String> colorCodes = getColorCodes();

        private final CropClick plugin;

        private final int startIndex;


        @Override
        public void create() {
            setMaterial(XMaterial.OAK_SIGN);
            setLore(getCodesAsLore(startIndex));
            setName(NAME_COLOR_ITEM_NAME.get(plugin));
        }


        /**
         * Gets all the {@link ChatColor#name() color codes}.
         *
         * @return color codes.
         */
        @NotNull
        private static Map<Character, String> getColorCodes() {
            return Arrays.stream(ChatColor.values())
                    .collect(Collectors.toMap(
                            ChatColor::getChar,
                            color -> MessageUtils.beautify(color.name(), true)
                    ));
        }


        /**
         * Gets color codes in lore format.
         *
         * @param startIndex the index at which to begin the looping.
         *
         * @return color codes as a lore.
         */
        private List<String> getCodesAsLore(int startIndex) {
            return colorCodes.entrySet().stream()
                    .map(colorMap -> MessageUtils.colorize(
                            NAME_COLOR_ITEM_CODE.get(
                                    plugin,
                                    colorMap.getKey(),
                                    colorMap.getKey(),
                                    colorMap.getValue())
                    ).replace("#", "&")) // Some wizardry formatting
                    .skip(startIndex)
                    .limit(11)
                    .collect(Collectors.toList());
        }

    }

}
