package br.com.nexus.main.core.launches.spigot.redis.economy;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.util.FormattedBigDecimal;
import br.com.nexus.main.core.launches.spigot.util.ItemBuilder;
import br.com.nexus.main.core.launches.spigot.util.SkullAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EconomyRanking {

    public static HashMap<EconomyEnum, Inventory> inventoryRanking = new HashMap<>();
    public static HashMap<EconomyEnum, BigDecimal> economyAll = new HashMap<>();
    public static HashMap<EconomyEnum, HashMap<String, BigDecimal>> playersRanking = new HashMap<>();
    private final Economy economy;

    public EconomyRanking(Economy economy) {
        this.economy = economy;
    }

    public void LoadRanking() {
        for (EconomyEnum economyEnum : EconomyEnum.values()) {
            Map<String, BigDecimal> rankingEconomy = economy.getRankingEconomy(economyEnum);
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "§8Ranking " + economyEnum.getEconomyName());
            int slot = 10;
            int position = 1;
            if (rankingEconomy.isEmpty()) {
                inventory.setItem(22, new ItemBuilder(Material.BARRIER).setName("§cNão há jogadores no ranking.").toItemStack());
                inventoryRanking.put(economyEnum, inventory);
                playersRanking.put(economyEnum, new HashMap<>(rankingEconomy));
                continue;
            } else {
                for (Map.Entry<String, BigDecimal> entry : rankingEconomy.entrySet()) {
                    if (slot == 17) slot = 19;
                    if (slot == 26) slot = 28;
                    if (slot == 35) break;
                    String name = entry.getKey();
                    BigDecimal balance = entry.getValue();
                    inventory.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).
                            setSkullOwner(name).
                            setName(economyEnum.getEconomyColor() + name + " §7#" + position).
                            setLore("§7Saldo: §f" + FormattedBigDecimal.formatarBigInteger(balance)).
                            toItemStack());
                    slot++;
                    position++;
                }
            }
            slot = (6 * 9) - 8;
            for (EconomyEnum value : EconomyEnum.values()) {
                if (value == economyEnum) {
                    ItemStack green = new ItemBuilder(SkullAPI.getSkull("http://textures.minecraft.net/texture/ffec3d25ae0d147c342c45370e0e43300a4e48a5b43f9bb858babff756144dac"))
                            .setName(value.getEconomyColor() + value.getEconomyName())
                            .toItemStack();
                    inventory.setItem(slot, green);
                } else {
                    ItemStack cinza = new ItemBuilder(SkullAPI.getSkull("http://textures.minecraft.net/texture/b2554dda80ea64b18bc375b81ce1ed1907fc81aea6b1cf3c4f7ad3144389f64c"))
                            .setName(value.getEconomyColor() + value.getEconomyName())
                            .toItemStack();
                    inventory.setItem(slot, cinza);
                }
                slot++;
            }

            inventoryRanking.put(economyEnum, inventory);
            playersRanking.put(economyEnum, new HashMap<>(rankingEconomy));
        }
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aTodos os rankings foram recarregados.");
    }

}
