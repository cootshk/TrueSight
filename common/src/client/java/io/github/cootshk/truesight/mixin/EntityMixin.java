package io.github.cootshk.truesight.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import io.github.cootshk.truesight.Truesight;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Scores;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Invoker("getType")
    public abstract EntityType<?> getType();

    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void onIsInvisible(CallbackInfoReturnable<Boolean> info) {
        // Server-side opt out
        // get the current player
        // 0: allow
        // 1: disable
        // 2: allow (no players)
        int score = 2;
        try {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) {
                return;
            };
            Scoreboard scoreboard = player.getScoreboard();
            if (scoreboard != null) {
                ScoreboardObjective objective = scoreboard.getNullableObjective("disableTrueSight");
                if (objective != null) {
                    Scores scores = scoreboard.getScores(player.getNameForScoreboard());
                    if (scores != null) {
                        score = Objects.requireNonNull(scores.get(objective)).getScore();
                    }
                }
            }
        } catch (Exception ignored) {
            System.out.println("error!");
        };
        System.out.println("Score: " + score);
        if (score == 1) { // disabled
            return;
        }
        if (score == 2) { // disable for players (default)
            if (getType().equals(EntityType.PLAYER)) {
                return;
            }
        }
        if (Truesight.enabled) {
            info.setReturnValue(false);
        }
    }
}
