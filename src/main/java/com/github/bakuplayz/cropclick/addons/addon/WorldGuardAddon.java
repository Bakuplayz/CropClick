package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.addons.addon.templates.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class WorldGuardAddon extends Addon {

    private final WorldGuard worldGuard = WorldGuard.getInstance();
    private final StateFlag FLAG = new StateFlag("cropclick", true);

    public WorldGuardAddon(final @NotNull AddonsConfig config) {
        super("WorldGuard", config);

        registerFlag();
    }

    public void registerFlag() {
        FlagRegistry registry = worldGuard.getFlagRegistry();
        registry.register(FLAG);
    }

    public boolean regionAllowsPlayer(final @NotNull Player player) {
        RegionContainer container = worldGuard.getPlatform().getRegionContainer();
        RegionManager manager = container.get((World) player.getWorld());
        if (manager == null) return false;

        Location location = player.getLocation();
        BlockVector3 position = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (position == null) return false;

        ApplicableRegionSet foundRegions = manager.getApplicableRegions(position);
        for (ProtectedRegion region : foundRegions) {
            if (region == null) continue;
            if (region.getFlag(FLAG) == null) return false;
            if (regionHasMember(region, player)) return true;
        }
        return false;
    }

    private boolean regionHasMember(final @NotNull ProtectedRegion region,
                                    final @NotNull Player player) {
        return region.getMembers().contains(player.getUniqueId()) ^ region.getOwners().contains(player.getUniqueId());
    }
}
