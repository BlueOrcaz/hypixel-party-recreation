package dev.blueorcaz.partySystem.Commands;

import dev.blueorcaz.partySystem.Managers.PartyManager;
import dev.blueorcaz.partySystem.Model.Party;
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
                partyManager.createParty(player);
                break;
            case "list":

                break;
            case "invite":
                player.sendMessage("Unknown subc2323ommand.");
                break;
            case "join":
                player.sendMessage("Unknown s1ubcommand.");
                break;
            case "leave":
                player.sendMessage("Unknown 232subcommand.");
                break;
            case "chat":
                player.sendMessage("Unknown subcommand.1");
                break;
            default:
                player.sendMessage("Unknown subcommand.");
                break;
        }

        return true;
    }
}
