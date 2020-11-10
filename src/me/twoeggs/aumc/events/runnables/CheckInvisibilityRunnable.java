package me.twoeggs.aumc.events.runnables;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CheckInvisibilityRunnable implements Runnable {
    @Override
    public void run() {
        PotionEffect pe = new PotionEffect(PotionEffectType.INVISIBILITY, 1285489,0, true);
        for(Player player : Main.playerMap.keySet()) {
            PlayerData pd = Main.playerMap.get(player);
            if(pd.isGhost()) {
                player.addPotionEffect(pe);
            }
            if(!pd.isGhost() && pd.isImpostor()) {
                if(player.hasPotionEffect(pe.getType())) {
                    continue;
                }
            }
            if(!pd.isGhost()) {
                player.removePotionEffect(pe.getType());
            }
        }
    }
}
