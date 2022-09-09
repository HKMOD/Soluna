package com.hkmod.soluna.common.blocks.entity

import com.hkmod.soluna.MODID
import com.hkmod.soluna.common.blocks.SolunaBlocks.ANALYSIS_TABLE
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object SolunaBlockEntities {
    private val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<out BlockEntity>> = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID)
    val ANALYSIS_TABLE_ENTITY: BlockEntityType<AnalysisTableEntity> by BLOCK_ENTITIES.registerObject("analysis_table") {
        BlockEntityType.Builder.of({ blockPos: BlockPos, blockState: BlockState ->
            AnalysisTableEntity(ANALYSIS_TABLE_ENTITY, blockPos, blockState)
        }, ANALYSIS_TABLE).build(null)
    }

    fun registerBEs(bus: IEventBus) {
        BLOCK_ENTITIES.register(bus)
    }
}