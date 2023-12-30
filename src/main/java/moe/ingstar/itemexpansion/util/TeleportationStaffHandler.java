package moe.ingstar.itemexpansion.util;

import com.google.common.collect.Sets;
import moe.ingstar.itemexpansion.item.TeleportationStaff;
import moe.ingstar.itemexpansion.registry.ModItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Set;

public class TeleportationStaffHandler {
    public static NbtCompound nbtCompound = TeleportationStaff.counter;
    public static void init() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof LivingEntity && player.getMainHandStack().isOf(ModItems.TELEPORTATION_STAFF)
                    && !(entity instanceof PlayerEntity) && !(entity instanceof EnderDragonEntity) && !(entity instanceof WitherEntity)) {
                if (player.getMainHandStack().getItem() == ModItems.TELEPORTATION_STAFF) {
                    String dimensionNbtId = nbtCompound.getString("world");
                    RegistryKey<World> dimension = player.getEntityWorld().getRegistryKey();
                    String dimensionId = dimension.getValue().toString();


                    if ((NBTHelper.hasNbt(player.getMainHandStack(), "used"))) {
                        if (dimensionId.equals(dimensionNbtId)) {
                            entity.teleport(Double.parseDouble(player.getMainHandStack().getNbt().getString("x")),
                                    Double.parseDouble(player.getMainHandStack().getNbt().getString("y")),
                                    Double.parseDouble(player.getMainHandStack().getNbt().getString("z")));
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
