package com.hkmod.soluna.datagen

import com.hkmod.soluna.common.blocks.SolunaBlocks
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import java.util.function.Consumer

class SolunaRecipes(generator: DataGenerator) : RecipeProvider(generator) {
    override fun buildCraftingRecipes(finishedRecipeConsumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(SolunaBlocks.ANALYSIS_TABLE.asItem()).run {
            define('l', Blocks.LECTERN.asItem())
            define('e', Items.LEATHER)
            define('i', Items.INK_SAC)
            define('f', Items.FEATHER)

            pattern("e  ")
            pattern("li ")
            pattern("f  ")

            unlockedBy("has_lectern", has(Blocks.LECTERN.asItem()))
            unlockedBy("has_enchanted_book", has(Items.ENCHANTED_BOOK))
        }.save(finishedRecipeConsumer)
    }
}