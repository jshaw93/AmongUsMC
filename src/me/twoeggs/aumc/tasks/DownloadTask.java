package me.twoeggs.aumc.tasks;

import me.twoeggs.aumc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DownloadTask implements CommandExecutor, Listener {

    private final Main instance;
    private boolean running = false;

    public DownloadTask(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("downloadtask")) {
            if(!(sender instanceof Player)) return true;
            Player player = (Player)sender;
            openGUI(player, inventory());
            return true;
        }
        return false;
    }

    private void openGUI(Player player, Inventory inv) {
        player.openInventory(inv);
    }

    private ItemStack itemStack() {
        return new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    }

    private Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 18, ChatColor.DARK_BLUE+"Download");
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemStack item2 = item.clone();
        ItemMeta meta = item2.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Start");
        item2.setItemMeta(meta);
        for(int i = 0; i < 9; i++) {
            inv.setItem(i, item);
        }
        inv.setItem(17, item2);
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Download")) return;
        if(e.getCurrentItem() == null) return;
        if(!e.getCurrentItem().hasItemMeta()) {
            e.setCancelled(true);
            return;
        }
        Player player = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        e.setCancelled(true);
        if(running) {
            return;
        }
        runDownloadRunnable(player, inv);
    }

    private void runDownloadRunnable(Player player, Inventory inv) {
        for(int i = 0; i < 9; i++) {
            int ticks = i * 20;
            final int z = i;
            if(z == 0) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                    @Override
                    public void run() {
                        inv.setItem(17, itemStack());
                    }
                }, 190);
            }
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                @Override
                public void run() {
                    inv.setItem(z, itemStack());
                }
            }, ticks);
        }
        if(!running) {
            running = true;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                @Override
                public void run() {
                    running = false;
                    player.closeInventory();
                }
            }, 210);
        }
    }
}
