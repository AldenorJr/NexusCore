package br.com.nexus.main.core.launches.spigot.listener;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.math.BigDecimal;

public class PlayerJoinServer implements Listener {

    private final Economy economy;

    public PlayerJoinServer(Economy economy) {
        this.economy = economy;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        economy.registerPlayer(player.getName());
        String[] strings = {"", ""};
        player.sendMessage(new String[]{"", ""});
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("NexusCore"), () -> {
            for(int x = 0; x <= 10000; x++) {
                for(EconomyEnum economyEnum : EconomyEnum.values()) {
                    economy.bankDeposit(player.getName(), economyEnum, new BigDecimal("100000000000000000000000000000000000000"));
                }
            }
        });
        Bukkit.getConsoleSender().sendMessage("§aDepositado 1000000000 em cada conta. 100x");
    }

}
