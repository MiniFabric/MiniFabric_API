package io.github.minifabric.minifabric_api.impl.entity;

import minicraft.core.Updater;
import minicraft.entity.Entity;
import minicraft.entity.mob.Cow;
import minicraft.entity.mob.Pig;
import minicraft.entity.mob.Sheep;

import java.util.ArrayList;
import java.util.Arrays;

public class PassiveEntityRegistryImpl {
    public static ArrayList<Class> entities = new ArrayList<>(Arrays.asList(Cow.class, Pig.class, Sheep.class));
    public static ArrayList<Integer> entitySpawnrates = new ArrayList<>(Arrays.asList(33, 35, 32));
    public static ArrayList<Integer> entitySpawnratesMorning = new ArrayList<>(Arrays.asList(33, 35, 32));
    public static ArrayList<Integer> entitySpawnratesEvening = new ArrayList<>(Arrays.asList(33, 35, 32));
    public static ArrayList<Integer> entitySpawnratesNighttime = new ArrayList<>(Arrays.asList(22, 46, 32));

    public static int getTotalSpawnrate(Updater.Time time) {
        int total = 0;
        for (Integer entitySpawnrate : switch (time) {
            case Day -> entitySpawnrates;
            case Night -> entitySpawnratesNighttime;
            case Morning -> entitySpawnratesMorning;
            case Evening -> entitySpawnratesEvening;
        }) {
            total += entitySpawnrate;
        }
        return total;
    }

    public static Entity getEntity(int value, Updater.Time time) throws InstantiationException, IllegalAccessException {
        int storeEntitySpawnrate = 0;
        Entity entity = null;
        boolean entityNotSet = true;

        for (int x = 0; x < entities.size(); x++) {
            storeEntitySpawnrate += switch (time) {
                case Day -> entitySpawnrates.get(x);
                case Morning -> entitySpawnratesMorning.get(x);
                case Evening -> entitySpawnratesEvening.get(x);
                case Night -> entitySpawnratesNighttime.get(x);
            };
            if (value <= storeEntitySpawnrate && entityNotSet) {
                entityNotSet = false;
                entity = (Entity) entities.get(x).newInstance();
            }
        }
        return entity;
    }
}
