package io.github.minifabric.minifabric_api.api.entity;

import io.github.minifabric.minifabric_api.impl.entity.PassiveEntityRegistryImpl;

import java.util.ArrayList;

public class EntityRegistry {
    public static void registerPassiveEntity(Class entity, int spawnrate) {
        PassiveEntityRegistryImpl.entities.add(entity);
        PassiveEntityRegistryImpl.entitySpawnrates.add(spawnrate);
    }

    public static void registerEnemyEntity(Class entity, int lvl, int spawnrate) {
        // TODO
    }

    public static void registerAquaticEntity(Class entity, int spawnrate) {
        // TODO
    }

    public static void registerDungeonEntity(Class entity, int lvl, int spawnrate) {
        // TODO
    }

    public static ArrayList<Class> getEntityList() {
        return PassiveEntityRegistryImpl.entities;
    }
}
