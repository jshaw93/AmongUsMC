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

import java.util.HashMap;
import java.util.Random;

public class SimonSaysTask implements CommandExecutor, Listener {
    private final Main instance;
    private int currentPlace = 0;
    private int currentClick = 0;
    private final HashMap<Object, Object> ez = new HashMap<>();
    private final HashMap<Object, Object> ex = new HashMap<>();
    private boolean running = false;

    public SimonSaysTask(Main instance) {
        this.instance = instance;
        ez.put(0, 12);
        ez.put(1, 13);
        ez.put(2, 14);
        ez.put(3, 21);
        ez.put(4, 22);
        ez.put(5, 23);
        ez.put(6, 30);
        ez.put(7, 31);
        ez.put(8, 32);

        ex.put(0, 10);
        ex.put(1, 19);
        ex.put(2, 28);
        ex.put(3, 16);
        ex.put(4, 25);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("testtask")) {
            if(!(sender instanceof Player)) return true;
            Player player = (Player)sender;
            int[] indexes = new int[5];
            for(int i = 0; i < 5; i++) {
                indexes[i] = (int)ez.get(new Random().nextInt(9));
            }
            Main.simonMap.put(player, indexes);
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
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Start");
        item.setItemMeta(meta);
        return item;
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
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE+"Simon Says");
        // Top row
        for(int i = 0; i < 9; i++) {
            inv.setItem(i, blackGlassPane());
        }

        // Leftmost columns
        for(int i = 9; i < 36; i += 9) {
            inv.setItem(i, blackGlassPane());
        }
        for(int i = 10; i < 37; i += 9) {
            inv.setItem(i, blueGlassPane());
        }
        for(int i = 11; i < 38; i += 9) {
            inv.setItem(i, blackGlassPane());
        }

        // Block
        for(int i = 12; i < 39; i += 9) {
            inv.setItem(i, grayGlassPane());
        }
        for(int i = 13; i < 40; i += 9) {
            inv.setItem(i, grayGlassPane());
        }
        for(int i = 14; i < 41; i += 9) {
            inv.setItem(i, grayGlassPane());
        }

        // Rightmost columns
        for(int i = 15; i < 42; i += 9) {
            inv.setItem(i, blackGlassPane());
        }
        for(int i = 16; i < 26; i += 9) {
            inv.setItem(i, blueGlassPane());
        }
        inv.setItem(34, redGlassPane());
        for(int i = 17; i < 44; i += 9) {
            inv.setItem(i, blackGlassPane());
        }

        // Bottom row
        for(int i = 36; i < 45; i++) {
            inv.setItem(i, blackGlassPane());
        }

        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Simon Says")) return;
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().isSimilar(redGlassPane())) {
            simonSays((Player)e.getWhoClicked(), e.getClickedInventory());
            e.setCancelled(true);
            return;
        }
        if(!e.getCurrentItem().isSimilar(grayGlassPane())) {
            e.setCancelled(true);
            return;
        }
        if(running) {
            e.setCancelled(true);
            return;
        }
        Player player = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();

        e.setCancelled(true);
        // Run code here
        int index = e.getSlot();
        if(inv != null) {
            clickSlot(inv, index);
        } else {
            return;
        }
        if(index == Main.simonMap.get(player)[currentClick]) {
            if(index == Main.simonMap.get(player)[currentPlace] && currentClick == currentPlace) {
                currentPlace++;
                currentClick = 0;
                if(currentPlace >= 5) {
                    // Task complete
                    player.sendMessage("E!");
                    inv.setItem((int)ex.get(currentPlace-1), limeGlassPane());
                    inv.setItem(34, limeGlassPane());
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, player::closeInventory, 10);
                    Main.simonMap.remove(player);
                    return;
                }
                inv.setItem((int)ex.get(currentPlace-1), limeGlassPane());
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance,
                        () -> simonSays(player, inv), 10);
            } else {
                currentClick++;
            }
        } else {
            currentClick = 0;
            currentPlace = 0;
            for(Object i : ex.values()) {
                inv.setItem((int)i, blueGlassPane());
            }
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> simonSays(player, inv), 10);
        }
    }

    private void simonSays(Player player, Inventory inv) {
        running = true;
        int time = 1;
        for(int i = 0; i < currentPlace+1; i++) {
            int z = Main.simonMap.get(player)[i];
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance,
                    () -> inv.setItem(z, blueGlassPane()), time*10);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance,
                    () -> inv.setItem(z, grayGlassPane()), (time+1)*10);
            time += 2;
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance,
                () -> running = false, (currentPlace+1)*10);
    }

    private void clickSlot(Inventory inv, int index) {
        inv.setItem(index, limeGlassPane());
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance,
                () -> inv.setItem(index, grayGlassPane()), 10);
    }
}
