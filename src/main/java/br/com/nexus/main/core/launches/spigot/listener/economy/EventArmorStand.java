package br.com.nexus.main.core.launches.spigot.listener.economy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class EventArmorStand implements Listener {

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void killEntity(EntityDamageEvent e) {
        if(e.getEntity().hasMetadata("nexuscore")) {
            e.setCancelled(true);
        }
    }

}
