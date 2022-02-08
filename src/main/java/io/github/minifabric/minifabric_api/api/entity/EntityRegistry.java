package io.github.minifabric.minifabric_api.api.entity;

import io.github.minifabric.minifabric_api.impl.entity.HostileEntityRegistryImpl;
import io.github.minifabric.minifabric_api.impl.entity.PassiveEntityRegistryImpl;

import java.util.ArrayList;

public class EntityRegistry {
    /**
     * Registers an entity with a single spawnrate
     * @param entity The entity class
     * @param spawnrate The spawnrate (Will be applied to all times of day)
     */
    public static void registerPassiveEntity(Class entity, int spawnrate) {
        PassiveEntityRegistryImpl.entities.add(entity);
        PassiveEntityRegistryImpl.entitySpawnrates.add(spawnrate);
        PassiveEntityRegistryImpl.entitySpawnratesNighttime.add(spawnrate);
        PassiveEntityRegistryImpl.entitySpawnratesMorning.add(spawnrate);
        PassiveEntityRegistryImpl.entitySpawnratesEvening.add(spawnrate);
    }

    /**
     * Registers an entity with a daytime-nighttime spawnrate
     * @param entity The entity class
     * @param daytimeSpawnrate The daytime spawnrate (For morning/day/evening)
     * @param nighttimeSpawnRate The nighttime spawnrate
     */
    public static void registerPassiveEntity(Class entity, int daytimeSpawnrate, int nighttimeSpawnRate) {
        PassiveEntityRegistryImpl.entities.add(entity);
        PassiveEntityRegistryImpl.entitySpawnrates.add(daytimeSpawnrate);
        PassiveEntityRegistryImpl.entitySpawnratesNighttime.add(nighttimeSpawnRate);
        PassiveEntityRegistryImpl.entitySpawnratesMorning.add(daytimeSpawnrate);
        PassiveEntityRegistryImpl.entitySpawnratesEvening.add(daytimeSpawnrate);
    }

    /**
     * Registers an entity with different spawnrates for each time
     * @param entity The entity class
     * @param daytimeSpawnrate The daytime spawnrate
     * @param nighttimeSpawnRate The nighttime spawnrate
     * @param morningSpawnRate The morning spawnrate
     * @param eveningSpawnRate The evening spawnrate
     */
    public static void registerPassiveEntity(Class entity, int daytimeSpawnrate, int nighttimeSpawnRate, int morningSpawnRate, int eveningSpawnRate) {
        PassiveEntityRegistryImpl.entities.add(entity);
        PassiveEntityRegistryImpl.entitySpawnrates.add(daytimeSpawnrate);
        PassiveEntityRegistryImpl.entitySpawnratesNighttime.add(nighttimeSpawnRate);
        PassiveEntityRegistryImpl.entitySpawnratesMorning.add(morningSpawnRate);
        PassiveEntityRegistryImpl.entitySpawnratesEvening.add(eveningSpawnRate);
    }

    /**
     * Registers a hostile enemy entity
     * @param entity The entity class
     * @param spawnrate The spawnrate (Enemies only spawn at nighttime)
     * @param isDungeon Whether the entity spawns in dungeons or not
     */
    public static void registerEnemyEntity(Class entity, int spawnrate, boolean isDungeon) {
        if (isDungeon) {
            HostileEntityRegistryImpl.dungeonEntities.add(entity);
            HostileEntityRegistryImpl.dungeonSpawnrates.add(spawnrate);
        } else {
            HostileEntityRegistryImpl.entities.add(entity);
            HostileEntityRegistryImpl.entitySpawnrates.add(spawnrate);
        }
    }

    public static void registerAquaticEntity(Class entity, int spawnrate) {
        // TODO
    }

    public static ArrayList<Class> getEntityList() {
        ArrayList<Class> totalEntityList = PassiveEntityRegistryImpl.entities;
        totalEntityList.addAll(HostileEntityRegistryImpl.entities);
        totalEntityList.addAll(HostileEntityRegistryImpl.dungeonEntities);
        return totalEntityList;
    }
}
