package mrthomas20121.charred_horizons.data;

import mrthomas20121.charred_horizons.CharredHorizons;
import mrthomas20121.charred_horizons.init.CharredBlocks;
import mrthomas20121.charred_horizons.init.CharredItems;
import mrthomas20121.charred_horizons.init.CharredTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CharredItemTagProvider extends ItemTagsProvider {

    public CharredItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, tagLookupCompletableFuture, CharredHorizons.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(CharredTags.Items.CURIO_RING).add(CharredItems.GOLD_RING.get(), CharredItems.BLIGHT_RING.get());
        tag(CharredTags.Items.CURIO_CHARM).add(CharredItems.MYSTERIOUS_CHARM.get());

        tag(Tags.Items.STRING).add(CharredItems.FIERY_STRING.get());
        tag(Tags.Items.TOOLS_BOWS).add(CharredItems.FIERY_BOW.get());

        tag(ItemTags.PLANKS)
                .add(
                        CharredBlocks.BLIGHT_PLANKS.get().asItem(),
                        CharredBlocks.WITHERED_PLANKS.get().asItem()
                );

        tag(ItemTags.LOGS)
                .add(
                        CharredBlocks.BLIGHT_STEM.get().asItem(),
                        CharredBlocks.WITHERED_STEM.get().asItem()
                );

        tag(CharredTags.Items.LOGS_BLIGHT)
                .add(
                        CharredBlocks.BLIGHT_STEM.get().asItem()
                );

        tag(CharredTags.Items.LOGS_WITHERED)
                .add(
                        CharredBlocks.WITHERED_STEM.get().asItem()
                );
    }
}
