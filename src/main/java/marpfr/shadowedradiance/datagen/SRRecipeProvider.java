package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.common.block.SRBlocks;
import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class SRRecipeProvider extends RecipeProvider {

    protected SRRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        this.shaped(RecipeCategory.MISC, SRItems.LUX_CRYSTAL_SHARD_ITEM.get(), 1)
                .pattern(" FF")
                .pattern("FFF")
                .pattern("FF ")
                .define('F', SRItems.LUX_CRYSTAL_FRAGMENT_ITEM.get())
                .unlockedBy("has_lux_crystal_fragement", this.has(SRItems.LUX_CRYSTAL_FRAGMENT_ITEM))
                .save(this.output);

        this.shaped(RecipeCategory.BUILDING_BLOCKS, SRItems.LUX_IMBUED_STONE_BRICK_BLOCK_ITEM.get())
                .pattern("BB")
                .pattern("BB")
                .define('B', SRItems.LUX_IMBUED_STONE_BLOCK_ITEM.get())
                .unlockedBy("has_lux_stone", this.has(SRItems.LUX_IMBUED_STONE_BLOCK_ITEM))
                .save(this.output);

        this.shaped(RecipeCategory.MISC, SRItems.LUX_CRYSTAL_LENS_ITEM.get())
                .pattern("KCK")
                .pattern("GPG")
                .pattern("KCK")
                .define('K', Items.COPPER_INGOT)
                .define('C', SRItems.LUX_CRYSTAL_SHARD_ITEM)
                .define('G', Items.GOLD_INGOT)
                .define('P', Items.GLASS_PANE)
                .unlockedBy("has_lux_crystal_shard", this.has(SRItems.LUX_CRYSTAL_SHARD_ITEM))
                .save(this.output);

        this.registerBuildingBlockRecipes(
                "lux_stone_brick",
                SRBlocks.LUX_IMBUED_STONE_BRICK_BLOCK,
                SRBlocks.LUX_IMBUED_STONE_BRICK_STAIR_BLOCK,
                SRBlocks.LUX_IMBUED_STONE_BRICK_SLAB_BLOCK,
                SRBlocks.LUX_IMBUED_STONE_BRICK_WALL_BLOCK);
    }

    private void registerBuildingBlockRecipes(String unlockName, ItemLike baseBlock, ItemLike stairBlock, ItemLike slabBlock, ItemLike wallBlock) {
        var hasKey = "has_" + unlockName;

        this.stairBuilder(stairBlock, Ingredient.of(baseBlock)).unlockedBy(hasKey, this.has(baseBlock)).save(this.output);
        this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, slabBlock, Ingredient.of(baseBlock)).unlockedBy(hasKey, this.has(baseBlock)).save(this.output);
        this.wallBuilder(RecipeCategory.BUILDING_BLOCKS, wallBlock, Ingredient.of(baseBlock)).unlockedBy(hasKey, this.has(baseBlock)).save(this.output);
    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {

        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
            return new SRRecipeProvider(provider, output);
        }

        @Override
        public @NonNull String getName() {
            return "Shadowed Radiance Recipes";
        }
    }
}
