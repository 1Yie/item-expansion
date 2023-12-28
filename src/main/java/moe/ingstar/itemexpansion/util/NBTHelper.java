package moe.ingstar.itemexpansion.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

/**
 * @author Jay_fearless
 * {@linkplain <a href="https://blog.csdn.net/Jay_fearless/article/details/128769831">
 * Inspired by this blog.</a>}
 */

public class NBTHelper {
    public static boolean hasNbt(ItemStack itemStack, String keyName) {
        return !itemStack.isEmpty() && itemStack.getNbt() != null && itemStack.getNbt().contains(keyName);
    }

    private static void initCompoundNBT(ItemStack itemStack) {
        if (itemStack.getNbt() == null) {
            itemStack.setNbt(new NbtCompound());
        }
    }

    public static NbtCompound getNbt(ItemStack stack, String keyName) {
        initCompoundNBT(stack);

        if (!stack.getNbt().contains(keyName)) {
            putNbt(stack, keyName, new NbtCompound());
        }

        return stack.getNbt().getCompound(keyName);
    }

    public static void putNbt(ItemStack stack, String keyName, NbtCompound compound) {
        initCompoundNBT(stack);

        stack.getNbt().put(keyName, compound);
    }

    public static void removeNbt(ItemStack stack, String keyName) {
        if (stack.getNbt() != null) {
            stack.getNbt().remove(keyName);
        }
    }

    public static void writeToNbt(ItemStack stack, String keyName, long value) {
        initCompoundNBT(stack);

        stack.getNbt().putLong(keyName, value);
    }

    public static long readFromNbt(ItemStack stack, String keyName) {
        initCompoundNBT(stack);

        return stack.getNbt().getLong(keyName);
    }

}
