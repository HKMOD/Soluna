package com.hkmod.soluna.common.init

import com.hkmod.soluna.MODID
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object BlockInit {

    private val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)

    fun register(bus: IEventBus) = BLOCKS.register(bus)
    // Example Block
}