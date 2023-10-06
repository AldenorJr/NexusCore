package br.com.nexus.main.core.launches.spigot.listener;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.economy.Economy;
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
    public void playerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        economy.bankDeposit(player.getName(), EconomyEnum.Money, new BigDecimal("100000000000000000000000000000"));
        economy.bankDeposit(player.getName(), EconomyEnum.Alma, new BigDecimal("500000000000000"));
        economy.bankDeposit(player.getName(), EconomyEnum.Cash, new BigDecimal("32000000000000000"));
        economy.bankDeposit(player.getName(), EconomyEnum.Coins, new BigDecimal("20000"));
        economy.bankDeposit(player.getName(), EconomyEnum.Crystal, new BigDecimal("3200000000000"));
        economy.bankDeposit(player.getName(), EconomyEnum.Magia, new BigDecimal("2000000000000"));
        economy.bankDeposit(player.getName(), EconomyEnum.Token, new BigDecimal("10000000000000"));
    }

}
