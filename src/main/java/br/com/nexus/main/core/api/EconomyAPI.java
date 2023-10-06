package br.com.nexus.main.core.api;

import br.com.nexus.main.core.launches.spigot.Spigot;
import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.economy.Economy;

import java.math.BigDecimal;

public class EconomyAPI {

    private final Economy economy;

    public EconomyAPI() {
        this.economy = Spigot.getPlugin(Spigot.class).getEconomyRedis();
    }

    public void bankDeposit(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        economy.bankDeposit(player, economyEnum, bigDecimal);
    }

    public void bankWithdrawn(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        economy.bankWithdrawn(player, economyEnum, bigDecimal);
    }

    public void bankSetValue(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        economy.bankSetValue(player, economyEnum, bigDecimal);
    }

    public BigDecimal getBalance(String player, EconomyEnum economyEnum) {
        return economy.getBalance(player, economyEnum);
    }


}
