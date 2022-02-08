package io.github.minifabric.minifabric_api.mixin.saveload;

import io.github.minifabric.minifabric_api.impl.entity.HostileEntityRegistryImpl;
import io.github.minifabric.minifabric_api.impl.entity.PassiveEntityRegistryImpl;
import minicraft.entity.Entity;
import minicraft.saveload.Load;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tinylog.Logger;

import java.lang.reflect.InvocationTargetException;

@Mixin(Load.class)
public class LoadMixin {
    @Unique private static String saveName;

    @Inject(method = "<init>(Ljava/lang/String;Z)V", at = @At(value = "TAIL"))
    private void getWorldName(String worldname, boolean loadGame, CallbackInfo ci) {
        saveName = worldname;
    }

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
        } for (int notx = 4; notx < HostileEntityRegistryImpl.entities.size(); notx++) {
            String emaNssalc = reverseString(className);
            String[] segmentedClassname = emaNssalc.split("\\.");
            String basicName = reverseString(segmentedClassname[0]);

            if (HostileEntityRegistryImpl.entities.get(notx).getName().contains(basicName)) {
                return HostileEntityRegistryImpl.entities.get(notx);
            }
        } for (int notx = 2; notx < HostileEntityRegistryImpl.dungeonEntities.size(); notx++) {
            String emaNssalc = reverseString(className);
            String[] segmentedClassname = emaNssalc.split("\\.");
            String basicName = reverseString(segmentedClassname[0]);

            if (HostileEntityRegistryImpl.dungeonEntities.get(notx).getName().contains(basicName)) {
                return HostileEntityRegistryImpl.dungeonEntities.get(notx);
            }
        } return Class.forName(className);
    }

    @Inject(method = "getEntity", at = @At(value = "HEAD"), cancellable = true)
    private static void getModdedEntity(String string, int moblvl, CallbackInfoReturnable<Entity> cir) {
        try {
            for (int x = 0; x < PassiveEntityRegistryImpl.entities.size(); x++) {
                if (PassiveEntityRegistryImpl.entities.get(x).getName().contains(string)) {
                    cir.setReturnValue((Entity) PassiveEntityRegistryImpl.entities.get(x).newInstance());
                }
            }
            for (int x = 0; x < HostileEntityRegistryImpl.entities.size(); x++) {
                if (HostileEntityRegistryImpl.entities.get(x).getName().contains(string)) {
                    cir.setReturnValue((Entity) HostileEntityRegistryImpl.entities.get(x).getConstructors()[0].newInstance(moblvl));
                }
            }
            for (int x = 0; x < HostileEntityRegistryImpl.dungeonEntities.size(); x++) {
                if (HostileEntityRegistryImpl.dungeonEntities.get(x).getName().contains(string)) {
                    cir.setReturnValue((Entity) HostileEntityRegistryImpl.dungeonEntities.get(x).getConstructors()[0].newInstance(moblvl));
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Logger.error("Invalid hostile entity found in save file for world" + saveName);
            e.printStackTrace();
        }
    }
}
