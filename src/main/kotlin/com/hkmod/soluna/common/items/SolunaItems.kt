package com.hkmod.soluna.common.items

import com.hkmod.soluna.MODID
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object SolunaItems {
    private val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    fun registerItems(bus: IEventBus) = ITEMS.register(bus)
}

object SolunaTab : CreativeModeTab(-1, MODID) {
    override fun makeIcon(): ItemStack {
        return ItemStack(Items.DIAMOND)
    }

}