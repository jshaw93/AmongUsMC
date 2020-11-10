package me.twoeggs.aumc.playerdata;

import me.twoeggs.aumc.Main;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

public class PlayerData {
    private final Color color;
    private boolean ghost = false;
    private boolean impostor = false;
    private boolean priority = false;
    private ItemStack[] armor;

    public PlayerData(Color c, ItemStack helm, ItemStack cp, ItemStack legs, ItemStack boots) {
        this.color = c;
        this.armor = new ItemStack[] {helm, cp, legs, boots};
        this.armor = Main.reverse(this.armor, 4);
    }

    public Color getColor() {
        return color;
    }

    public boolean isGhost() {
        return ghost;
    }

    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }

    public boolean isImpostor() {
        return impostor;
    }

    public void setImpostor(boolean impostor) {
        this.impostor = impostor;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public ItemStack[] getArmor() {
        return armor;
    }
}
