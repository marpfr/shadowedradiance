package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.block.SRBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class SRBlockTagProvider extends BlockTagsProvider {

    public SRBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ShadowedRadiance.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(SRBlocks.LUX_IMBUED_STONE_BRICK_BLOCK.get())
                .add(SRBlocks.LUX_IMBUED_STONE_BRICK_STAIR_BLOCK.get())
                .add(SRBlocks.LUX_IMBUED_STONE_BRICK_SLAB_BLOCK.get())
                .add(SRBlocks.LUX_IMBUED_STONE_BRICK_WALL_BLOCK.get())
                .add(SRBlocks.LUX_IMBUED_STONE_BLOCK.get())
                .add(SRBlocks.LUX_CRYSTAL_CLUSTER.get());

        this.tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)
                .add(SRBlocks.LUX_CRYSTAL_CLUSTER.get());

    }
}
