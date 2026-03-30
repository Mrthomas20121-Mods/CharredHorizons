package mrthomas20121.charred_horizons.worldgen;

import mrthomas20121.charred_horizons.data.CharredBiomes;
import mrthomas20121.charred_horizons.init.CharredBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class CharredSurfaceData {

    public static SurfaceRules.ConditionSource ABOVE_31 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);
    public static SurfaceRules.ConditionSource ABOVE_30 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0);
    public static SurfaceRules.ConditionSource NOT_ABOVE_35 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0));
    public static SurfaceRules.ConditionSource NETHERRACK = SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.54d);
    public static SurfaceRules.ConditionSource NETHER_WART = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17d);
    public static SurfaceRules.ConditionSource NETHER_STATE_SELECTOR = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0d);
    public static SurfaceRules.ConditionSource PATCH = SurfaceRules.noiseCondition(Noises.PATCH, -0.012D);

    private static final SurfaceRules.RuleSource GRAVEL = SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());
    public static SurfaceRules.RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
    public static SurfaceRules.RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
    public static SurfaceRules.RuleSource WITHERED_NYLIUM = SurfaceRules.state(CharredBlocks.WITHERED_NYLIUM.get().defaultBlockState());
    public static SurfaceRules.RuleSource WITHERED_WART = SurfaceRules.state(CharredBlocks.WITHERED_NETHER_WART_BLOCK.get().defaultBlockState());
    public static SurfaceRules.RuleSource BLIGHT_NYLIUM = SurfaceRules.state(CharredBlocks.BLIGHT_NYLIUM.get().defaultBlockState());
    public static SurfaceRules.RuleSource BLIGHT_WART = SurfaceRules.state(CharredBlocks.BLIGHT_NETHER_WART_BLOCK.get().defaultBlockState());
    public static SurfaceRules.RuleSource MYSTIC_NYLIUM = SurfaceRules.state(CharredBlocks.MYSTIC_NYLIUM.get().defaultBlockState());
    public static SurfaceRules.RuleSource MYSTIC_WART = SurfaceRules.state(CharredBlocks.MYSTIC_MUSHROOM_STEM.get().defaultBlockState());

    public static SurfaceRules.RuleSource GRAVEL_COND = SurfaceRules.ifTrue(PATCH, SurfaceRules.ifTrue(ABOVE_30, SurfaceRules.ifTrue(NOT_ABOVE_35, GRAVEL)));

    public static SurfaceRules.RuleSource GROUND_BLOCKS(SurfaceRules.RuleSource wart, SurfaceRules.RuleSource nylium) {
        return SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.not(NETHERRACK),
                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.TOP, 0)), SurfaceRules.ifTrue(ABOVE_31,
                        SurfaceRules.sequence(SurfaceRules.ifTrue(NETHER_WART, wart), nylium)))));
    }

    public static SurfaceRules.RuleSource decaying_valley() {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CharredBiomes.DECAYING_VALLEY),
                SurfaceRules.sequence(
                        GROUND_BLOCKS(WITHERED_WART, WITHERED_NYLIUM),
                        SurfaceRules.ifTrue(
                                SurfaceRules.UNDER_CEILING,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(NETHER_STATE_SELECTOR, SOUL_SAND), SOUL_SOIL)),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.sequence(GRAVEL_COND,
                                        SurfaceRules.ifTrue(NETHER_STATE_SELECTOR, SOUL_SAND), SOUL_SOIL))
                )
        );
    }

    public static SurfaceRules.RuleSource desolated_forest() {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CharredBiomes.DESOLATED_FOREST), GROUND_BLOCKS(BLIGHT_WART, BLIGHT_NYLIUM));
    }

    public static SurfaceRules.RuleSource fungal_abyss() {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CharredBiomes.FUNGAL_ABYSS), GROUND_BLOCKS(MYSTIC_WART, MYSTIC_NYLIUM));
    }

    public static SurfaceRules.RuleSource rules() {
        return SurfaceRules.sequence(
                decaying_valley(), desolated_forest(), fungal_abyss()
        );
    }
}
