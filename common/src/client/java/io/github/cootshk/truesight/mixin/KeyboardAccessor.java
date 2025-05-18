package io.github.cootshk.truesight.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.Keyboard;

@Mixin(Keyboard.class)
public interface KeyboardAccessor {
    @Invoker
    void invokeDebugLog(String key, Object... args);
}