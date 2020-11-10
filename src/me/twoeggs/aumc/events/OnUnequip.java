package me.twoeggs.aumc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class OnUnequip implements Listener {
    @EventHandler
    public void onUnequip(InventoryClickEvent e) {
        if(e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            e.setCancelled(true);
        }
    }
}
