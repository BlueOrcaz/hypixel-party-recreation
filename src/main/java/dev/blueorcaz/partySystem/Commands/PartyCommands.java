package dev.blueorcaz.partySystem.Commands;

import dev.blueorcaz.partySystem.Managers.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            case "invite":
                if (args.length < 2) {
                    player.sendMessage("Usage: /party invite <player>");
                    break;
                }
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
                boolean currentlyToggled = partyManager.isPartyChatEnabled(player); // check if its currently toggled
                if (currentlyToggled) {
                    player.sendMessage("Party Chat is now disabled!");
                    partyManager.togglePartyChat(player);
                } else {
                    player.sendMessage("Party Chat is now enabled!");
                    partyManager.togglePartyChat(player);
                }
                break;
            case "warp":
                partyManager.warpParty(player);
                break;
            case "join":
                if (args.length < 2) {
                    player.sendMessage("Usage: /party join <username>");
                    break;
                }
                partyManager.joinParty(player, args[1]);
            default:
                player.sendMessage("Unknown subcommand.");
                break;
        }

        return true;
    }
}
