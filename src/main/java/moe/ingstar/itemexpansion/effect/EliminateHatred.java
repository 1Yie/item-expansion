package moe.ingstar.itemexpansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EliminateHatred extends StatusEffect {
    public EliminateHatred(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof MobEntity mob) {
            if (mob.getAttacker() instanceof PlayerEntity || mob.getTarget() instanceof PlayerEntity) {
                mob.setAttacker(null);
                mob.setTarget(null);

                if (mob instanceof SkeletonEntity skeleton) {
                    skeleton.stopUsingItem();
                }
            }
        }
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
