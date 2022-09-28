package com.hkmod.soluna.common.blocks

import com.hkmod.soluna.common.blocks.entity.AnalysisTableEntity
import com.hkmod.soluna.common.blocks.entity.SolunaBlockEntities.ANALYSIS_TABLE_ENTITY
import com.hkmod.soluna.common.util.devException
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.EnchantedBookItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
class AnalysisTable(properties: Properties) : Block(properties), EntityBlock {
    companion object {
        val FACING: DirectionProperty = HorizontalDirectionalBlock.FACING
        val HAS_BOOK: BooleanProperty = BlockStateProperties.HAS_BOOK

        private val SHAPE_BASE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0)
        private val SHAPE_POST: VoxelShape = box(4.0, 2.0, 4.0, 12.0, 14.0, 12.0)
        val SHAPE_COMMON: VoxelShape = Shapes.or(SHAPE_BASE, SHAPE_POST)
        private val SHAPE_TOP_PLATE: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 15.0, 16.0)
        val SHAPE_COLLISION: VoxelShape = Shapes.or(SHAPE_COMMON, SHAPE_TOP_PLATE)
        val SHAPE_WEST: VoxelShape = Shapes.or(box(1.0, 10.0, 0.0, 5.333333, 14.0, 16.0), box(5.333333, 12.0, 0.0, 9.666667, 16.0, 16.0), box(9.666667, 14.0, 0.0, 14.0, 18.0, 16.0), SHAPE_COMMON)
        val SHAPE_NORTH: VoxelShape = Shapes.or(box(0.0, 10.0, 1.0, 16.0, 14.0, 5.333333), box(0.0, 12.0, 5.333333, 16.0, 16.0, 9.666667), box(0.0, 14.0, 9.666667, 16.0, 18.0, 14.0), SHAPE_COMMON)
        val SHAPE_EAST: VoxelShape = Shapes.or(box(10.666667, 10.0, 0.0, 15.0, 14.0, 16.0), box(6.333333, 12.0, 0.0, 10.666667, 16.0, 16.0), box(2.0, 14.0, 0.0, 6.333333, 18.0, 16.0), SHAPE_COMMON)
        val SHAPE_SOUTH: VoxelShape = Shapes.or(box(0.0, 10.0, 10.666667, 16.0, 14.0, 15.0), box(0.0, 12.0, 6.333333, 16.0, 16.0, 10.666667), box(0.0, 14.0, 2.0, 16.0, 18.0, 6.333333), SHAPE_COMMON)
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_BOOK, false))
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        val itemStack = player.getItemInHand(hand)

        return if (!state.getValue(HAS_BOOK)) {
            if (placeBook(level, pos, state, itemStack, player)) {
                InteractionResult.CONSUME
            } else {
                InteractionResult.PASS
            }
        } else {
            pullBook(level, pos, state, player, hand)
            InteractionResult.SUCCESS
        }
    }

    private fun placeBook(level: Level, blockPos: BlockPos, state: BlockState, book: ItemStack, player: Player?): Boolean {
        if (state.getValue(HAS_BOOK)) return false
        if (book.isEmpty) return false
        if (book.item !is EnchantedBookItem) return false
        if (level.isClientSide) return true

        val blockEntity = level.getBlockEntity(blockPos)
        if (blockEntity !is AnalysisTableEntity) {
            devException("There is block entity that's not AnalysisTableEntity at $blockPos!!")
            return false
        }

        blockEntity.book = book.split(1)

        level.playSound(null, blockPos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1F, 1F)
        level.playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 0.7F, 1.2F)
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos)
        return true
    }

    /**
     * The type of render function called. [RenderShape.MODEL] for mixed tesr and static model, [RenderShape.ENTITYBLOCK_ANIMATED] for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape = SHAPE_COMMON

    override fun useShapeForLightOcclusion(state: BlockState) = true

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val level = context.level
        val itemStack = context.itemInHand
        val player = context.player
        var hasBook = false
        if (!level.isClientSide && player != null && player.canUseGameMasterBlocks()) {
            val tag = BlockItem.getBlockEntityData(itemStack)
            if (tag != null && tag.contains("book")) {
                hasBook = true
            }
        }
        return defaultBlockState().setValue(FACING, context.horizontalDirection.opposite).setValue(HAS_BOOK, hasBook)
    }

    override fun getShape(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext): VoxelShape {
        return when (pState.getValue(FACING) as Direction) {
            Direction.NORTH -> SHAPE_NORTH
            Direction.SOUTH -> SHAPE_SOUTH
            Direction.EAST -> SHAPE_EAST
            Direction.WEST -> SHAPE_WEST
            else -> SHAPE_COMMON
        }
    }

    override fun rotate(state: BlockState, rotation: Rotation): BlockState {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
    }

    override fun mirror(state: BlockState, mirror: Mirror): BlockState {
        return state.rotate(mirror.getRotation(state.getValue(FACING)))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(FACING, HAS_BOOK)
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (!state.`is`(newState.block)) {
            popBook(state, level, pos)
            super.onRemove(state, level, pos, newState, isMoving)
        }
    }

    private fun pullBook(level: Level, pos: BlockPos, state: BlockState, player: Player, pullHand: InteractionHand) {
        if (level.isClientSide) return
        if (!state.getValue(HAS_BOOK)) return
        if (!player.getItemInHand(pullHand).isEmpty) return

        val blockEntity = level.getBlockEntity(pos)

        if (blockEntity !is AnalysisTableEntity) {
            devException("There is block entity that's not AnalysisTableEntity at $pos!!")
            return
        }

        val book = blockEntity.book.copy()
        blockEntity.book = ItemStack.EMPTY
        player.setItemInHand(pullHand, book)
    }

    private fun popBook(state: BlockState, level: Level, pos: BlockPos) {
        if (level.isClientSide) return
        if (!state.getValue(HAS_BOOK)) return

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is AnalysisTableEntity) {
            val direction = state.getValue(FACING)
            val itemStack = blockEntity.book.copy()
            val x = 0.25F * direction.stepX
            val z = 0.25F * direction.stepZ
            val itemEntity = ItemEntity(level, pos.x + 0.5 + x, pos.y + 1.0, pos.z + 0.5 + z, itemStack)
            itemEntity.setDefaultPickUpDelay()
            level.addFreshEntity(itemEntity)
            blockEntity.book = ItemStack.EMPTY
        } else {
            devException("There is block entity that's not AnalysisTableEntity at $pos!!")
            return
        }

    }

    override fun getCollisionShape(state: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext): VoxelShape = SHAPE_COLLISION
    override fun newBlockEntity(pPos: BlockPos, state: BlockState) = AnalysisTableEntity(ANALYSIS_TABLE_ENTITY, pPos, state)

}