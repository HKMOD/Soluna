package com.hkmod.soluna.common.blocks.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AnalysisTableEntity(type: BlockEntityType<AnalysisTableEntity>, pos: BlockPos, state: BlockState):
    BlockEntity(type, pos, state) {
}