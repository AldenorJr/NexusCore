package br.com.nexus.main.core.launches.spigot.listener;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.EconomyRedis;
import br.com.nexus.main.core.launches.spigot.util.FormattedBigDecimal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PlayerJoinServer implements Listener {

    private final EconomyRedis economyRedis;

    public PlayerJoinServer(EconomyRedis economyRedis) {
        this.economyRedis = economyRedis;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        economyRedis.registerPlayer(player.getName());
        String[] strings = {"", ""};
        player.sendMessage(new String[]{"", ""});
    }

    @EventHandler
    public void onBlockBreak(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        long start = System.currentTimeMillis();
        economyRedis.bankDeposit(player.getName(), EconomyEnum.Money, new BigDecimal("10000000000000000000000000"));
        long finish = System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage("§6levou "+(finish-start)+"ms.");
    }

}
