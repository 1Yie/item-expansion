package moe.ingstar.itemexpansion.registry;

import moe.ingstar.itemexpansion.ItemExpansion;
import moe.ingstar.itemexpansion.effect.EliminateHatred;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffect {
    public static StatusEffect ELIMINATE_HATRED;

    private static StatusEffect registerStatusEffects(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ItemExpansion.MOD_ID, name),
                statusEffect);
    }
    public static void register() {
        ELIMINATE_HATRED = registerStatusEffects("eliminate_hatred", new EliminateHatred(StatusEffectCategory.BENEFICIAL, 0xffffff));
    }
}
