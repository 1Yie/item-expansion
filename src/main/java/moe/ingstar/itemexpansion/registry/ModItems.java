package moe.ingstar.itemexpansion.registry;

import moe.ingstar.itemexpansion.ItemExpansion;

import moe.ingstar.itemexpansion.item.TeleportationStaff;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item TELEPORTATION_STAFF = new TeleportationStaff(new FabricItemSettings().maxCount(1));


    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(ItemExpansion.MOD_ID, "teleportation_staff"), TELEPORTATION_STAFF);

    }
}
