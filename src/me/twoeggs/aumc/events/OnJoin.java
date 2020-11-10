package me.twoeggs.aumc.events;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.playerdata.PlayerData;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class OnJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        int colorCode = Integer.parseInt(Main.colors.get(0));
        Color color = Color.fromRGB(colorCode);

        // Leather helm
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta hMeta = (LeatherArmorMeta)helm.getItemMeta();
        hMeta.setColor(color);
        helm.setItemMeta(hMeta);

        // Leather chestplate
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta cMeta = (LeatherArmorMeta)chestplate.getItemMeta();
        cMeta.setColor(color);
        chestplate.setItemMeta(cMeta);

        // Leather legs
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta lMeta = (LeatherArmorMeta)legs.getItemMeta();
        lMeta.setColor(color);
        legs.setItemMeta(lMeta);

        // Leather boots
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bMeta = (LeatherArmorMeta)boots.getItemMeta();
        bMeta.setColor(color);
        boots.setItemMeta(bMeta);

        Main.playerMap.put(player, new PlayerData(color, helm, chestplate, legs, boots));

        // Set armor to above created leather objects
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);

        Main.colors.remove(0);
    }
}
