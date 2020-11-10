package me.twoeggs.aumc.commands;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.npcs.DeadPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class removedp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(s.equalsIgnoreCase("removedp")) {
            for(DeadPlayer dp : Main.deadPlayers) {
                dp.dead = true;
            }
            Main.deadPlayers.clear();
            return true;
        }
        return false;
    }
}
