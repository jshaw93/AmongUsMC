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

public class LightsTask implements Listener, CommandExecutor {

    private final Main instance;

    public LightsTask(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("lighttask")) {
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
    private ItemStack redGlassPane() {
        return new ItemStack(Material.RED_STAINED_GLASS_PANE);
    }

    private Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 18, ChatColor.DARK_BLUE+"Lights");
        for(int i = 2; i < 7; i++) {
            inv.setItem(i, redGlassPane());
        }
        inv.setItem(17, redGlassPane());
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Lights")) return;
        if(e.getCurrentItem() == null) return;
        Player player = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();

        e.setCancelled(true);

        // Run code here
        if(e.getCurrentItem().isSimilar(redGlassPane())) {
            int slot = e.getSlot();
            inv.setItem(slot, itemStack());
        }else if(e.getCurrentItem().isSimilar(itemStack())) {
            int slot = e.getSlot();
            inv.setItem(slot, redGlassPane());
        }

        runTask(player, inv);
    }

    private void runTask(Player player, Inventory inv) {
        int x = 0;
        for(ItemStack itemStack : inv.getContents()) {
            if(itemStack == null) continue;
            if(itemStack.isSimilar(itemStack())) {
                x += 1;
            }
        }
        if(x >= 5) {
            inv.setItem(17, itemStack());
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, player::closeInventory, 40);
        }
    }
}
