package me.twoeggs.aumc.events;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PlayerData playerData = Main.playerMap.get(player);
        Main.playerMap.remove(player);
        String colorCode = Integer.toString(playerData.getColor().asRGB());
        Main.colors.add(colorCode);
        player.getInventory().clear();
    }
}
