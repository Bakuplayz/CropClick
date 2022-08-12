package com.github.bakuplayz.cropclick.menu.menus.sounds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.settings.SoundsMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MathUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundMenu extends Menu {

    private final int MIN_DELAY = 0; // in milliseconds
    private final int MAX_DELAY = 5000; // in milliseconds

    private final int DELAY_MIN_CHANGE = 100; // in milliseconds
    private final int DELAY_MAX_CHANGE = 500; // in milliseconds

    private final int MIN_VOLUME = 0;
    private final int MAX_VOLUME = 500;

    private final int VOLUME_MIN_CHANGE = 1;
    private final int VOLUME_MAX_CHANGE = 5;

    private final int MIN_PITCH = 0;
    private final int MAX_PITCH = 1;

    private final double PITCH_MIN_CHANGE = 0.1;
    private final double PITCH_MAX_CHANGE = 0.2;

    private final Crop crop;
    private final String cropName;
    private final String soundName;
    private final SoundConfigSection soundSection;

    private int maxOrder;
    private int currentOrder;


    public SoundMenu(@NotNull CropClick plugin,
                     @NotNull Player player,
                     @NotNull Crop crop,
                     @NotNull String soundName) {
        super(plugin, player, LanguageAPI.Menu.SOUND_TITLE);
        this.soundSection = plugin.getCropsConfig().getSoundSection();
        this.cropName = crop.getName();
        this.soundName = soundName;
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        this.currentOrder = soundSection.getSoundOrder(cropName, soundName);
        this.maxOrder = soundSection.getSounds(cropName).size() - 1;

        inventory.setItem(10, getDelayRemoveItem(DELAY_MAX_CHANGE));
        inventory.setItem(11, getDelayRemoveItem(DELAY_MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayAddItem(DELAY_MIN_CHANGE));
        inventory.setItem(16, getDelayAddItem(DELAY_MAX_CHANGE));

        inventory.setItem(19, getVolumeRemoveItem(VOLUME_MAX_CHANGE));
        inventory.setItem(20, getVolumeRemoveItem(VOLUME_MIN_CHANGE));
        inventory.setItem(22, getVolumeItem());
        inventory.setItem(24, getVolumeAddItem(VOLUME_MIN_CHANGE));
        inventory.setItem(25, getVolumeAddItem(VOLUME_MAX_CHANGE));

        inventory.setItem(28, getPitchRemoveItem(PITCH_MAX_CHANGE));
        inventory.setItem(29, getPitchRemoveItem(PITCH_MIN_CHANGE));
        inventory.setItem(31, getPitchItem());
        inventory.setItem(33, getPitchAddItem(PITCH_MIN_CHANGE));
        inventory.setItem(34, getPitchAddItem(PITCH_MAX_CHANGE));

        setBackItem();

        if (currentOrder == -1) {
            return;
        }

        if (currentOrder != 0) {
            inventory.setItem(47, getDecreaseOrderItem());
        }

        if (currentOrder != maxOrder) {
            inventory.setItem(51, getIncreaseOrderItem());
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new SoundsMenu(plugin, player, crop));

        // ORDER
        if (clicked.equals(getIncreaseOrderItem())) {
            soundSection.swapSoundOrder(cropName, currentOrder, ++currentOrder);
        }

        if (clicked.equals(getDecreaseOrderItem())) {
            soundSection.swapSoundOrder(cropName, currentOrder, --currentOrder);
        }

        // DELAY
        if (clicked.equals(getDelayAddItem(DELAY_MIN_CHANGE))) {
            addSoundDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayAddItem(DELAY_MAX_CHANGE))) {
            addSoundDelay(DELAY_MAX_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(DELAY_MIN_CHANGE))) {
            removeSoundDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(DELAY_MAX_CHANGE))) {
            removeSoundDelay(DELAY_MAX_CHANGE);
        }

        // VOLUME
        if (clicked.equals(getVolumeAddItem(VOLUME_MIN_CHANGE))) {
            increaseVolume(VOLUME_MIN_CHANGE);
        }

        if (clicked.equals(getVolumeAddItem(VOLUME_MAX_CHANGE))) {
            increaseVolume(VOLUME_MAX_CHANGE);
        }

        if (clicked.equals(getVolumeRemoveItem(VOLUME_MIN_CHANGE))) {
            decreaseVolume(VOLUME_MIN_CHANGE);
        }

        if (clicked.equals(getVolumeRemoveItem(VOLUME_MAX_CHANGE))) {
            decreaseVolume(VOLUME_MAX_CHANGE);
        }

        // PITCH
        if (clicked.equals(getPitchAddItem(PITCH_MIN_CHANGE))) {
            increasePitch(PITCH_MIN_CHANGE);
        }

        if (clicked.equals(getPitchAddItem(PITCH_MAX_CHANGE))) {
            increasePitch(PITCH_MAX_CHANGE);
        }

        if (clicked.equals(getPitchRemoveItem(PITCH_MIN_CHANGE))) {
            decreasePitch(PITCH_MIN_CHANGE);
        }

        if (clicked.equals(getPitchRemoveItem(PITCH_MAX_CHANGE))) {
            decreasePitch(PITCH_MAX_CHANGE);
        }

        updateMenu();
    }


    private @NotNull ItemStack getDelayItem() {
        double delay = soundSection.getSoundDelay(
                cropName,
                soundName
        );

        return new ItemUtil(Material.WATCH)
                .setName(plugin, LanguageAPI.Menu.SOUND_DELAY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_DELAY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SOUND_DELAY_ITEM_VALUE.get(plugin, delay)
                ))
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeItem() {
        double volume = soundSection.getSoundVolume(
                cropName,
                soundName
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 4)
                .setName(plugin, LanguageAPI.Menu.SOUND_VOLUME_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_VOLUME_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SOUND_VOLUME_ITEM_VALUE.get(plugin, volume)
                ))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchItem() {
        double pitch = soundSection.getSoundPitch(
                cropName,
                soundName
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(plugin, LanguageAPI.Menu.SOUND_PITCH_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_PITCH_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SOUND_PITCH_ITEM_VALUE.get(plugin, pitch)
                ))
                .toItemStack();
    }


    private @NotNull ItemStack getIncreaseOrderItem() {
        int orderAfter = Math.min(currentOrder + 1, maxOrder);

        return new ItemUtil(Material.IRON_PLATE)
                .setName(plugin, LanguageAPI.Menu.SOUND_INCREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_INCREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDecreaseOrderItem() {
        int orderAfter = Math.max(currentOrder - 1, 0);

        return new ItemUtil(Material.GOLD_PLATE)
                .setName(plugin, LanguageAPI.Menu.SOUND_DECREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_DECREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayAddItem(int amount) {
        double beforeValue = soundSection.getSoundDelay(
                cropName,
                soundName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_DELAY);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, amount, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayRemoveItem(int amount) {
        double beforeValue = soundSection.getSoundDelay(
                cropName,
                soundName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_DELAY);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, amount, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeAddItem(int amount) {
        double beforeValue = soundSection.getSoundVolume(
                cropName,
                soundName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_VOLUME);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, amount, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeRemoveItem(int amount) {
        double beforeValue = soundSection.getSoundVolume(
                cropName,
                soundName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_VOLUME);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, amount, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchAddItem(double amount) {
        double beforeValue = soundSection.getSoundPitch(
                cropName,
                soundName
        );
        double afterValue = MathUtil.round(
                Math.min(beforeValue + amount, MAX_PITCH),
                2
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, amount, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchRemoveItem(double amount) {
        double beforeValue = soundSection.getSoundPitch(
                cropName,
                soundName
        );
        double afterValue = MathUtil.round(
                Math.max(beforeValue - amount, MIN_PITCH),
                2
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, amount, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private void addSoundDelay(int delay) {
        int oldDelay = (int) (soundSection.getSoundDelay(cropName, soundName) + delay);
        int newDelay = Math.min(oldDelay, MAX_DELAY);
        soundSection.setSoundDelay(cropName, soundName, newDelay);
    }


    private void removeSoundDelay(int delay) {
        int oldDelay = (int) (soundSection.getSoundDelay(cropName, soundName) - delay);
        int newDelay = Math.max(oldDelay, MIN_DELAY);
        soundSection.setSoundDelay(cropName, soundName, newDelay);
    }


    private void increaseVolume(int volume) {
        int oldVolume = (int) (soundSection.getSoundVolume(cropName, soundName) + volume);
        int newVolume = Math.min(oldVolume, MAX_VOLUME);
        soundSection.setSoundVolume(cropName, soundName, newVolume);
    }


    private void decreaseVolume(int volume) {
        int oldVolume = (int) (soundSection.getSoundVolume(cropName, soundName) - volume);
        int newVolume = Math.max(oldVolume, MIN_VOLUME);
        soundSection.setSoundVolume(cropName, soundName, newVolume);
    }


    private void increasePitch(double pitch) {
        double oldPitch = MathUtil.round(
                soundSection.getSoundPitch(cropName, soundName) + pitch,
                2
        );
        double newPitch = Math.min(oldPitch, MAX_PITCH);
        soundSection.setSoundPitch(cropName, soundName, newPitch);
    }


    private void decreasePitch(double pitch) {
        double oldPitch = MathUtil.round(
                soundSection.getSoundPitch(cropName, soundName) - pitch,
                2
        );
        double newPitch = Math.max(oldPitch, MIN_PITCH);
        soundSection.setSoundPitch(cropName, soundName, newPitch);
    }

}