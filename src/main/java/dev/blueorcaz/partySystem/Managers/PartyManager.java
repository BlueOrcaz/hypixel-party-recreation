package dev.blueorcaz.partySystem.Managers;

import dev.blueorcaz.partySystem.Model.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

import java.util.List;
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
        leader.sendMessage(String.valueOf(parties));
    }

    public void inviteParty(Player leader, Player target) { // TEMPORARY ADDTOPARTY I WILL SETUP JSONTEXT LATAER COZ I NEED TO LEARN LISTENRES LAASDGASKJDhJKASd
        // check if theyre online
        if (target == null) {
            leader.sendMessage("Player is not online!");
            return;
        }

        Party leaderParty = parties.get(leader.getUniqueId()); // get the leaders party
        if (leaderParty.isMember(target)) { // if the target is already a member
            leader.sendMessage("Player is already in party!");
            return;
        }

        leaderParty.addMember(target); // add once json works
        target.sendMessage("You have been invited");
        leader.sendMessage(leaderParty.getMembers().toString()); // returns uuids like [xxx, xxx]
    }

    public void listParty(Player player) {
        for (Party party : parties.values()) {
            if (party.getMembers().contains(player.getUniqueId())) {
                player.sendMessage(party.getMembers().toString());
                return;
            }

        }
        player.sendMessage("You are not in a party!");
    }

    public void leaveParty(Player player) {
        // check if you are in a party
        // remove from the hashmap
        // check if it updates
        // should also check if you are the party leader and then like once you are party leader, then itll disband the party ts

        // leadercheck

        for (Party party : parties.values()) {
            if (party.getMembers().contains(player.getUniqueId())) {
                // remove from parties value
                party.removeMember(player);
                player.sendMessage(party.getLeader().getUniqueId().toString());
                parties.put(party.getLeader().getUniqueId(), party); // update AHHHHHHHHHHH SHIBAJSDKLHHASKLJDHJASHJDHAJSDhj IT WSA WORKING
                player.sendMessage(party.getMembers().toString());
                return;
            }
        }
        player.sendMessage("You are not in a party!");
    }
}

