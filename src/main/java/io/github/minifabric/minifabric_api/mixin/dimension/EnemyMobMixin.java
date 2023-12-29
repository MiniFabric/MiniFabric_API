package io.github.minifabric.minifabric_api.mixin.dimension;

import minicraft.entity.mob.EnemyMob;
import minicraft.entity.mob.MobAi;
import minicraft.gfx.Screen;
import minicraft.gfx.SpriteLinker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnemyMob.class)
public abstract class EnemyMobMixin extends MobAi {
	@Shadow protected SpriteLinker.LinkedSprite[][][] lvlSprites;
	@Shadow public int lvl;
	
	/**
	 * Constructor for a mob with an ai.
	 *
	 * @param sprites   All of this mob's sprites.
	 * @param maxHealth Maximum health of the mob.
	 * @param lifetime  How many ticks this mob can live before its removed.
	 * @param rwTime    How long the mob will walk in a random direction. (random walk duration)
	 * @param rwChance  The chance of this mob will walk in a random direction (random walk chance)
	 */
	protected EnemyMobMixin(SpriteLinker.LinkedSprite[][] sprites, int maxHealth, int lifetime, int rwTime, int rwChance) {
		super(sprites, maxHealth, lifetime, rwTime, rwChance);
	}
	
	/**
	 * Prevent out of bounds exception for some entities with custom dimensions
	 */
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void onRender(Screen screen, CallbackInfo info) {
		if (lvl >= lvlSprites.length || lvl < 0) {
			this.sprites = lvlSprites[0];
			super.render(screen);
			info.cancel();
		}
	}
}