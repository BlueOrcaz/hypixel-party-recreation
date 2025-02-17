package dev.blueorcaz.partySystem.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PartyCommands implements CommandExecutor {
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // give items

            ItemStack diamond = new ItemStack(Material.DIAMOND);
            diamond.setAmount(20);

            player.getInventory().addItem(diamond,diamond); // adds 40 diamonds, splits into two

            // could also do ItemStack bricks = new ItemStack(Material.BRICK, 20);
        }

        return true;
    }
}
