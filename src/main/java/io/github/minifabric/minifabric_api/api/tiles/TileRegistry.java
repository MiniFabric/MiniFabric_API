package io.github.minifabric.minifabric_api.api.tiles;

import io.github.minifabric.minifabric_api.impl.tiles.FabricOreTileImpl;
import io.github.minifabric.minifabric_api.impl.tiles.OreTileRegistryImpl;
import io.github.minifabric.minifabric_api.mixin.tiles.TilesInvoker;
import minicraft.level.tile.Tile;

public class TileRegistry {
    public static void registerOreTile(Tile oreTile, int rarity, int level) {
        OreTileRegistryImpl.ores.add(oreTile);
        OreTileRegistryImpl.rarities.add(rarity);
        OreTileRegistryImpl.levels.add(level);
        short id = OreTileRegistryImpl.getLastUnusedID();
        OreTileRegistryImpl.ids.add(id);
        TilesInvoker.invokeAddTile(id, oreTile);
    }
}
