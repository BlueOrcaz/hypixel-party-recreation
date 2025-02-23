package dev.blueorcaz.partySystem.Managers;

import dev.blueorcaz.partySystem.Model.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class PartyManager {
    private HashMap<String, Party> parties = new HashMap<>();
    private static PartyManager manager;
    public static PartyManager getManager() { // shoutout to rexe0
        if (manager == null) {
            manager = new PartyManager();
        }
        return manager;
    }

    public void joinParty(Player player, String key) { // used for json messages
        Party party = parties.get(key);
        party.addMember(player);
    }


    public void inviteParty(Player leader, Player target) {// inviteparty
        // if the leader is not in some sort of party create that first
        if (!parties.containsKey(leader.getName())) {
            Party party = new Party(leader);
            parties.put(leader.getName(), party);
            TextComponent acceptMessage = new TextComponent("You have been invited to " + leader.getName() + "'s party! Click here to join!");
            acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party join " + leader.getName()));
            target.spigot().sendMessage(acceptMessage);
            return;
        }


        if (target == null) {
            leader.sendMessage("Player is not online!");
            return;
        }

        Party leaderParty = parties.get(leader.getName()); // get the leaders party
        if (leaderParty.isMember(target)) { // if the target is already a member
            leader.sendMessage("Player is already in party!");
            return;
        }

        TextComponent acceptMessage = new TextComponent("You have been invited to " + leader.getName() + "'s party! Click here to join!");
        acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party join " + leader.getName()));
        target.spigot().sendMessage(acceptMessage);
        //leader.sendMessage(leaderParty.getMembers().toString()); // returns uuids like [xxx, xxx]
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
        if (parties.containsKey(currentLeader.getName())) {
            // if you are the leader then u can promote
            // promotion logic here ig
            Party currentParty = parties.get(currentLeader.getName());
            // check if the newLeader is even in the party
            for (UUID member : currentParty.getMembers()) {
                // iterate through each member uuid to check if it matches the newLeader uuid
                if (member.equals(newLeader.getUniqueId())) {
                    // promotion logic
                    // make a new key with existing party members
                    currentParty.setLeader(newLeader);
                    parties.put(newLeader.getName(), currentParty);
                    parties.remove(currentLeader.getName());
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

        if (parties.containsKey(player.getName())) { // leadercheck
            parties.remove(player.getName());
            player.sendMessage("You have disbanded the party.");
            return;
        }

        for (Party party : parties.values()) {
            if (party.getMembers().contains(player.getUniqueId())) {
                party.removeMember(player);
                //player.sendMessage(party.getLeader().getUniqueId().toString());
                parties.put(party.getLeader().getName(), party); // update AHHHHHHHHHHH SHIBAJSDKLHHASKLJDHJASHJDHAJSDhj IT WSA WORKING
                player.sendMessage("You have left the party.");
                //player.sendMessage(party.getMembers().toString());
                if (party.getMembers().isEmpty()) {
                    parties.remove(party.getLeader().getName()); // remove
                    player.sendMessage("Party has disbanded");
                    return;
                }
                return;
            }
        }
        player.sendMessage("You are not in a party!");
    }

    public void warpParty(Player leader) {
        if (parties.containsKey(leader.getName())) {
            Location leaderLocation = leader.getLocation();
            Party leaderParty = parties.get(leader.getName());
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

