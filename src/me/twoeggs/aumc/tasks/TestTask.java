package me.twoeggs.aumc.tasks;

import me.twoeggs.aumc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class TestTask implements Listener, CommandExecutor {

    private final Main instance;

    private final int[] indexes = new int[] {10, 11, 12, 14, 15, 16, 28, 29, 30, 32, 33, 34};

    public TestTask(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("testtask")) {
            if(!(sender instanceof Player)) return true;
            Player player = (Player)sender;
            Inventory inv = inventory();
            ItemStack leaves = new ItemStack(Material.OAK_LEAVES);
            int count = 0;
            for(int index : indexes) {
                int x = new Random().nextInt(2);
                if(x == 0) {
                    inv.setItem(index, leaves);
                    count++;
                }
            }
            if(count == 0) {
                for(int index : indexes) {
                    inv.setItem(index, leaves);
                }
            }
            openGUI(player, inv);
            return true;
        }
        return false;
    }

    private void openGUI(Player player, Inventory inv) {
        player.openInventory(inv);
    }

    private ItemStack limeGlassPane() {
        return new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    }
    private ItemStack redGlassPane() {
        return new ItemStack(Material.RED_STAINED_GLASS_PANE);
    }
    private ItemStack grayGlassPane() {
        return new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    }
    private ItemStack blackGlassPane() {
        return new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    }
    private ItemStack blueGlassPane() {
        return new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
    }

    private Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE+"yeet");
        // Top bar
        for(int i = 0; i < 9; i++) {
            inv.setItem(i, blackGlassPane());
        }
        // Left bar
        for(int i = 9; i < 37; i+=9) {
            inv.setItem(i, blackGlassPane());
        }
        // Middle bars
        // Horizontal
        for(int i = 19; i < 27; i++) {
            inv.setItem(i, blackGlassPane());
        }
        // Vertical
        for(int i = 4; i < 45; i+=9) {
            inv.setItem(i, blackGlassPane());
        }
        // Right bar
        for(int i = 8; i < 37; i+=9) {
            inv.setItem(i, blackGlassPane());
        }
        //Bottom bar
        for(int i = 37; i < 45; i++) {
            inv.setItem(i, blackGlassPane());
        }
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("yeet")) return;
        if(e.getCurrentItem() == null) return;
        if(!e.getCurrentItem().isSimilar(new ItemStack(Material.OAK_LEAVES))) {
            e.setCancelled(true);
            return;
        }
        Player player = (Player)e.getWhoClicked();
        int index = e.getSlot();
        Inventory inv = e.getClickedInventory();
        if(inv == null) return;
        inv.setItem(index, null);
        player.playSound(player.getLocation(), Sound.BLOCK_CROP_BREAK, 1.0f, 1.0f);
        int count = 0;
        for(int i : indexes) {
            if(inv.getItem(i) != null) {
                count++;
            }
        }
        if(count == 0) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, player::closeInventory, 20);
        }
    }
}
