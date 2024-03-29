package moe.ingstar.itemexpansion.util;

import moe.ingstar.itemexpansion.registry.ModItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.registry.RegistryKey;

import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;


import net.minecraft.world.World;

public class TeleportationStaffHandler {
    public static void init() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof LivingEntity && player.getMainHandStack().isOf(ModItem.TELEPORTATION_STAFF)
                    && !(entity instanceof PlayerEntity) && !(entity instanceof EnderDragonEntity) && !(entity instanceof WitherEntity)) {
                if (player.getMainHandStack().getItem() == ModItem.TELEPORTATION_STAFF) {
                    String dimensionNbtId = player.getMainHandStack().getOrCreateNbt().getString("world");
                    RegistryKey<World> dimension = player.getEntityWorld().getRegistryKey();
                    String dimensionId = dimension.getValue().toString();

                    if ((NBTHelper.hasNbt(player.getMainHandStack(), "used"))) {
                        if (dimensionId.equals(dimensionNbtId)) {
                            entity.teleport(Double.parseDouble(player.getMainHandStack().getNbt().getString("x")),
                                    Double.parseDouble(player.getMainHandStack().getNbt().getString("y")),
                                    Double.parseDouble(player.getMainHandStack().getNbt().getString("z")));

                            player.sendMessage(Text.translatable("item.item_expansion.teleportation_staff.key.success",
                                    Double.parseDouble(player.getMainHandStack().getNbt().getString("x")),
                                    Double.parseDouble(player.getMainHandStack().getNbt().getString("y")),
                                    Double.parseDouble(player.getMainHandStack().getNbt().getString("z"))).formatted(Formatting.WHITE), true);
                        } else {
                            player.sendMessage(Text.translatable("item.item_expansion.teleportation_staff.key.fail").formatted(Formatting.DARK_RED), true);
                            return ActionResult.FAIL;
                        }

                        player.getMainHandStack().getNbt().remove("used");
                        player.getMainHandStack().getNbt().remove("world");
                        player.getMainHandStack().getNbt().remove("x");
                        player.getMainHandStack().getNbt().remove("y");
                        player.getMainHandStack().getNbt().remove("z");

                        player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 20 * 15);

                        return ActionResult.SUCCESS;
                    }
                }
            }

            return ActionResult.PASS;
        });
    }
}