package io.github.minifabric.minifabric_api.api.registry;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import io.github.minifabric.minifabric_api.impl.registry.DimensionRegistryImpl;

public class DimensionRegistry {
	public static DimensionInfo register(String name) {
		return DimensionRegistryImpl.register(name);
	}
	
	public static DimensionInfo getByName(String name) {
		return DimensionRegistryImpl.getByName(name);
	}
}
