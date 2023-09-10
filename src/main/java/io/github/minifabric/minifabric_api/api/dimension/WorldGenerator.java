package io.github.minifabric.minifabric_api.api.dimension;

/**
 * Generate terrain in the whole world (only during new world creation)
 */
public interface WorldGenerator {
	/**
	 * World generation method. Tiles are stored in 1D arrays with "y * width + x" index format.
	 */
	void generate(DimensionInfo dimension, int width, int height, long seed, short[] tiles, short[] data);
}
