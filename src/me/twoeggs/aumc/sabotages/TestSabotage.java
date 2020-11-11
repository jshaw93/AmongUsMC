package me.twoeggs.aumc.sabotages;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.playerdata.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TestSabotage implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("testsabotage")) {
            if (!(sender instanceof Player)) return true;
            for(Player player : Main.playerMap.keySet()) {
                PlayerData pd = Main.playerMap.get(player);
                if(!pd.isImpostor()) {
                    PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, 1285489,0, true);
                    player.addPotionEffect(pe);
                }
            }
        }
        return false;
    }
}
