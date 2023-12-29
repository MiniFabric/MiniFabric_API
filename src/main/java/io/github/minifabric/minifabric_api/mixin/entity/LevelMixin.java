package io.github.minifabric.minifabric_api.mixin.entity;

import io.github.minifabric.minifabric_api.impl.registry.HostileEntityRegistryImpl;
import io.github.minifabric.minifabric_api.impl.registry.PassiveEntityRegistryImpl;
import minicraft.core.Updater;
import minicraft.entity.Entity;
import minicraft.entity.mob.EnemyMob;
import minicraft.entity.mob.PassiveMob;
import minicraft.entity.mob.Player;
import minicraft.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Random;

@Mixin(Level.class)
public class LevelMixin {
    @Shadow @Final public int depth;
    @Shadow public void add(Entity e, int x, int y) {}

    @Shadow private Random random;

    @Redirect(method = "trySpawn", at = @At(value = "INVOKE", target = "Lminicraft/entity/mob/PassiveMob;checkStartPos(Lminicraft/level/Level;II)Z"))
    public boolean disableVanillaPassiveSpawn(Level level, int x, int y) {
        return false;
    }

    @Redirect(method = "trySpawn", at = @At(value = "INVOKE", target = "Lminicraft/entity/mob/EnemyMob;checkStartPos(Lminicraft/level/Level;II)Z"))
    public boolean disableVanillaHostileSpawn(Level level, int x, int y) {
        return false;
    }

    @Inject(method = "trySpawn", at = @At(value = "INVOKE", target = "Lminicraft/core/Updater;getTime()Lminicraft/core/Updater$Time;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    public void rewritePassiveMobSpawn(CallbackInfo ci, int spawnSkipChance, boolean spawned, Iterator var3, Player player, int lvl, int i, int rnd, int nx, int ny, double distance) throws InstantiationException, IllegalAccessException {
        if (depth == 0 && PassiveMob.checkStartPos((Level) (Object) this, nx, ny)) {
            add(PassiveEntityRegistryImpl.getEntity(random.nextInt(PassiveEntityRegistryImpl.getTotalSpawnrate(Updater.getTime())), Updater.getTime()), nx, ny);
            spawned = true;
        }
    }

    @Inject(method = "trySpawn", at = @At(value = "INVOKE", target = "Lminicraft/core/Updater;getTime()Lminicraft/core/Updater$Time;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    public void rewriteHostileMobSpawn(CallbackInfo ci, int spawnSkipChance, boolean spawned, Iterator var3, Player player, int lvl, int i, int rnd, int nx, int ny, double distance) throws InstantiationException, IllegalAccessException {
        if ((Updater.getTime() == Updater.Time.Night && Updater.pastDay1 || depth != 0) && EnemyMob.checkStartPos((Level) (Object) this, nx, ny)) {
            if (depth != -4) {
                add(HostileEntityRegistryImpl.getEntity(random.nextInt(HostileEntityRegistryImpl.getTotalSpawnrate()), lvl), nx, ny);
            } else {
                add(HostileEntityRegistryImpl.getDungeonEntity(random.nextInt(HostileEntityRegistryImpl.getTotalDungeonSpawnrate()), lvl), nx, ny);
            }
            spawned = true;
        }
    }

}
