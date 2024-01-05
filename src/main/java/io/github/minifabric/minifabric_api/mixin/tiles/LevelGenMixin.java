package io.github.minifabric.minifabric_api.mixin.tiles;

import io.github.minifabric.minifabric_api.impl.registry.OreTileRegistryImpl;
import minicraft.level.LevelGen;
import minicraft.level.tile.Tiles;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(LevelGen.class)
public abstract class LevelGenMixin {
    @Final @Shadow private static Random random;

    @Shadow private static long worldSeed;

    @Shadow
    private static short[][] createUndergroundMap(int w, int h, int depth) {
        return new short[0][];
    }

    @Shadow
    static short[][] createAndValidateMap(int w, int h, int level, long seed) {
        return new short[0][];
    }

    /***/
    @Overwrite
    private static short[][] createAndValidateUndergroundMap(int w, int h, int depth) {
        random.setSeed(worldSeed);
        do {
            short[][] result = createUndergroundMap(w, h, depth);

            int[] count = new int[256];

            for (int i = 0; i < w * h; i++) {
                count[result[0][i] & 0xff]++;
            }
            if (count[Tiles.get("rock").id & 0xff] < 100) continue;
            if (count[Tiles.get("dirt").id & 0xff] < 100) continue;
            //if (count[(TestEntrypoint.tater_ore.id & 0xff)] < 20) continue;

            if (depth < 3 && count[Tiles.get("Stairs Down").id & 0xff] < w / 32)
                continue; // Size 128 = 4 stairs min

            return result;

        } while (true);
    }


    @Inject(method = "createUndergroundMap", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/Random;nextInt(I)I", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void injectOres(int w, int h, int depth, CallbackInfoReturnable<Short[][]> cir, LevelGen mnoise1, LevelGen mnoise2, LevelGen mnoise3, LevelGen nnoise1, LevelGen nnoise2, LevelGen nnoise3, LevelGen wnoise1, LevelGen wnoise2, LevelGen wnoise3, LevelGen noise1, LevelGen noise2, short map[], short data[], int r, int i, int x, int y) {
        for (int notx = 0; notx < OreTileRegistryImpl.levels.size(); notx++) {
            for (int j = 0; j < w * h / OreTileRegistryImpl.rarities.get(notx); j++) {
                int xx = x + random.nextInt(5) - random.nextInt(5);
                int yy = y + random.nextInt(5) - random.nextInt(5);
                if (xx >= r && yy >= r && xx < w - r && yy < h - r) {
                    if (map[xx + yy * w] == Tiles.get("rock").id) {
                        if (OreTileRegistryImpl.levels.get(notx) == depth) {
                            map[xx + yy * w] = (short) ((OreTileRegistryImpl.ores.get(notx).id & 0xffff));
                        }
                    }
                }
            }
        }
    }
}
