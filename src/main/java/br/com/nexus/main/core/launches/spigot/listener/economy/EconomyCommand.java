package br.com.nexus.main.core.launches.spigot.listener.economy;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.economy.Economy;
import br.com.nexus.main.core.launches.spigot.redis.economy.EconomyRanking;
import br.com.nexus.main.core.launches.spigot.util.FormattedBigDecimal;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.math.BigDecimal;
import java.util.Arrays;

public class EconomyCommand implements Listener {

    private final Economy economy;

    public EconomyCommand(Economy economy) {
        this.economy = economy;
    }

    @EventHandler
    public void executeCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String[] args = e.getMessage().replaceAll("/", "").split(" ");
        EconomyEnum[] values = EconomyEnum.values();
        for (EconomyEnum value : values) {
            if (!value.getEconomyName().equalsIgnoreCase(args[0]) &&
                    Arrays.stream(value.getAlieases()).noneMatch(alias -> alias.equalsIgnoreCase(args[0]))) continue;
            e.setCancelled(true);
            if (args.length == 1) {
                player.sendMessage("§aVocê possui §7" +
                        FormattedBigDecimal.formatarBigInteger(economy.getBalance(player.getName(), value)) + "§a de " + value.getEconomyColor()  + value.getEmblem() + value.getEconomyName());
                return;
            }
            if(args[1].equalsIgnoreCase("ranking") || args[1].equalsIgnoreCase("top")) {
                player.openInventory(EconomyRanking.inventoryRanking.get(value));
                player.sendMessage("§aCarregando ranking...");
                return;
            }
            if (args[1].equalsIgnoreCase("pay")) {
                if(!value.isSent()) {
                    player.sendMessage("§cEssa economia não pode ser enviada de um jogador para outro.");
                    return;
                }
                if(args.length != 4) {
                    player.sendMessage("§cSiga o seguinte padrão: /"+value.getEconomyName()+" pay <nick> <quantidade>.");
                    return;
                }
                String nick = args[2];
                String quantidade = args[3];
                if(!FormattedBigDecimal.verificarSufixo(quantidade)) {
                    player.sendMessage("§cPor favor informa uma quantidade valida, você pode seguir o seguinte padrão, 10B, 10T e etc.");
                    return;
                }
                BigDecimal balance = economy.getBalance(player.getName(), value);
                BigDecimal transferir = FormattedBigDecimal.desformatarBigInteger(quantidade);
                if(balance.compareTo(transferir) <= 0) {
                    player.sendMessage("§cVocê não tem saldo suficiente.");
                    return;
                }
                if(player.getName().equalsIgnoreCase(nick)) {
                    player.sendMessage("§cVocê não pode transferir para si mesmo.");
                    return;
                }
                if(!economy.hasAccount(nick, value)) {
                    player.sendMessage("§cEsse jogador não está cadastrado.");
                    return;
                }
                economy.bankWithdrawn(player.getName(), value, transferir);
                economy.bankDeposit(nick, value, transferir);
                player.sendMessage("§aVocê transferiu §7" + FormattedBigDecimal.formatarBigInteger(transferir) + "§a para o jogador §7" + nick + "§a.");
                try {
                    Bukkit.getPlayer(nick).sendMessage("§aVocê recebeu §7" + quantidade + " §ado jogador §f" + player.getName() + "§a.");
                } catch (Exception ignored) {}
                return;
            }
            if(args[1].equalsIgnoreCase("bolsa") || args[1].equalsIgnoreCase("global")) {
                player.sendMessage(value.getEconomyColor()+"O nível atual da economia global é de §7" + FormattedBigDecimal.formatarBigInteger(EconomyRanking.economyAll.get(value)) + "§a.");
                player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
                return;
            }
            if(args.length == 2) {
                String nick = args[1];
                if(economy.hasAccount(nick, value)) {
                    String balance = FormattedBigDecimal.formatarBigInteger(economy.getBalance(nick, value));
                    player.sendMessage("§aO jogador §7" + nick + "§a está com o saldo de §7" + balance + "§a.");
                    return;
                }
            }

            sendDefaultMessage(player, value);
        }
    }

    public void sendDefaultMessage(Player player, EconomyEnum economyEnum) {
        String economyName = economyEnum.getEconomyName();
        String economyColor = economyEnum.getEconomyColor();
        player.sendMessage("");
        player.sendMessage(" "+economyColor + "✔ Lista de comandos da economa " + economyName);
        player.sendMessage("");
        player.sendMessage(economyColor+" /" + economyName + " pay <nick> <quantidade> §f§l- §7Enviar quantidade para outros jogadores.");
        player.sendMessage(economyColor+" /"+ economyName + " ranking §f§l- §7Ver ranking dos mais ricos do servidor.");
        player.sendMessage(economyColor+" /"+ economyName + " bolsa §f§l- §7Ver o atual nível da economia global.");
        player.sendMessage(economyColor+" /"+ economyName + " <nick> §f§l- §7Ver o saldo de outros jogadores.");
        player.sendMessage("");
    }

}
