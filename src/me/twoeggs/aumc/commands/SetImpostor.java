package me.twoeggs.aumc.commands;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.playerdata.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetImpostor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("setimpostor")) {
            if(!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player)sender;
            PlayerData playerData = Main.playerMap.get(player);
            playerData.setImpostor(!playerData.isImpostor());
            player.sendMessage("Impostor: " + playerData.isImpostor());
            return true;
        }
        return false;
    }
}
