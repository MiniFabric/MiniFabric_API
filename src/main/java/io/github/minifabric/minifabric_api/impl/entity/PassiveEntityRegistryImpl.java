package io.github.minifabric.minifabric_api.impl.entity;

import minicraft.entity.Entity;
import minicraft.entity.mob.Cow;
import minicraft.entity.mob.Pig;
import minicraft.entity.mob.Sheep;

import java.util.ArrayList;
import java.util.Arrays;

public class PassiveEntityRegistryImpl {
    public static ArrayList<Class> entities = new ArrayList<>(Arrays.asList(Cow.class, Pig.class, Sheep.class));
    public static ArrayList<Integer> entitySpawnrates = new ArrayList<>(Arrays.asList(33, 35, 32));

    public static int getTotalSpawnrate() {
        int total = 0;
        for (Integer entitySpawnrate : entitySpawnrates) {
            total += entitySpawnrate;
        }
        return total;
    }

    public static Entity getEntity(int value, int lvl) throws InstantiationException, IllegalAccessException {
        int storeEntitySpawnrate = 0;
        Entity entity = null;
        boolean entityNotSet = true;

        for (int x = 0; x < entitySpawnrates.size(); x++) {
            storeEntitySpawnrate += entitySpawnrates.get(x);
            if (value <= storeEntitySpawnrate && entityNotSet) {
                entityNotSet = false;
                entity = (Entity) entities.get(x).newInstance();
            }
        }
        return entity;
    }
}
