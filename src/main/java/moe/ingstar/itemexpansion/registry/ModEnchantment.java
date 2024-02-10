package moe.ingstar.itemexpansion.registry;

import moe.ingstar.itemexpansion.ItemExpansion;
import moe.ingstar.itemexpansion.enchantment.PeaceTreaty;
import moe.ingstar.itemexpansion.enchantment.ThreeRules;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantment {
    public static final Identifier PEACE_TREATY_ID = new Identifier(ItemExpansion.MOD_ID, "peace_treaty");
    public static final Identifier THREE_RULES_ID = new Identifier(ItemExpansion.MOD_ID, "three_rules");

    public static final Enchantment PEACE_TREATY = new PeaceTreaty(Enchantment.Rarity.RARE,
            EnchantmentTarget.VANISHABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment THREE_RULES = new ThreeRules(Enchantment.Rarity.RARE,
            EnchantmentTarget.VANISHABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

    public static void register() {
        Registry.register(Registries.ENCHANTMENT, PEACE_TREATY_ID, PEACE_TREATY);
        Registry.register(Registries.ENCHANTMENT, THREE_RULES_ID, THREE_RULES);
    }
}
