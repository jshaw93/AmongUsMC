package me.twoeggs.aumc.npcs;

import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;

public class DeadPlayer extends EntityZombie {
    public DeadPlayer(EntityTypes<? extends EntityZombie> type, Location loc) {
        super(type, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.setAggressive(false);
        this.setCustomNameVisible(false);
        this.setInvulnerable(true);
        this.setSilent(true);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
    }
}
