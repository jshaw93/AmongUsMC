package me.twoeggs.aumc.events;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.playerdata.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OnInteract implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        boolean z = false;
        Player player = e.getPlayer();
        PlayerData pd = Main.playerMap.get(player);
        Block block = e.getClickedBlock();
        try {
            if (!block.getType().equals(Material.IRON_TRAPDOOR)) {
                return;
            }
        } catch(NullPointerException err) {
            return;
        }
        Location playerLoc = player.getLocation();
        Location loc = block.getLocation();
        if(playerLoc.getY() > loc.getY()) {
            z = true;
        }
        if(z) {
            loc.add(0, -2, 0);
        } else {
            loc.add(0, 1, 0);
        }
        if(pd.isImpostor() && block.getType().equals(Material.IRON_TRAPDOOR) && !pd.isGhost()) {
            player.teleport(loc);
            PotionEffect pe = new PotionEffect(PotionEffectType.INVISIBILITY, 1285489,0, true);
            if(!player.hasPotionEffect(pe.getType())) {
                player.addPotionEffect(pe);
                player.getInventory().clear();
            } else {
                player.removePotionEffect(pe.getType());
                player.getInventory().setArmorContents(pd.getArmor());
            }
        }
    }
}
