package dev.blueorcaz.partySystem.Commands;

import dev.blueorcaz.partySystem.Managers.PartyManager;
import dev.blueorcaz.partySystem.Model.Party;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PartyCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PartyManager partyManager = PartyManager.getManager();
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /party <subcommand>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                partyManager.createParty(player); // temporary create thing
                break;
            case "invite":
                if (args.length < 2) {
                    player.sendMessage("Usage: /party invite <player>");
                    break;
                }
                // invite logic
                Player target = Bukkit.getPlayerExact(args[1]);
                partyManager.inviteParty(player, target);
                break;
            case "promote":
                if (args.length < 2) {
                    player.sendMessage("Usage: /party promote <player>");
                    break;
                }
                Player newLeader = Bukkit.getPlayerExact(args[1]);
                partyManager.promotePlayer(player, newLeader);
            case "list":
                partyManager.listParty(player);
                break;
            case "leave":
                partyManager.leaveParty(player);
                break;
            case "chat":
                player.sendMessage("Unknown subcommand.1");
                break;
            case "warp":
                partyManager.warpParty(player);
                break;
            default:
                player.sendMessage("Unknown subcommand.");
                break;
        }


        return true;
    }
}
