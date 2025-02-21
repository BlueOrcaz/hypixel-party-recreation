package dev.blueorcaz.partySystem.Managers;

import dev.blueorcaz.partySystem.Model.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

import java.util.HashSet;
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
        // check if they are currently in a party
        UUID leaderUUID = leader.getUniqueId();
        // search the parties thingy to check if the player is in any sort of party

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

    public void listParty(Player player) { // lists party members but uuid for now
        for (Party party : parties.values()) {
            if (party.getMembers().contains(player.getUniqueId())) {
                player.sendMessage(party.getMembers().toString());
                return;
            }

        }
        player.sendMessage("You are not in a party!");
    }

    public void promotePlayer(Player currentLeader, Player newLeader) {
        if (parties.containsKey(currentLeader.getUniqueId())) {
            // if you are the leader then u can promote
            // promotion logic here ig
            Party currentParty = parties.get(currentLeader.getUniqueId());
            // check if the newLeader is even in the party
            for (UUID member : currentParty.getMembers()) {
                // iterate through each member uuid to check if it matches the newLeader uuid
                if (member.equals(newLeader.getUniqueId())) {
                    // promotion logic
                    // make a new key with existing party members
                    currentParty.setLeader(newLeader);
                    parties.put(newLeader.getUniqueId(), currentParty);
                    parties.remove(currentLeader.getUniqueId());
                    currentLeader.sendMessage("Successfully promoted " + newLeader.getName());
                    newLeader.sendMessage("You have been promoted to party leader!");
                    return;
                }
            }

            currentLeader.sendMessage("That member is not in your party!");
        } else {
            currentLeader.sendMessage("You must be party leader to promote!");
        }
    }

    public void leaveParty(Player player) {
        // check if you are in a party
        // remove from the hashmap
        // check if it updates
        // should also check if you are the party leader and then like once you are party leader, then itll disband the party ts

        // leadercheck

        if (parties.containsKey(player.getUniqueId())) { // leadercheck
            parties.remove(player.getUniqueId());
            player.sendMessage("You have disbanded the party.");
            return;
        }

        for (Party party : parties.values()) {
            if (party.getMembers().contains(player.getUniqueId())) {

                party.removeMember(player);
                //player.sendMessage(party.getLeader().getUniqueId().toString());
                parties.put(party.getLeader().getUniqueId(), party); // update AHHHHHHHHHHH SHIBAJSDKLHHASKLJDHJASHJDHAJSDhj IT WSA WORKING
                player.sendMessage("You have left the party.");
                //player.sendMessage(party.getMembers().toString());
                if (party.getMembers().isEmpty()) {
                    parties.remove(party.getLeader().getUniqueId()); // remove
                    player.sendMessage("Party has disbanded");
                    return;
                }
                return;
            }
        }
        player.sendMessage("You are not in a party!");
    }

    public void warpParty(Player leader) {
        if (parties.containsKey(leader.getUniqueId())) {
            Location leaderLocation = leader.getLocation();
            Party leaderParty = parties.get(leader.getUniqueId());
            for (UUID memberUUID: leaderParty.getMembers()) {
                Player member = Bukkit.getPlayer(memberUUID);
                member.teleport(leaderLocation);
            }
            leader.sendMessage("Warped all members to " + leader.getName() + "'s location");
        } else {
            leader.sendMessage("You need to be a party leader to execute this command!");
        }
    }
}

