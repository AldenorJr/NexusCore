package br.com.nexus.main.core.launches.spigot.listener.economy;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.redis.economy.EconomyRanking;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class RankingEvent implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        ItemMeta itemMeta = currentItem.getItemMeta();
        if(currentItem.getType() == Material.AIR) return;
        if(!e.getClickedInventory().getName().contains("Ranking")) return;
        if(itemMeta.getDisplayName().contains("#")) {
            e.setCancelled(true);
            return;
        }
        HashMap<EconomyEnum, Inventory> inventoryRanking = EconomyRanking.inventoryRanking;
        for (EconomyEnum value : inventoryRanking.keySet()) {
            Inventory inventory = inventoryRanking.get(value);
            if(inventory.getName().equalsIgnoreCase(e.getClickedInventory().getName())) {
                if(itemMeta.getDisplayName().contains("#")) break;
                if(itemMeta.getDisplayName().contains(value.getEconomyName())) {
                    e.setCancelled(true);
                    return;
                }
                for(EconomyEnum clicked : inventoryRanking.keySet()) {
                    if(itemMeta.getDisplayName().contains(clicked.getEconomyName())) {
                        Player player = (Player) e.getWhoClicked();
                        player.openInventory(EconomyRanking.inventoryRanking.get(clicked));
                        player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
                    }
                }
                e.setCancelled(true);
            }
        }
    }

}
