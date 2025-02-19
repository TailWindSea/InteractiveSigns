package me.vovari2.interactivesigns.utils;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleUtils {
    public static void spawnWaxOn(Location location){
        location.getWorld().spawnParticle(Particle.WAX_ON, location, 20, 0.3, 0.3, 0.3);
    }
    public static void spawnWaxOff(Location location){
        location.getWorld().spawnParticle(Particle.WAX_OFF, location, 20, 0.3, 0.3, 0.3);
    }
}
