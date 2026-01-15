package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.common.block.SRBlocks;
import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class SRBlockLootTableProvider extends BlockLootSubProvider {


    protected SRBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    public static LootItemConditionalFunction.Builder<?> countBetween(float min, float max) {
        return SetItemCountFunction.setCount(UniformGenerator.between(min, max));
    }

    public static LootItemConditionalFunction.Builder<?> countExact(float value) {
        return SetItemCountFunction.setCount(ConstantValue.exactly(value));
    }

    @Override
    protected void generate() {
        var enchantmentsLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        var itemLookup = registries.lookupOrThrow(Registries.ITEM);

        this.add(SRBlocks.LUX_IMBUED_STONE_BLOCK.get(), block ->
                this.applyExplosionDecay(
                        Items.COBBLESTONE,
                        LootTable.lootTable()
                                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(SRItems.LUX_CRYSTAL_FRAGMENT_ITEM).apply(countBetween(0, 1))).when(this.hasSilkTouch().invert()))
                                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.COBBLESTONE).apply(countExact(1))).when(this.hasSilkTouch().invert()))
                                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(SRItems.LUX_IMBUED_STONE_BLOCK_ITEM).apply(countExact(1))).when(this.hasSilkTouch()))
                )
        );

        this.add(
                SRBlocks.LUX_CRYSTAL_CLUSTER.get(),
                block -> this.createSilkTouchDispatchTable(
                        block,
                        LootItem.lootTableItem(SRItems.LUX_CRYSTAL_FRAGMENT_ITEM)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(enchantmentsLookup.getOrThrow(Enchantments.FORTUNE)))
                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(itemLookup, ItemTags.CLUSTER_MAX_HARVESTABLES)))
                                .otherwise(
                                        this.applyExplosionDecay(
                                                block, LootItem.lootTableItem(SRItems.LUX_CRYSTAL_FRAGMENT_ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                                        )
                                )
                )
        );

        this.dropSelf(SRBlocks.LUX_IMBUED_STONE_BRICK_BLOCK.get());
        this.dropSelf(SRBlocks.LUX_IMBUED_STONE_BRICK_STAIR_BLOCK.get());
        this.add(SRBlocks.LUX_IMBUED_STONE_BRICK_SLAB_BLOCK.get(),
                block -> this.createSlabItemTable(SRBlocks.LUX_IMBUED_STONE_BRICK_SLAB_BLOCK.get()));
        this.dropSelf(SRBlocks.LUX_IMBUED_STONE_BRICK_WALL_BLOCK.get());
    }

    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return SRBlocks.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }
}
