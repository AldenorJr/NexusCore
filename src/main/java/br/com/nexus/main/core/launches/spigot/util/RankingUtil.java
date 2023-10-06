package br.com.nexus.main.core.launches.spigot.util;

import br.com.nexus.main.core.launches.spigot.Spigot;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class RankingUtil {

    private final Spigot spigot;

    public RankingUtil(Spigot spigot) {
        this.spigot = spigot;
    }

    public void setRanking(Location location, ItemStack skull, ItemStack peitoral, ItemStack calca, ItemStack bota) {
        location.getChunk().load(true);
        if(location.getWorld() == null) return;
        ArmorStand hologram = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        hologram.setMetadata("nexuscore", new FixedMetadataValue(spigot, true));
        hologram.setGravity(false);
        hologram.setBoots(bota);
        hologram.setArms(true);
        hologram.setChestplate(peitoral);
        hologram.setLeggings(calca);
        hologram.setHelmet(skull);
        hologram.setCanPickupItems(false);
        hologram.setSmall(true);
        hologram.setVisible(true);
    }

}
