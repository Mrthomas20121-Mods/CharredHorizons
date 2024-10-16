package mrthomas20121.charred_horizons;

import mrthomas20121.charred_horizons.data.CharredDatagen;
import mrthomas20121.charred_horizons.init.*;
import mrthomas20121.charred_horizons.worldgen.CharredRegion;
import mrthomas20121.charred_horizons.worldgen.CharredSurfaceData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(CharredHorizons.MOD_ID)
public class CharredHorizons {

	public static final String MOD_ID = "charred_horizons";

	public CharredHorizons() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		CharredItems.ITEMS.register(bus);
		CharredBlocks.BLOCKS.register(bus);
		CharredBlockEntities.BLOCK_ENTITY_TYPES.register(bus);
		CharredCreativeTabs.CREATIVE_TABS.register(bus);
		CharredEntityTypes.ENTITY_TYPES.register(bus);
		CharredFeatures.FEATURES.register(bus);
		CharredParticleTypes.PARTICLE_TYPES.register(bus);
		CharredLootModifiers.LOOT_MODIFIERS.register(bus);

		bus.addListener(CharredDatagen::init);
		bus.addListener(this::setup);
	}

	public void setup(FMLCommonSetupEvent event) {
		Regions.register(new CharredRegion(new ResourceLocation(MOD_ID, "charred_horizons"), 10));

		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, MOD_ID, CharredSurfaceData.rules());

		event.enqueueWork(() -> {
			BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(CharredTags.Items.DUSTS_SULFUR), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE));
		});
	}
}
