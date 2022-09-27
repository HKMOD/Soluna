package com.hkmod.soluna.common.blocks.entity

import com.hkmod.soluna.common.blocks.AnalysisTable
import com.hkmod.soluna.common.util.devException
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
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
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler

class AnalysisTableEntity(type: BlockEntityType<AnalysisTableEntity>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state) {
    private val inventory = object : ItemStackHandler(1) {
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            setChanged()
            if (level?.isClientSide == false) {
                level!!.setBlockAndUpdate(pos, state.setValue(AnalysisTable.HAS_BOOK, !stacks[0].isEmpty))
            }
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return super.isItemValid(slot, stack) && (stack.item is EnchantedBookItem || stack.isEmpty)
        }
    }
    private val inventoryLazy = LazyOptional.of { inventory }

    var book: ItemStack
        set(value) {
            if (!inventory.isItemValid(0, value)) {
                devException("Please don't put non-enchanted book item into the table, please :)")
            } else {
                inventory.setStackInSlot(0, value)
            }
        }
        get() = inventory.getStackInSlot(0)

    companion object {
        val INVENTORY_CAP: Capability<IItemHandler> = CapabilityManager.get(object : CapabilityToken<IItemHandler>() {})
    }

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (cap == INVENTORY_CAP) return inventoryLazy.cast()

        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryLazy.invalidate()
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