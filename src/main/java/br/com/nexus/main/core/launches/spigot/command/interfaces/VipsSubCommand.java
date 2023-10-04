package br.com.nexus.main.core.launches.spigot.command.interfaces;

import org.bukkit.entity.Player;

public interface VipsSubCommand {

    boolean subCommand(String[] args);
    void run(String[] args, Player player);

}
