package moe.ingstar.itemexpansion.registry;

import moe.ingstar.itemexpansion.ItemExpansion;

import moe.ingstar.itemexpansion.item.PeaceManual;
import moe.ingstar.itemexpansion.item.TeleportationStaff;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItem {
    public static final Item TELEPORTATION_STAFF = new TeleportationStaff(new FabricItemSettings().maxCount(1));
    public static final Item PEACE_MANUAL = new PeaceManual(new FabricItemSettings());


    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(ItemExpansion.MOD_ID, "teleportation_staff"), TELEPORTATION_STAFF);
        Registry.register(Registries.ITEM, new Identifier(ItemExpansion.MOD_ID, "peace_manual"), PEACE_MANUAL);
    }
}
