package br.com.nexus.main.core.launches.spigot.listener.economy;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.EconomyRedis;
import br.com.nexus.main.core.launches.spigot.util.FormattedBigDecimal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class EconomyCommand implements Listener {

    private final EconomyRedis economyRedis;

    public EconomyCommand(EconomyRedis economyRedis) {
        this.economyRedis = economyRedis;
    }

    @EventHandler
    public void executeCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String[] args = e.getMessage().replaceAll("/", "").split(" ");
        EconomyEnum[] values = EconomyEnum.values();
        for (EconomyEnum value : values) {
            if (!value.getEconomyName().equalsIgnoreCase(args[0]) &&
                    Arrays.stream(value.getAlieases()).noneMatch(alias -> alias.equalsIgnoreCase(args[0]))) continue;
            if (args.length == 1) {
                player.sendMessage("§aVocê possui " + FormattedBigDecimal.formatarBigInteger(economyRedis.getBalance(player.getName(), value)) + " de " + value.getEconomyColor()  + value.getEmblem() + value.getEconomyName());
                e.setCancelled(true);
                return;
            }
            if (args[1].equalsIgnoreCase("pay")) {
                if(!value.isSent()) {
                    player.sendMessage("§cEssa economia não pode ser enviada de um jogador para outro.");
                    return;
                }
                if(args.length != 3) {
                    player.sendMessage("§cSiga o seguinte padrão: /"+value.getEconomyName()+" pay <nick> <quantidade>.");
                    return;
                }
                String nick = args[1];
                String quantidade = args[2];
                player.sendMessage("§a" + nick + " " + quantidade);
            }
            sendDefaultMessage(player, value);
            e.setCancelled(true);
        }
    }

    public void sendDefaultMessage(Player player, EconomyEnum economyEnum) {
        String economyName = economyEnum.getEconomyName();
        String economyColor = economyEnum.getEconomyColor();
        player.sendMessage(" "+economyColor + "✔ Lista de comandos da economa " + economyName);
        player.sendMessage("");
        player.sendMessage(economyColor+" /" + economyName + " pay <nick> <quantidade> §f§l- §7Enviar quantidade para outros jogadores.");
        player.sendMessage(economyColor+" /"+ economyName + " ranking §f§l- §7Ver ranking dos mais ricos do servidor.");
    }

}
