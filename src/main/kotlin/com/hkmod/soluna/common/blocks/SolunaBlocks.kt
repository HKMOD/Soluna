package com.hkmod.soluna.common.blocks

import com.hkmod.soluna.MODID
import com.hkmod.soluna.SolunaTab
import com.hkmod.soluna.common.util.resource
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject
import kotlin.properties.ReadOnlyProperty

object SolunaBlocks {
    private val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)
    private val BLOCK_ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    val RESEARCH_TABLE: Block by registerBlockAndItem("research_table") {
        ResearchTable(BlockBehaviour.Properties.copy(Blocks.LECTERN).noOcclusion())
    }

    private fun registerBlockAndItem(name: String, supplier: () -> Block): ReadOnlyProperty<Any?, Block> {
        BLOCK_ITEMS.register(name) {
            BlockItem(ForgeRegistries.BLOCKS.getValue(name.resource)!!, Item.Properties().tab(SolunaTab))
        }
        return BLOCKS.registerObject(name, supplier)
    }
    fun registerBlocks(bus: IEventBus) {
        BLOCKS.register(bus)
        BLOCK_ITEMS.register(bus)
    }
}