package me.twoeggs.aumc.commands;

import me.twoeggs.aumc.Main;
import me.twoeggs.aumc.helpers.ColorHelper;
import me.twoeggs.aumc.npcs.DeadPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R1.ChatBaseComponent;
import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;

public class deadplayer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("deadplayer")) {
            if(!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player)sender;
            Location loc = player.getLocation();
            DeadPlayer dp = new DeadPlayer(EntityTypes.ZOMBIE, loc);
            dp.setCustomName(new ChatComponentText(player.getName()));
            dp.collides = false;
            WorldServer world = ((CraftWorld)player.getWorld()).getHandle();
            world.addEntity(dp);
            Main.deadPlayers.add(dp);
            return true;
        }
        return false;
    }
}
