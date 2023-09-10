package io.github.minifabric.minifabric_api.impl.registry;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import minicraft.core.World;
import minicraft.level.Level;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DimensionRegistryImpl {
	private static final Map<String, DimensionInfo> DIMENSION_BY_NAME = new HashMap<>();
	private static final Map<Integer, DimensionInfo> DIMENSION_BY_DEPTH = new HashMap<>();
	private static final Map<Integer, DimensionInfo> DIMENSION_BY_INDEX = new HashMap<>();
	private static final Unsafe UNSAFE;
	private static final long MIN_LEVEL_OFFSET;
	private static int minLevel;
	private static int index;
	
	public static DimensionInfo register(String name) {
		int dimIndex = index++;
		int depth = --minLevel;
		DimensionInfo info = new DimensionInfo(name, depth, dimIndex);
		DIMENSION_BY_NAME.put(name, info);
		DIMENSION_BY_DEPTH.put(depth, info);
		DIMENSION_BY_INDEX.put(dimIndex, info);
		UNSAFE.putInt(World.class, MIN_LEVEL_OFFSET, minLevel);
		return info;
	}
	
	public static DimensionInfo getByName(String name) {
		return DIMENSION_BY_NAME.get(name);
	}
	
	public static DimensionInfo getByDepth(int depth) {
		return DIMENSION_BY_DEPTH.get(depth);
	}
	
	public static DimensionInfo getByIndex(int depth) {
		return DIMENSION_BY_INDEX.get(depth);
	}
	
	private static void register(String name, int index, int depth) {
		DimensionInfo info = new DimensionInfo(name, depth, index);
		DIMENSION_BY_NAME.put(name, info);
		DIMENSION_BY_DEPTH.put(depth, info);
		DIMENSION_BY_INDEX.put(index, info);
	}
	
	static {
		index = World.levels.length;
		minLevel = World.minLevelDepth;
		
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			UNSAFE = (Unsafe) field.get(null);
			
			field = World.class.getDeclaredField("minLevelDepth");
			MIN_LEVEL_OFFSET = UNSAFE.staticFieldOffset(field);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		for (int depth = -4; depth <= 1; depth++) {
			register(Level.getLevelName(depth), World.lvlIdx(depth), depth);
		}
	}
}
