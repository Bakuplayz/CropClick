package com.github.bakuplayz.cropclick.addons.addon;


import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ResidenceAddon extends Addon {

    private Flags flag;

    public ResidenceAddon(final @NotNull AddonsConfig config) {
        super("Residence", config);

        registerFlag();
    }

    private void registerFlag() {
        FlagPermissions.addFlag("cropclick");
        this.flag = Flags.getFlag("cropclick");
    }

    public boolean regionOrPlayerHasFlag(final @NotNull Player player) {
        ResidenceManager manager = Residence.getInstance().getResidenceManager();
        if (manager == null) return false;

        ClaimedResidence claimed = manager.getByLoc(player.getLocation());
        if (claimed == null) return false;

        ResidencePermissions permissions = claimed.getPermissions();
        if (permissions == null) return false;
        if (permissions.getFlags() == null) return false;
        if (permissions.getFlags().size() == 0) return false;

        boolean playerHasFlag = permissions.playerHas(player, flag, true); //most likely not required
        boolean residenceHasFlag = permissions.has(flag, true);

        return residenceHasFlag || playerHasFlag;
    }

}
