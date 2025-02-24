package dev.blueorcaz.partySystem.Listeners;

import dev.blueorcaz.partySystem.Managers.PartyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final PartyManager partyManager = PartyManager.getManager();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

    }
}
