package io.github.minifabric.minifabric_api.mixin.resource;

import minicraft.core.Renderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Renderer.class)
public class RendererMixin {
    /*@Inject(method = "loadDefaultSpriteSheets", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;", ordinal = 4))
    private static void injectModSpriteSheets(CallbackInfoReturnable<SpriteSheet[]> cir) {
        //FabricRenderer.modSpriteSheets.forEach();
    }*/
}
