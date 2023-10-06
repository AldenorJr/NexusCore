package br.com.nexus.main.core.launches.spigot.util;

import br.com.nexus.main.core.launches.spigot.Spigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

public class HologramUtil {

    private final Spigot spigot;

    public HologramUtil(Spigot spigot) {
        this.spigot = spigot;
    }

    public void spawnHologram(Location location, String message) {
        location.getChunk().load(true);
        ArmorStand hologram = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        hologram.setMetadata("nexuscore", new FixedMetadataValue(spigot, true));
        hologram.setGravity(false);
        hologram.setCustomName(message.replaceAll("&", "§"));
        hologram.setCanPickupItems(false);
        hologram.setSmall(true);
        hologram.setCustomNameVisible(true);
        hologram.setVisible(false);
    }

    public void destroyAllEntity() {
        for(World w : Bukkit.getWorlds()) {
            for(Entity e : w.getEntities()) {
                if(e instanceof ArmorStand) {
                    if(e.hasMetadata("nexuscore")) e.remove();
                }
            }
            w.save();
        }
    }

}
