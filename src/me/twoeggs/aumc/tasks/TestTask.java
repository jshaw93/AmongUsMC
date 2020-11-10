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

public class TestTask implements Listener, CommandExecutor {

    private final Main instance;

    public TestTask(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("testtask")) {
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
        inv.setItem(0, limeGlassPane());
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("yeet")) return;
        if(e.getCurrentItem() == null) return;
        int x = 0;
    }
}
