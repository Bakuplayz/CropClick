package com.github.bakuplayz.cropclick.menus.settings.sounds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Sounds menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SoundsMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, String> {

    @Setter(AccessLevel.PRIVATE)
    private Crop crop;

    @Setter(AccessLevel.PRIVATE)
    private SoundConfigSection soundSection;


    public SoundsMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(SOUNDS_TITLE.getTitle(plugin), plugin);
        setSoundSection(plugin.getCropsConfig().getSoundSection());
        setCrop(crop);
    }


    @Override
    public List<String> getPaginationItems() {
        return Arrays.stream(Sound.values())
                .map(Sound::name)
                .collect(Collectors.toList());
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler() {
        return new BasicPaginatedStateHandler(this);
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull String sound, int position) {
        return new SoundItem(sound);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull String sound, int position) {
        return (item, player) -> new SoundMenu(plugin, crop, sound).open(player);
    }


    @AllArgsConstructor
    private class SoundItem extends ClickableItem {

        @NotNull
        private final String sound;


        @Override
        public void create() {
            boolean isEnabled = soundSection.isEnabled(crop.getName(), sound);
            String status = MessageUtils.getStatusMessage(plugin, isEnabled);
            String name = MessageUtils.beautify(sound, true);

            setMaterial(XMaterial.NOTE_BLOCK);
            setName(SOUNDS_ITEM_NAME.get(plugin, name, status));
            setMaterial(isEnabled, XMaterial.LIME_STAINED_GLASS_PANE);

            if (isEnabled) {
                setLore(SOUNDS_ITEM_ORDER.get(plugin, soundSection.getOrder(crop.getName(), sound)));
            }
        }

    }

}
