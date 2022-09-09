package com.hkmod.soluna.common.blocks

import com.hkmod.soluna.common.blocks.entity.AnalysisTableEntity
import com.hkmod.soluna.common.blocks.entity.SolunaBlockEntities.ANALYSIS_TABLE_ENTITY
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.state.BlockState

class AnalysisTable(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState) = AnalysisTableEntity(ANALYSIS_TABLE_ENTITY, pPos, pState)

}