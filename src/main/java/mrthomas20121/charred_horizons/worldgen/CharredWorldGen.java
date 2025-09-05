package mrthomas20121.charred_horizons.worldgen;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import mrthomas20121.charred_horizons.CharredHorizons;
import mrthomas20121.charred_horizons.data.CharredBiomes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;

public class CharredWorldGen {

    public static void init() {

        SurfaceGeneration.addNetherSurfaceRules(new ResourceLocation(CharredHorizons.MOD_ID, "surface_rules"), CharredSurfaceData.rules());

        BiomePlacement.replaceNether(Biomes.SOUL_SAND_VALLEY, CharredBiomes.DECAYING_VALLEY, 0.5d);
        BiomePlacement.replaceNether(Biomes.CRIMSON_FOREST, CharredBiomes.DESOLATED_FOREST, 0.5d);
        BiomePlacement.replaceNether(Biomes.WARPED_FOREST, CharredBiomes.FUNGAL_ABYSS, 0.5d);
    }
}
