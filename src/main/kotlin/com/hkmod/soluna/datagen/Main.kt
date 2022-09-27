package com.hkmod.soluna.datagen

import com.hkmod.soluna.datagen.languages.SolunaEN
import com.hkmod.soluna.datagen.languages.SolunaKR
import net.minecraftforge.data.event.GatherDataEvent

fun datagen(event: GatherDataEvent) {
    event.generator.run {
        addProvider(event.includeServer(), SolunaRecipes(this))
        addProvider(event.includeClient(), SolunaKR(this))
        addProvider(event.includeClient(), SolunaEN(this))
    }
}