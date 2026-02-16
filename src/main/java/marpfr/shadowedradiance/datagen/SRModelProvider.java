package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.block.LuxCrystalClusterBlock;
import marpfr.shadowedradiance.common.block.SRBlocks;
import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

public class SRModelProvider extends ModelProvider {

    public SRModelProvider(PackOutput output) {
        super(output, ShadowedRadiance.MODID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {

        itemModels.generateFlatItem(SRItems.LUX_CRYSTAL_FRAGMENT_ITEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(SRItems.LUX_CRYSTAL_SHARD_ITEM.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(SRItems.LUX_CRYSTAL_LENS_ITEM.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(SRItems.LUX_BATON_ITEM.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        blockModels.createTrivialCube(SRBlocks.LUX_IMBUED_STONE_BLOCK.get());

        blockModels
                .family(SRBlocks.LUX_IMBUED_STONE_BRICK_BLOCK.get())
                .stairs(SRBlocks.LUX_IMBUED_STONE_BRICK_STAIR_BLOCK.get())
                .slab(SRBlocks.LUX_IMBUED_STONE_BRICK_SLAB_BLOCK.get())
                .wall(SRBlocks.LUX_IMBUED_STONE_BRICK_WALL_BLOCK.get());

        blockModels
                .woodProvider(SRBlocks.LUXWOOD_LOG.get())
                .wood(SRBlocks.LUXWOOD_WOOD.get())
                .logWithHorizontal(SRBlocks.LUXWOOD_LOG.get());
        blockModels
                .woodProvider(SRBlocks.STRIPPED_LUXWOOD_LOG.get())
                .wood(SRBlocks.STRIPPED_LUXWOOD_WOOD.get())
                .logWithHorizontal(SRBlocks.STRIPPED_LUXWOOD_LOG.get());
        blockModels.createTrivialCube(SRBlocks.LUXWOOD_PLANKS.get());

        registerLuxCrystalCluster(blockModels, SRBlocks.LUX_CRYSTAL_CLUSTER.get());
    }

    private static void registerLuxCrystalCluster(@NonNull BlockModelGenerators blockModels, LuxCrystalClusterBlock luxCrystalClusterBlock) {
        MultiVariant multivariant = new MultiVariant(WeightedList.of(new Variant(ModelTemplates.CROSS.create(luxCrystalClusterBlock, TextureMapping.cross(luxCrystalClusterBlock), blockModels.modelOutput))));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(luxCrystalClusterBlock, multivariant).with(BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING));
        blockModels.registerSimpleFlatItemModel(luxCrystalClusterBlock);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return SRBlocks.BLOCKS
                .getEntries()
                .stream()
                .filter(x -> !x.is(SRBlocks.LUX_ACCUMULATOR.getId()))
                .filter(x -> !x.is(SRBlocks.LUX_RELAY.getId()));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return SRItems.ITEMS
                .getEntries()
                .stream()
                .filter(x -> !x.is(SRBlocks.LUX_ACCUMULATOR.getId()))
                .filter(x -> !x.is(SRBlocks.LUX_RELAY.getId()));
    }
}
