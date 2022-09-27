package com.hkmod.soluna.datagen.languages

import com.hkmod.soluna.MODID
import com.hkmod.soluna.common.blocks.SolunaBlocks
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

class SolunaEN(gen: DataGenerator) : LanguageProvider(gen, MODID, "en_us") {
    override fun addTranslations() {
        add(SolunaBlocks.ANALYSIS_TABLE, "Analysis Table")
        // TODO Add Key category translation
        add("itemGroup.soluna", "Soluna")
        add("key.categories.soluna", "Soluna")
    }
}