package mrthomas20121.charred_horizons.block;

import mrthomas20121.charred_horizons.data.CharredConfiguredFeatures;
import mrthomas20121.charred_horizons.init.CharredBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraftforge.common.IPlantable;

public class CharredNyliumBlock extends NyliumBlock {

    public CharredNyliumBlock(Properties p_55057_) {
        super(p_55057_);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.relative(facing));
        if(state.is(CharredBlocks.WITHERED_NYLIUM.get()) && plant.is(Blocks.WITHER_ROSE)) {
            return true;
        }
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }

    private static boolean canBeNylium(BlockState p_55079_, LevelReader p_55080_, BlockPos p_55081_) {
        BlockPos blockpos = p_55081_.above();
        BlockState blockstate = p_55080_.getBlockState(blockpos);
        int i = LightEngine.getLightBlockInto(p_55080_, p_55079_, p_55081_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(p_55080_, blockpos));
        return i < p_55080_.getMaxLightLevel();
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource random) {
        if (!canBeNylium(state, level, blockPos)) {
            if(state.is(CharredBlocks.WITHERED_NYLIUM.get())) {
                level.setBlockAndUpdate(blockPos, Blocks.SOUL_SAND.defaultBlockState());
            }
            else {
                level.setBlockAndUpdate(blockPos, Blocks.NETHERRACK.defaultBlockState());
            }
        }

    }

    public void performBonemeal(ServerLevel p_221825_, RandomSource p_221826_, BlockPos p_221827_, BlockState p_221828_) {
        BlockState blockstate = p_221825_.getBlockState(p_221827_);
        BlockPos blockpos = p_221827_.above();
        ChunkGenerator chunkgenerator = p_221825_.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = p_221825_.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        if (blockstate.is(Blocks.CRIMSON_NYLIUM)) {
            this.place(registry, NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL, p_221825_, chunkgenerator, p_221826_, blockpos);
        } else if (blockstate.is(Blocks.WARPED_NYLIUM)) {
            this.place(registry, NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL, p_221825_, chunkgenerator, p_221826_, blockpos);
            this.place(registry, NetherFeatures.NETHER_SPROUTS_BONEMEAL, p_221825_, chunkgenerator, p_221826_, blockpos);
            if (p_221826_.nextInt(8) == 0) {
                this.place(registry, NetherFeatures.TWISTING_VINES_BONEMEAL, p_221825_, chunkgenerator, p_221826_, blockpos);
            }
        }
        else if (blockstate.is(CharredBlocks.BLIGHT_NYLIUM.get())) {
            this.place(registry, CharredConfiguredFeatures.BLIGHT_VEGETATION, p_221825_, chunkgenerator, p_221826_, blockpos);
        }
        else if (blockstate.is(CharredBlocks.WITHERED_NYLIUM.get())) {
            this.place(registry, CharredConfiguredFeatures.WITHERED_VEGETATION, p_221825_, chunkgenerator, p_221826_, blockpos);
        }
        else if (blockstate.is(CharredBlocks.MYSTIC_NYLIUM.get())) {
            this.place(registry, CharredConfiguredFeatures.MYSTIC_VEGETATION, p_221825_, chunkgenerator, p_221826_, blockpos);
            if (p_221826_.nextInt(8) == 0) {
                this.place(registry, CharredConfiguredFeatures.MYSTIC_VINES_BONE_MEAL, p_221825_, chunkgenerator, p_221826_, blockpos);
            }
        }

    }

    private void place(Registry<ConfiguredFeature<?, ?>> p_255879_, ResourceKey<ConfiguredFeature<?, ?>> p_256032_, ServerLevel p_255631_, ChunkGenerator p_256445_, RandomSource p_255709_, BlockPos p_256019_) {
        p_255879_.getHolder(p_256032_).ifPresent((p_255920_) -> {
            p_255920_.value().place(p_255631_, p_256445_, p_255709_, p_256019_);
        });
    }
}
