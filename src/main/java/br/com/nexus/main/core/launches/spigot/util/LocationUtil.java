package br.com.nexus.main.core.launches.spigot.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static String serializeLoc(Location loc) {
        double x = Math.floor(loc.getX()) + 0.5;
        double z = Math.floor(loc.getZ()) + 0.5;
        loc.setX(x);
        loc.setZ(z);
        return loc.getX() + "#" + loc.getY() + "#" + loc.getZ() + "#" + loc.getWorld().getName() +"#"+ loc.getPitch() + "#" + loc.getYaw();
    }

    public static Location desarializeLoc(String loc) {
        String[] split = loc.split("#");
        Location loc2 = new Location(Bukkit.getWorld(split[3]), Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
        if(split.length >= 5) loc2.setPitch((float) Double.parseDouble(split[4]));
        if(split.length >= 5) loc2.setYaw((float) Double.parseDouble(split[5]));
        return loc2;
    }

}
