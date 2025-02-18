package dev.blueorcaz.partySystem.Managers;

import dev.blueorcaz.partySystem.Model.Party;
import org.bukkit.entity.Player;

import java.util.HashMap;

import java.util.UUID;

public class PartyManager {
    private HashMap<UUID, Party> parties = new HashMap<>();
    private static PartyManager manager;
    public static PartyManager getManager() { // shoutout to rexe0
        if (manager == null) {
            manager = new PartyManager();
        }
        return manager;
    }

    // Create party method
    public void createParty(Player leader) {
        UUID leaderUUID = leader.getUniqueId();
        if (parties.containsKey(leaderUUID)) {
            leader.sendMessage("You already have a party!");
            return;
        }

        leader.sendMessage("Party created!");
        Party party = new Party(leader);
        parties.put(leaderUUID, party);
    }
}

