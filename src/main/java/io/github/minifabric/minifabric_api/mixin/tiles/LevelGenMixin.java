package io.github.minifabric.minifabric_api.mixin.tiles;

import io.github.minifabric.minifabric_api.impl.tiles.OreTileRegistryImpl;
import io.github.minifabric.minifabric_api.test.TestEntrypoint;
import minicraft.core.Game;
import minicraft.level.LevelGen;
import minicraft.level.tile.Tiles;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    static short[][] createAndValidateMap(int w, int h, int level) {
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
    private static void injectOres(int w, int h, int depth, CallbackInfoReturnable<short[][]> cir, LevelGen mnoise1, LevelGen mnoise2, LevelGen mnoise3, LevelGen nnoise1, LevelGen nnoise2, LevelGen nnoise3, LevelGen wnoise1, LevelGen wnoise2, LevelGen wnoise3, LevelGen noise1, LevelGen noise2, short map[], short data[], int r, int i, int x, int y) {
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
    /***/
    @Overwrite
    public static void main(String[] args) {
        worldSeed = 0x100;

        // Fixes to get this method to work

        // AirWizard needs this in constructor
        Game.gameDir = "";

        Tiles.initTileList();
        // End of fixes

        int idx = -1;

        int[] maplvls = new int[args.length];
        boolean valid = true;
        if (maplvls.length > 0) {
            for (int i = 0; i < args.length; i++) {
                try {
                    int lvlnum = Integer.parseInt(args[i]);
                    maplvls[i] = lvlnum;
                } catch (Exception ex) {
                    valid = false;
                    break;
                }
            }
        } else valid = false;

        if (!valid) {
            maplvls = new int[1];
            maplvls[0] = 0;
        }

        //noinspection InfiniteLoopStatement
        while (true) {
            int w = 128;
            int h = 128;

            int lvl = maplvls[idx++ % maplvls.length];
            if (lvl > 1 || lvl < -4) continue;

            short[][] fullmap = createAndValidateMap(w, h, lvl);

            if (fullmap == null) continue;
            short[] map = fullmap[0];

            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int i = x + y * w;

                    if (map[i] == Tiles.get("water").id) pixels[i] = 0x000080;
                    if (map[i] == TestEntrypoint.tater_ore.id) pixels[i] = 0x000080;
                    if (map[i] == Tiles.get("gold Ore").id) pixels[i] = 0x000080;
                    if (map[i] == Tiles.get("gem Ore").id) pixels[i] = 0x000080;
                    if (map[i] == Tiles.get("grass").id) pixels[i] = 0x208020;
                    if (map[i] == Tiles.get("rock").id) pixels[i] = 0xa0a0a0;
                    if (map[i] == Tiles.get("dirt").id) pixels[i] = 0x604040;
                    if (map[i] == Tiles.get("sand").id) pixels[i] = 0xa0a040;
                    if (map[i] == Tiles.get("Stone Bricks").id) pixels[i] = 0xa0a040;
                    if (map[i] == Tiles.get("tree").id) pixels[i] = 0x003000;
                    if (map[i] == Tiles.get("Obsidian Wall").id) pixels[i] = 0x0aa0a0;
                    if (map[i] == Tiles.get("Obsidian").id) pixels[i] = 0x000000;
                    if (map[i] == Tiles.get("lava").id) pixels[i] = 0xff2020;
                    if (map[i] == Tiles.get("cloud").id) pixels[i] = 0xa0a0a0;
                    if (map[i] == Tiles.get("Stairs Down").id) pixels[i] = 0xffffff;
                    if (map[i] == Tiles.get("Stairs Up").id) pixels[i] = 0xffffff;
                    if (map[i] == Tiles.get("Cloud Cactus").id) pixels[i] = 0xff00ff;
                }
            }
            img.setRGB(0, 0, w, h, pixels, 0, w);
            JOptionPane.showMessageDialog(null, null, "Another Map", JOptionPane.PLAIN_MESSAGE, new ImageIcon(img.getScaledInstance(w * 4, h * 4, Image.SCALE_AREA_AVERAGING)));
            if (worldSeed == 0x100)
                worldSeed = 0xAAFF20;
            else
                worldSeed = 0x100;
        }
    }
}
