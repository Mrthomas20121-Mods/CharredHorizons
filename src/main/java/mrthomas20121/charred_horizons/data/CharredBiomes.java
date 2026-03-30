package mrthomas20121.charred_horizons.data;

import mrthomas20121.charred_horizons.CharredHorizons;
import mrthomas20121.charred_horizons.init.CharredEntityTypes;
import mrthomas20121.charred_horizons.init.CharredParticleTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CharredBiomes {

    public static final ResourceKey<Biome> DECAYING_VALLEY = createKey("decaying_valley");
    public static final ResourceKey<Biome> DESOLATED_FOREST = createKey("desolated_forest");
    public static final ResourceKey<Biome> FUNGAL_ABYSS = createKey("fungal_abyss");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(CharredHorizons.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatureHolderGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarverHolderGetter = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(DECAYING_VALLEY, decaying_valley(placedFeatureHolderGetter, configuredWorldCarverHolderGetter));
        context.register(DESOLATED_FOREST, desolated_forest(placedFeatureHolderGetter, configuredWorldCarverHolderGetter));
        context.register(FUNGAL_ABYSS, fungal_abyss(placedFeatureHolderGetter, configuredWorldCarverHolderGetter));
    }

    private static Biome fungal_abyss(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarverHolderGetter) {
        MobSpawnSettings mobspawnsettings = new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CharredEntityTypes.SPORE_CREEPER.get(), 5, 1, 2))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 80, 1, 1))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2))
                .build();

        return biomeDefinition(
                false,
                2f,
                0f,
                new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(13232852)
                        .skyColor(calculateSkyColor(2.0F))
                        .ambientParticle(new AmbientParticleSettings(CharredParticleTypes.FUNGAL_SPORE.get(), 0.00625F))
                        .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
                        .build(),
                mobspawnsettings,
                new BiomeGenerationSettings.Builder(placedFeatureHolderGetter, configuredWorldCarverHolderGetter)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CharredPlacedFeatures.SHROOMLIGHT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_SOUL_SAND)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GRAVEL_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_BLACKSTONE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GOLD_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_QUARTZ_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_SMALL)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.MYSTIC_MUSHROOM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.MYSTIC_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.MYSTIC_VINES)
                        .build()
                );
    }

    private static Biome desolated_forest(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarverHolderGetter) {
        MobSpawnSettings mobspawnsettings = new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 9, 3, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 5, 3, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CharredEntityTypes.SULFURIC_SKELETON.get(), 5, 1, 3))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2)).build();
        return biomeDefinition(
                false,
                2f,
                0f,
                new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(16762482)
                        .skyColor(calculateSkyColor(2.0F))
                        .ambientParticle(new AmbientParticleSettings(CharredParticleTypes.BLIGHT_SPORE.get(), 0.00625F))
                        .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
                        .build(),
                mobspawnsettings,
                new BiomeGenerationSettings.Builder(placedFeatureHolderGetter, configuredWorldCarverHolderGetter)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_SOUL_SAND)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GRAVEL_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_BLACKSTONE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GOLD_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_QUARTZ_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_SMALL)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.BLIGHT_FUNGUS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.BLIGHT_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.DROOPING_VINES)
                        .build()
        );
    }

    private static Biome decaying_valley(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarverHolderGetter) {
        MobSpawnSettings mobspawnsettings = new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 5, 5, 5))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2))
                .addMobCharge(EntityType.WITHER_SKELETON, 0.7D, 0.15D).addMobCharge(EntityType.GHAST, 0.7D, 0.15D)
                .addMobCharge(EntityType.ENDERMAN, 0.7D, 0.15D).addMobCharge(EntityType.STRIDER, 0.7D, 0.15D)
                .build();

        return biomeDefinition(
                false,
                2f,
                0f,
                new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(6070716)
                        .skyColor(calculateSkyColor(2.0F))
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.ASH, 0.00625F))
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
                        .build(),
                mobspawnsettings,
                new BiomeGenerationSettings.Builder(placedFeatureHolderGetter, configuredWorldCarverHolderGetter)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, NetherPlacements.BASALT_PILLAR)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CharredPlacedFeatures.PATCH_SOUL_FIRE_WITHERED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_SOUL_SAND)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GRAVEL_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_BLACKSTONE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GOLD_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_QUARTZ_NETHER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_SMALL)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.WITHERED_FUNGUS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CharredPlacedFeatures.WITHERED_VEGETATION)
                        .build()
        );
    }

    private static Biome biomeDefinition(boolean precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .build();
    }

    protected static int calculateSkyColor(float p_194844_) {
        float $$1 = p_194844_ / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }
}
