package io.github.cootshk.truesight.mixin;

import net.minecraft.entity.Entity;
import io.github.cootshk.truesight.Truesight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void onIsInvisible(CallbackInfoReturnable<Boolean> info) {
        if (Truesight.enabled) {
            info.setReturnValue(false);
        }
    }
}
