package com.hkmod.soluna.datagen.languages

import com.hkmod.soluna.MODID
import com.hkmod.soluna.common.blocks.SolunaBlocks
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

class SolunaKR(gen: DataGenerator) : LanguageProvider(gen, MODID, "ko_kr") {
    override fun addTranslations() {
        add(SolunaBlocks.ANALYSIS_TABLE, "분석 탁자")
        // TODO 키 카테고리 번역 추가하기
        add("itemGroup.soluna", "Soluna")
        add("key.categories.soluna", "Soluna")
    }
}