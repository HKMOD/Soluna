package com.hkmod.soluna.common.blocks.entity

import com.hkmod.soluna.common.blocks.AnalysisTable
import com.hkmod.soluna.common.blocks.SolunaBlocks
import com.hkmod.soluna.common.util.devException
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.Connection
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.item.EnchantedBookItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AnalysisTableEntity(type: BlockEntityType<AnalysisTableEntity>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state) {
    var book: ItemStack = ItemStack.EMPTY
        set(value) {
            if (!isItemValid(value)) {
                devException("Please don't put non-enchanted book item into the table, please :)")
            } else {
                field = value
                if (level?.isClientSide == false) {
                    val blockState = level!!.getBlockState(blockPos)
                    if (blockState.`is`(SolunaBlocks.ANALYSIS_TABLE)) {
                        level!!.setBlockAndUpdate(blockPos, blockState.setValue(AnalysisTable.HAS_BOOK, !value.isEmpty))
                    }
                }
            }
        }

    fun isItemValid(itemStack: ItemStack): Boolean {
        return itemStack.item is EnchantedBookItem || itemStack.isEmpty
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("book", book.serializeNBT())
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        book = ItemStack.of(tag.getCompound("book"))
    }

    override fun getUpdateTag(): CompoundTag {
        return book.serializeNBT()
    }

    override fun onDataPacket(connection: Connection, packet: ClientboundBlockEntityDataPacket) {
        val item = ItemStack.of(packet.tag!!)
        book = item
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener>? {
        return ClientboundBlockEntityDataPacket.create(this)
    }
}