package me.twoeggs.aumc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class invisible implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("invisible")) {
            if(!(sender instanceof Player)) {
                return true;
            }
            PotionEffect pe = new PotionEffect(PotionEffectType.INVISIBILITY, 1285489,0, true);
            Player player = (Player)sender;
            if(!player.hasPotionEffect(pe.getType())) {
                player.addPotionEffect(pe);
            } else {
                player.removePotionEffect(pe.getType());
            }
        }
        return false;
    }
}
