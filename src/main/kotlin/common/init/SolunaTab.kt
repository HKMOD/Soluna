package com.pleahmacaka.examplemod.common.init

import com.pleahmacaka.examplemod.MODID
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object SolunaTab : CreativeModeTab(-1, MODID) {

    override fun makeIcon(): ItemStack {
        return ItemStack(Items.DIAMOND)
    }

}