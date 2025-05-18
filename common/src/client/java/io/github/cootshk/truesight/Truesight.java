package io.github.cootshk.truesight;

import net.fabricmc.api.ClientModInitializer;
public abstract class Truesight implements ClientModInitializer {
    public static boolean enabled = false;
    @Override
    public void onInitializeClient() {
        System.out.println("[Truesight] Automatically disabled at start!");
    }
}