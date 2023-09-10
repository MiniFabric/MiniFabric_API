package io.github.minifabric.minifabric_api.api.dimension;

import minicraft.core.World;
import minicraft.level.Level;

/**
 * Contains information about specific dimension
 */
public class DimensionInfo {
	private final String name;
	private final int depth;
	private final int index;
	
	private WorldGenerator generator;
	private WorldSpawner spawner;
	
	public DimensionInfo(String name, int depth, int index) {
		this.name = name;
		this.depth = depth;
		this.index = index;
	}
	
	public Level getLevel() {
		return World.levels[index];
	}
	
	public int getDepth() {
		return depth;
	}
	
	public String getName() {
		return name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public WorldGenerator getGenerator() {
		return generator;
	}
	
	public void setGenerator(WorldGenerator generator) {
		this.generator = generator;
	}
	
	public WorldSpawner getSpawner() {
		return spawner;
	}
	
	public void setSpawner(WorldSpawner spawner) {
		this.spawner = spawner;
	}
	
	public void travelTo() {
		World.changeLevel(getDepth() - World.currentLevel);
	}
	
	public boolean isVanilla() {
		return depth >= -4 && depth <= 1;
	}
	
	@Override
	public String toString() {
		return "DimensionInfo{" + "name='" + name + '\'' + ", depth=" + depth + ", index=" + index + ", generator=" + generator + ", spawner=" + spawner + '}';
	}
}
