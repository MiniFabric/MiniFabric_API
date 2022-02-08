package io.github.minifabric.minifabric_api.mixin.saveload;

import io.github.minifabric.minifabric_api.impl.entity.PassiveEntityRegistryImpl;
import minicraft.entity.Entity;
import minicraft.saveload.Load;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Load.class)
public class LoadMixin {
    private static String reverseString(String string) {
        StringBuilder gnirts = new StringBuilder();

        for (int i=0; i<string.length(); i++) {
            gnirts.insert(0, string.charAt(i));
        } return gnirts.toString();
    }

    @Redirect(method = "loadEntity(Ljava/lang/String;Lminicraft/saveload/Version;Z)Lminicraft/entity/Entity;", at = @At(value = "INVOKE", target = "Ljava/lang/Class;forName(Ljava/lang/String;)Ljava/lang/Class;"))
    private static Class<?> tryCustomEntity(String className) throws ClassNotFoundException {
        for (int notx = 3; notx < PassiveEntityRegistryImpl.entities.size(); notx++) {
            String emaNssalc = reverseString(className);
            String[] segmentedClassname = emaNssalc.split("\\.");
            String basicName = reverseString(segmentedClassname[0]);

            if (PassiveEntityRegistryImpl.entities.get(notx).getName().contains(basicName)) {
                return PassiveEntityRegistryImpl.entities.get(notx);
            }
        } return Class.forName(className);
    }

    @Inject(method = "getEntity", at = @At(value = "HEAD"), cancellable = true)
    private static void getModdedEntity(String string, int moblvl, CallbackInfoReturnable<Entity> cir) throws InstantiationException, IllegalAccessException {
        for (int x = 0; x < PassiveEntityRegistryImpl.entities.size(); x++) {
            if (PassiveEntityRegistryImpl.entities.get(x).getName().contains(string)) {
                cir.setReturnValue((Entity) PassiveEntityRegistryImpl.entities.get(x).newInstance());
            }
        }
    }
}
