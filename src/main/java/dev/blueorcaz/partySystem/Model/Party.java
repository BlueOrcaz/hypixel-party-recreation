package dev.blueorcaz.partySystem.Model;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class Party {
    // party should have members and leaders

    private Player leader;
    private HashSet<UUID> members;

    public Party(Player leader) {
        this.leader = leader;
        this.members = new HashSet<>();
        this.members.add(leader.getUniqueId());
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public HashSet<UUID> getMembers() {
        return members;
    }

    public void addMember(Player player) {
        members.add(player.getUniqueId());
        player.sendMessage("You joined the Party");
    }

    public void removeMember(Player player) {
        members.remove(player.getUniqueId());
        player.sendMessage("You have left the party");
    }

    public void setMembers(HashSet<UUID> members) {
        this.members = members;
    }

    public boolean isMember(Player player) {
        return members.contains(player.getUniqueId());
    }
}
