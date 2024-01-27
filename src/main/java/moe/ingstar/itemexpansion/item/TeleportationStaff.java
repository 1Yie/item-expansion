package moe.ingstar.itemexpansion.item;

import moe.ingstar.itemexpansion.util.NBTHelper;
import net.minecraft.client.item.TooltipContext;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import net.minecraft.text.Text;

import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleportationStaff extends Item {
    private static final NbtCompound nbtCompound = new NbtCompound();
    public TeleportationStaff(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack mainHandItem = user.getMainHandStack();
        ItemStack offHanItem = user.getOffHandStack();

        if (!NBTHelper.hasNbt(mainHandItem, "used")) {
            nbtCompound.putString("world", user.getWorld().getRegistryKey().getValue().toString());
            nbtCompound.putString("x", String.format("%.2f", user.getX()));
            nbtCompound.putString("y", String.format("%.2f", user.getY()));
            nbtCompound.putString("z", String.format("%.2f", user.getZ()));
            nbtCompound.putString("used", "1");

            user.getMainHandStack().setNbt(nbtCompound);
        } else {
            nbtCompound.putString("world", user.getWorld().getRegistryKey().getValue().toString());
            nbtCompound.putString("x", String.format("%.2f", user.getX()));
            nbtCompound.putString("y", String.format("%.2f", user.getY()));
            nbtCompound.putString("z", String.format("%.2f", user.getZ()));

            user.getMainHandStack().setNbt(nbtCompound);
        }

        user.getItemCooldownManager().set(user.getMainHandStack().getItem(), 10);

        if (hand == Hand.MAIN_HAND) {
            user.sendMessage(Text.translatable("item.item_expansion.teleportation_staff.key").formatted(Formatting.WHITE), true);
            return TypedActionResult.success(mainHandItem);
        }
        return TypedActionResult.fail(offHanItem);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return NBTHelper.hasNbt(stack, "used");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound tag = stack.getNbt();

        if (tag != null && tag.contains("x") && tag.contains("y") && tag.contains("z")) {
            NbtElement x = tag.get("x");
            NbtElement y = tag.get("y");
            NbtElement z = tag.get("z");
            NbtElement w = stack.getNbt().get("world");

            Text worldText = Text.translatable("World: " + w).formatted(Formatting.GRAY);
            Text coordsText = Text.translatable("X: " + x + ", Y: " + y + ", Z: " + z).formatted(Formatting.GRAY);

            tooltip.add(worldText);
            tooltip.add(coordsText);
        }


        if (tag != null && !tag.contains("x") && !tag.contains("y") && !tag.contains("z") && !tag.contains("world")) {
            tooltip.add(Text.translatable("item.item_expansion.teleportation_staff.nbt.key").formatted(Formatting.GRAY));
        }

        tooltip.add(Text.translatable("item.item_expansion.teleportation_staff.tooltip_1").formatted(Formatting.DARK_GRAY));
    }
}