package com.evgenyn.encore.utilities;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Fireworks {
    public Fireworks() {
    }

    public static void createRandom(Player p) {
        Firework fw = (Firework)p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        Random r = new Random();
        Type type = Type.BALL;
        switch(r.nextInt(4) + 1) {
            case 1:
                type = Type.BALL;
                break;
            case 2:
                type = Type.BALL_LARGE;
                break;
            case 3:
                type = Type.BURST;
                break;
            case 4:
                type = Type.CREEPER;
                break;
            case 5:
                type = Type.STAR;
        }

        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.ORANGE).withFade(Color.BLACK).with(type).trail(r.nextBoolean()).build();
        fwm.addEffect(effect);
        fwm.setPower(r.nextInt(2) + 1);
        fw.setFireworkMeta(fwm);
    }
}

