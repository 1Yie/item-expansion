package moe.ingstar.itemexpansion.item;

import moe.ingstar.itemexpansion.registry.ModEffect;

import moe.ingstar.itemexpansion.registry.ModEnchantment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class PeaceManual extends Item {
    public static final Predicate<Entity> PEACE_AREA = (entity) -> entity.isAlive() && !(entity instanceof PlayerEntity);
    private static final int peaceNormalNum = 10;
    public static int stateNum;
    public PeaceManual(Settings settings) {
        super(settings.maxDamage(64));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        int treatyLevel = EnchantmentHelper.getLevel(ModEnchantment.PEACE_TREATY, itemStack);
        int rulesLevel = EnchantmentHelper.getLevel(ModEnchantment.THREE_RULES, itemStack);

        if (!isCooldown(user, itemStack.getItem())) {
            for (LivingEntity livingEntity : user.getWorld().getEntitiesByClass(LivingEntity.class,
                    user.getBoundingBox().expand(peaceNormalNum + (treatyLevel * 10)), PEACE_AREA)) {
                if (livingEntity instanceof MobEntity) {
                    if (((MobEntity) livingEntity).getTarget() == user) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(ModEffect.ELIMINATE_HATRED,
                                10 * 20, 0, true, true));
                    }
                }
            }

            user.getItemCooldownManager().set(this, (15 - (rulesLevel * rulesLevel + 1)) * 20);
            itemStack.damage(1, user, (player -> player.sendToolBreakStatus(user.getActiveHand())));
        }
        return TypedActionResult.success(itemStack);
    }

    private static boolean isCooldown(PlayerEntity player, Item item) {
        return player.getItemCooldownManager().isCoolingDown(item);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int stateLevel = EnchantmentHelper.getLevel(ModEnchantment.PEACE_TREATY, stack);
        tooltip.add(Text.translatable("item.item_expansion.peace_manual.tooltip_1").formatted(Formatting.DARK_GRAY));

        if (stack.hasEnchantments()) {
            stateNum = peaceNormalNum + (stateLevel * 10);
        } else {
            stateNum = peaceNormalNum;
        }
        tooltip.add(Text.translatable("item.item_expansion.peace_manual.state", stateNum).formatted(Formatting.DARK_GRAY));
    }
}
