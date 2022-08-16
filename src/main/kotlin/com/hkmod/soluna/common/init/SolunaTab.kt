package com.hkmod.soluna.common.init

import com.hkmod.soluna.MODID
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object SolunaTab : CreativeModeTab(-1, MODID) {

    override fun makeIcon(): ItemStack {
        return ItemStack(Items.DIAMOND)
    }

}