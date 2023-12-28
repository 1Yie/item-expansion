package moe.ingstar.itemexpansion.registry;

import moe.ingstar.itemexpansion.ItemExpansion;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModGroup {

    public static final ItemGroup TOOL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.TELEPORTATION_STAFF))
            .displayName(Text.translatable("itemGroup.item_expansion.tool_group"))
            .entries((displayContext, entries) -> {
                entries.add(ModItems.TELEPORTATION_STAFF);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(ItemExpansion.MOD_ID, "tool_group"), TOOL_GROUP);
    }
}
