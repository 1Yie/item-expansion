package moe.ingstar.itemexpansion;

import moe.ingstar.itemexpansion.registry.ModEffect;
import moe.ingstar.itemexpansion.registry.ModEnchantment;
import moe.ingstar.itemexpansion.registry.ModGroup;
import moe.ingstar.itemexpansion.registry.ModItem;
import moe.ingstar.itemexpansion.util.TeleportationStaffHandler;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemExpansion implements ModInitializer {
	public static final String MOD_ID = "item_expansion";
    public static final Logger LOGGER = LoggerFactory.getLogger("item-expansion");

	@Override
	public void onInitialize() {
		ModItem.register();
		ModGroup.register();
		ModEffect.register();
		ModEnchantment.register();

		TeleportationStaffHandler.init();
	}
}