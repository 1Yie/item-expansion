package moe.ingstar.itemexpansion.item;

import moe.ingstar.itemexpansion.util.NBTHelper;
import moe.ingstar.itemexpansion.util.TeleportationStaffHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
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
    public static NbtCompound counter = new NbtCompound();

    public static NbtCompound worldCounter = TeleportationStaffHandler.nbtCompound;

    public TeleportationStaff(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack mainHandItem = user.getMainHandStack();
        ItemStack offHanItem = user.getOffHandStack();

        if (!NBTHelper.hasNbt(mainHandItem, "used")) {
            counter.putString("world", user.getWorld().getRegistryKey().getValue().toString());
            counter.putString("x", String.format("%.2f", user.getX()));
            counter.putString("y", String.format("%.2f", user.getY()));
            counter.putString("z", String.format("%.2f", user.getZ()));
            counter.putString("used", "1");

            user.getMainHandStack().setNbt(counter);
        } else {
            NbtCompound nbt = user.getMainHandStack().getNbt();
            counter.putString("world", user.getWorld().getRegistryKey().getValue().toString());
            nbt.putString("x", String.format("%.2f", user.getX()));
            nbt.putString("y", String.format("%.2f", user.getY()));
            nbt.putString("z", String.format("%.2f", user.getZ()));
            user.getMainHandStack().setNbt(nbt);
        }

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
            NbtElement w = worldCounter.get("world");

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
