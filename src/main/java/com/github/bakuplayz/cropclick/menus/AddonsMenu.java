package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.*;
import com.github.bakuplayz.cropclick.menus.addons.*;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPlainMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Addons menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class AddonsMenu extends AbstractPlainMenu {

    private final AddonsState state;

    private final CropClick plugin;


    public AddonsMenu(@NotNull CropClick plugin) {
        super(ADDONS_TITLE.getTitle(plugin));
        this.state = new AddonsState(plugin);
        this.plugin = plugin;
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @Override
    public void setItems() {
        setItem(10, new JobsItem(), (ignored, player) -> {
            if (state.isJobsInstalled()) new JobsRebornMenu(plugin).open(player);
        });
        setItem(12, new MMOItem(), (ignored, player) -> {
            if (state.isMmoInstalled()) new McMMOMenu(plugin).open(player);
        });
        setItem(14, new GrowthItem(), (ignored, player) -> {
            if (state.isGrowthInstalled()) new OfflineGrowthMenu(plugin).open(player);
        });
        setItem(16, new ResidenceItem(), (ignored, player) -> {
            if (state.isResidenceInstalled()) new ResidenceMenu(plugin).open(player);
        });
        setItem(28, new TownyItem(), (ignored, player) -> {
            if (state.isTownyInstalled()) new TownyMenu(plugin).open(player);
        });
        setItem(30, new GuardItem(), (ignored, player) -> {
            if (state.isGuardInstalled()) new WorldGuardMenu(plugin).open(player);
        });
        setItem(32, new SkillsItem(), (ignored, player) -> {
            if (state.isAuraSkillsInstalled()) new AuraSkillsMenu(plugin).open(player);
        });
        setItem(49, new CustomBackItem(plugin));
    }


    private final class JobsItem extends ClickableItem {

        @Override
        public void create() {
            setName(ADDONS_JOBS_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.STONE_HOE);
            setMaterial(!state.isJobsEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isJobsInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
            setLore(ADDONS_JOBS_ITEM_TIPS.getAsAppendList(plugin, ADDONS_JOBS_ITEM_STATUS.get(plugin, state.isJobsEnabled())));
        }

    }

    private final class MMOItem extends ClickableItem {

        @Override
        public void create() {
            setName(ADDONS_MCMMO_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.GOLDEN_SWORD);
            setMaterial(!state.isMmoEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isMmoInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
            setLore(ADDONS_MCMMO_ITEM_TIPS.getAsAppendList(plugin, ADDONS_MCMMO_ITEM_STATUS.get(plugin, state.isMmoEnabled())));
        }

    }

    private final class GrowthItem extends ClickableItem {

        @Override
        public void create() {
            setName(ADDONS_GROWTH_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.TALL_GRASS);
            setMaterial(!state.isGrowthEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isGrowthInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
            setLore(ADDONS_GROWTH_ITEM_TIPS.getAsAppendList(plugin, ADDONS_GROWTH_ITEM_STATUS.get(plugin, state.isGrowthEnabled())));
        }

    }

    private final class ResidenceItem extends ClickableItem {

        @Override
        public void create() {
            setName(ADDONS_RESIDENCE_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.OAK_FENCE);
            setMaterial(!state.isResidenceEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isResidenceInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
            setLore(ADDONS_RESIDENCE_ITEM_TIPS.getAsAppendList(plugin, ADDONS_RESIDENCE_ITEM_STATUS.get(plugin, state.isResidenceEnabled())));
        }

    }

    private final class TownyItem extends ClickableItem {

        @Override
        public void create() {
            setName(ADDONS_TOWNY_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.OAK_FENCE_GATE);
            setMaterial(!state.isTownyEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isTownyInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
            setLore(ADDONS_TOWNY_ITEM_TIPS.getAsAppendList(plugin, ADDONS_TOWNY_ITEM_STATUS.get(plugin, state.isTownyEnabled())));
        }

    }

    private final class GuardItem extends ClickableItem {

        @Override
        public void create() {
            setName(ADDONS_GUARD_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.GRASS_BLOCK);
            setMaterial(!state.isGuardEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isGuardInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
            setLore(ADDONS_GUARD_ITEM_TIPS.getAsAppendList(plugin, ADDONS_GUARD_ITEM_STATUS.get(plugin, state.isGuardEnabled())));
        }

    }

    private final class SkillsItem extends ClickableItem {

        @Override
        public void create() {
            setName(ADDONS_SKILLS_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.DIAMOND_AXE);
            setMaterial(!state.isAuraSkillsEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isAuraSkillsInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
            setLore(ADDONS_SKILLS_ITEM_TIPS.getAsAppendList(plugin, ADDONS_SKILLS_ITEM_STATUS.get(plugin, state.isAuraSkillsEnabled())));
        }

    }

    @Getter
    private final static class AddonsState {

        /**
         * A variable checking if the {@link McMMOAddon} is on the server.
         */
        private final boolean mmoInstalled;

        /**
         * A variable checking if the {@link McMMOAddon} is enabled in {@link CropClick}.
         */
        private final boolean mmoEnabled;

        /**
         * A variable checking if the {@link JobsRebornAddon} is on the server.
         */
        private final boolean jobsInstalled;

        /**
         * A variable checking if the {@link JobsRebornAddon} is enabled in {@link CropClick}.
         */
        private final boolean jobsEnabled;

        /**
         * A variable checking if the {@link TownyAddon} is on the server.
         */
        private final boolean townyInstalled;

        /**
         * A variable checking if the {@link TownyAddon} is enabled in {@link CropClick}.
         */
        private final boolean townyEnabled;

        /**
         * A variable checking if the {@link WorldGuardAddon} is on the server.
         */
        private final boolean guardInstalled;

        /**
         * A variable checking if the {@link WorldGuardAddon} is enabled in {@link CropClick}.
         */
        private final boolean guardEnabled;

        /**
         * A variable checking if the {@link OfflineGrowthAddon} is on the server.
         */
        private final boolean growthInstalled;

        /**
         * A variable checking if the {@link OfflineGrowthAddon} is enabled in {@link CropClick}.
         */
        private final boolean growthEnabled;

        /**
         * A variable checking if the {@link ResidenceAddon} is on the server.
         */
        private final boolean residenceInstalled;

        /**
         * A variable checking if the {@link ResidenceAddon} is enabled in {@link CropClick}.
         */
        private final boolean residenceEnabled;

        /**
         * A variable checking if the {@link AuraSkillsAddon} is on the server.
         */
        private final boolean auraSkillsInstalled;

        /**
         * A variable checking if the {@link AuraSkillsAddon} is enabled in {@link CropClick}.
         */
        private final boolean auraSkillsEnabled;


        public AddonsState(@NotNull CropClick plugin) {
            AddonManager addonManager = plugin.getAddonManager();
            this.mmoInstalled = addonManager.isInstalled(McMMOAddon.NAME);
            this.mmoEnabled = addonManager.isEnabled(McMMOAddon.NAME);
            this.townyInstalled = addonManager.isInstalled(TownyAddon.NAME);
            this.townyEnabled = addonManager.isEnabled(TownyAddon.NAME);
            this.jobsInstalled = addonManager.isInstalled(JobsRebornAddon.NAME);
            this.jobsEnabled = addonManager.isEnabled(JobsRebornAddon.NAME);
            this.guardInstalled = addonManager.isInstalled(WorldGuardAddon.NAME);
            this.guardEnabled = addonManager.isEnabled(WorldGuardAddon.NAME);
            this.residenceInstalled = addonManager.isInstalled(ResidenceAddon.NAME);
            this.residenceEnabled = addonManager.isEnabled(ResidenceAddon.NAME);
            this.growthInstalled = addonManager.isInstalled(OfflineGrowthAddon.NAME);
            this.growthEnabled = addonManager.isEnabled(OfflineGrowthAddon.NAME);
            this.auraSkillsInstalled = addonManager.isInstalled(AuraSkillsAddon.NAME);
            this.auraSkillsEnabled = addonManager.isEnabled(AuraSkillsAddon.NAME);
        }

    }

}
