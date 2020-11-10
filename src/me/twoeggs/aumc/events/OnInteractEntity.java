package me.twoeggs.aumc.events;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.npcs.DeadPlayer;
import me.twoeggs.aumc.playerdata.PlayerData;
import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class OnInteractEntity implements Listener {
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        String name = e.getRightClicked().getType().name();
        if(!(e.getRightClicked() instanceof Player) && !(name.equalsIgnoreCase("zombie"))) {
            System.out.println(e.getRightClicked().getType().name());
            return;
        }
        // if right-clicked entity was a player
        if(e.getRightClicked() instanceof Player) {
            // Player doing the interaction
            Player player = e.getPlayer();
            PlayerData playerData = Main.playerMap.get(player);

            // Player being interacted with
            Player interactedPlayer = (Player) e.getRightClicked();
            PlayerData intPlayerData = Main.playerMap.get(interactedPlayer);

            // Filter use cases
            if (!playerData.isImpostor() || intPlayerData.isImpostor() || playerData.isGhost() || intPlayerData.isGhost()) {
                return;
            }
            for (DeadPlayer dp : Main.deadPlayers) {
                if (dp.getName().equalsIgnoreCase(interactedPlayer.getDisplayName())) {
                    return;
                }
            }

            // Kill player, spawn DeadPlayer entity
            intPlayerData.setGhost(true);
            interactedPlayer.getInventory().clear();

            // Spawning DeadPlayer
            Location loc = interactedPlayer.getLocation();
            DeadPlayer dp = new DeadPlayer(EntityTypes.ZOMBIE, loc);
            dp.setCustomName(new ChatComponentText("\u00a74" + interactedPlayer.getName()));
            dp.collides = false;
            WorldServer world = ((CraftWorld) interactedPlayer.getWorld()).getHandle();
            world.addEntity(dp);
            Main.deadPlayers.add(dp);
        }
        // if right-clicked entity was a zombie (DeadPlayer)
        if(name.equalsIgnoreCase("zombie")) {
            Player player = e.getPlayer();
            CraftZombie deadPlayer = (CraftZombie)e.getRightClicked();
            player.sendMessage("You clicked " + deadPlayer.getName() + "'s body.");
        }
    }
}
