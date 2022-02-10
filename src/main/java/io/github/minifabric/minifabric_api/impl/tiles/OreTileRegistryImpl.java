package io.github.minifabric.minifabric_api.impl.tiles;

import minicraft.level.tile.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreTileRegistryImpl {
    public static ArrayList<Tile> ores = new ArrayList<>(List.of());
    public static ArrayList<Integer> rarities = new ArrayList<>(List.of());
    public static ArrayList<Integer> levels = new ArrayList<>(List.of());
    public static ArrayList<Short> ids = new ArrayList<>(List.of());

    public static short getLastUnusedID() {
        if (!(ids.size() == 0)) {
            for (int x = 0; x < ids.size(); x++) {
                if (ids.get(x) == null) {
                    return (short) (16383 - x);
                }
            }
        } else {
            return 16383;
        }
        return 16383;
    }


}
