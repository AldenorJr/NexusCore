package br.com.nexus.main.core.api;

import br.com.nexus.main.core.launches.spigot.Spigot;
import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.EconomyRedis;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class EconomyAPI {

    private final EconomyRedis economyRedis;

    public EconomyAPI() {
        this.economyRedis = Spigot.getPlugin(Spigot.class).getEconomyRedis();
    }

    public void bankDeposit(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        economyRedis.bankDeposit(player, economyEnum, bigDecimal);
    }

    public void bankWithdrawn(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        economyRedis.bankWithdrawn(player, economyEnum, bigDecimal);
    }

    public void bankSetValue(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        economyRedis.bankSetValue(player, economyEnum, bigDecimal);
    }

    public BigDecimal getBalance(String player, EconomyEnum economyEnum) {
        return economyRedis.getBalance(player, economyEnum);
    }


}
