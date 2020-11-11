package me.twoeggs.aumc.tasks;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.playerdata.PlayerData;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class LightsTask implements Listener, CommandExecutor {

    private final Main instance;

    public LightsTask(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("lightstask")) {
            if(!(sender instanceof Player)) return true;
            Player player = (Player)sender;
            if(Main.lightsInv == null) {
                Main.lightsInv = inventory();
            }
            openGUI(player, Main.lightsInv);
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
        int count = 0;
        for(int i = 2; i < 7; i++) {
            int z = new Random().nextInt(2);
            if(z == 0) {
                inv.setItem(i, redGlassPane());
                count++;
            } else {
                inv.setItem(i, itemStack());
            }
        }
        if(count == 0) {
            for(int i = 2; i < 7; i++) {
                inv.setItem(i, redGlassPane());
            }
        }
        inv.setItem(17, redGlassPane());
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Lights")) return;
        if(e.getCurrentItem() == null) return;
        Player player = (Player)e.getWhoClicked();
        Inventory inv = Main.lightsInv;
        if(inv == null) return;

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
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> {
                for(Player p : Main.playerMap.keySet()) {
                    Inventory inventory = p.getOpenInventory().getTopInventory();
                    PlayerData pd = Main.playerMap.get(p);
                    if(inv == inventory) {
                        p.closeInventory();
                    }
                    PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, 60,0, true);
                    p.removePotionEffect(PotionEffectType.BLINDNESS);
                    if(pd.isImpostor()) {
                        p.addPotionEffect(pe);
                    }
                }
            }, 40);
            Main.lightsInv = null;
        }
    }
}
