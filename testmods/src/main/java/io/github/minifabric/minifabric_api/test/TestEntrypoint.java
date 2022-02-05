package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.api.entity.EntityRegistry;
import io.github.minifabric.minifabric_api.test.TestEntity;
import net.fabricmc.api.ModInitializer;

public class TestEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        EntityRegistry.registerPassiveEntity(TestEntity.class, 48);
    }
}
