package me.twoeggs.aumc.events;

import me.twoeggs.aumc.helpers.ColorHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setMessage(ColorHelper.format(e.getMessage()));
    }
}
