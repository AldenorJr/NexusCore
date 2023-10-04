package br.com.nexus.main.core.launches.spigot.command.subcommand.tags;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreateTags implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // /tags create <tag> <permission> <valor>

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cApenas jogadores podem executar esse comando");
            return true;
        }

        return false;
    }
}
