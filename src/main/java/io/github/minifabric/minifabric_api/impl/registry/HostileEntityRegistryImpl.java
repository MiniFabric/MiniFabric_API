package io.github.minifabric.minifabric_api.impl.registry;

import minicraft.entity.Entity;
import minicraft.entity.mob.Creeper;
import minicraft.entity.mob.Knight;
import minicraft.entity.mob.Skeleton;
import minicraft.entity.mob.Slime;
import minicraft.entity.mob.Snake;
import minicraft.entity.mob.Zombie;
import org.tinylog.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class HostileEntityRegistryImpl {
    public static ArrayList<Class> entities = new ArrayList<>(Arrays.asList(Slime.class, Zombie.class, Skeleton.class, Creeper.class));
    public static ArrayList<Integer> entitySpawnrates = new ArrayList<>(Arrays.asList(40, 35, 15, 10));

    public static ArrayList<Class> dungeonEntities = new ArrayList<>(Arrays.asList(Snake.class, Knight.class));
    public static ArrayList<Integer> dungeonSpawnrates = new ArrayList<>(Arrays.asList(55, 45));

    public static int getTotalSpawnrate() {
        int total = 0;
        for (Integer entitySpawnrate : entitySpawnrates) {
            total += entitySpawnrate;
        }
        return total;
    }

    public static int getTotalDungeonSpawnrate() {
        int total = 0;
        for (Integer dungeonSpawnrate : dungeonSpawnrates) {
            total += dungeonSpawnrate;
        }
        return total;
    }

    public static Entity getEntity(int value, int lvl) {
        int storeEntitySpawnrate = 0;
        Entity entity = null;
        boolean entityNotSet = true;

        for (int x = 0; x < entitySpawnrates.size(); x++) {
            storeEntitySpawnrate += entitySpawnrates.get(x);
            if (value <= storeEntitySpawnrate && entityNotSet) {
                entityNotSet = false;
                try {
                    entity = (Entity) entities.get(x).getConstructors()[0].newInstance(lvl);
                } catch (InstantiationException |
                        IllegalAccessException |
                        InvocationTargetException e) {
                    Logger.error("Invalid entity called from HostileEntityRegistry:");
                    Logger.error(Arrays.toString(e.getStackTrace()));
                    e.printStackTrace();
                }
            }
        } return entity;
    }

    public static Entity getDungeonEntity(int value, int lvl) {
        int storeEntitySpawnrate = 0;
        Entity entity = null;
        boolean entityNotSet = true;

        for (int x = 0; x < dungeonSpawnrates.size(); x++) {
            storeEntitySpawnrate += dungeonSpawnrates.get(x);
            if (value <= storeEntitySpawnrate && entityNotSet) {
                entityNotSet = false;
                try {
                    entity = (Entity) dungeonEntities.get(x).getConstructors()[0].newInstance(lvl);
                } catch (InstantiationException |
                        IllegalAccessException |
                        InvocationTargetException e) {
                    Logger.error("Invalid dungeon entity called from HostileEntityRegistry:");
                    Logger.error(Arrays.toString(e.getStackTrace()));
                    e.printStackTrace();
                }
            }
        } return entity;
    }
}
