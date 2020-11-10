package me.twoeggs.aumc;

import me.twoeggs.aumc.commands.SetImpostor;
import me.twoeggs.aumc.commands.deadplayer;
import me.twoeggs.aumc.commands.invisible;
import me.twoeggs.aumc.commands.removedp;
import me.twoeggs.aumc.events.*;
import me.twoeggs.aumc.events.runnables.CheckInvisibilityRunnable;
import me.twoeggs.aumc.npcs.DeadPlayer;
import me.twoeggs.aumc.playerdata.PlayerData;
import me.twoeggs.aumc.tasks.TestTask;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {
    // Stores playerdata.
    public static HashMap<Player, PlayerData> playerMap = new HashMap<>();
    // Stores list of dead player entities to be added to on death and to be cleared on report/game end.
    public static ArrayList<DeadPlayer> deadPlayers = new ArrayList<>();
    // List of potential colors
    public static ArrayList<String> colors = new ArrayList<>();

    // Simon says task data
    public static HashMap<Player, int[]> simonMap = new HashMap<>();

    @Override
    public void onEnable() {
        addColors();
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new CheckInvisibilityRunnable(), 0, 10);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new OnChat(), this);
        pm.registerEvents(new OnJoin(), this);
        pm.registerEvents(new OnLeave(), this);
        pm.registerEvents(new OnUnequip(), this);
        pm.registerEvents(new OnInteract(), this);
        pm.registerEvents(new OnInteractEntity(), this);
        this.getCommand("invisible").setExecutor(new invisible());
        this.getCommand("deadplayer").setExecutor(new deadplayer());
        this.getCommand("removedp").setExecutor(new removedp());
        this.getCommand("setimpostor").setExecutor(new SetImpostor());

        this.getCommand("testtask").setExecutor(new TestTask(this));
        pm.registerEvents(new TestTask(this), this);
    }

    @Override
    public void onDisable() {
        for(DeadPlayer dp : deadPlayers) {
            dp.dead = true;
        }
        deadPlayers.clear();
    }

    private void addColors() {
        colors.add("15790320");
        colors.add("15435844");
        colors.add("6719955");
        colors.add("14602026");
        colors.add("4312372");
        colors.add("14188952");
        colors.add("4408131");
        colors.add("2651799");
        colors.add("8073150");
        colors.add("2437522");
        colors.add("5320730");
        colors.add("3887386");
        colors.add("11743532");
        colors.add("1973019");
    }

    // For reversing items in ItemStack[], used for re-equipping armor
    public static ItemStack[] reverse(ItemStack[] a, int n) {
        ItemStack[] b = new ItemStack[n];
        int j = n;
        for(int i = 0; i < n; i++) {
            b[j-1] = a[i];
            j -= 1;
        }
        return b;
    }
}
