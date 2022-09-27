package com.hkmod.soluna.common.blocks.entity

import com.hkmod.soluna.Soluna.LOGGER
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
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler

class AnalysisTableEntity(type: BlockEntityType<AnalysisTableEntity>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state) {
    private val inventoryLazy = LazyOptional.of {
        object : ItemStackHandler(1) {
            override fun onContentsChanged(slot: Int) {
                super.onContentsChanged(slot)
                setChanged()


            }

            override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
                return super.isItemValid(slot, stack) && (stack.item is EnchantedBookItem || stack.isEmpty)
            }
        }
    }

    fun setBook(book: ItemStack): Boolean {
        return if (inventoryLazy.resolve().get().isItemValid(0, book)) {
            inventoryLazy.resolve().get().setStackInSlot(0, book)
            true
        } else {
            if (!FMLEnvironment.production) {
                LOGGER.warn("non EnchantedBookItem tried to be inserted to analysis table!")
                Thread.dumpStack()
            }
            false
        }
    }

    fun getBook() = inventoryLazy.resolve().get().getStackInSlot(0)

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

    override fun getUpdateTag(): CompoundTag {
        return getBook().serializeNBT()
    }

    override fun onDataPacket(net: Connection, pkt: ClientboundBlockEntityDataPacket) {
        val item = ItemStack.of(pkt.tag!!)
        setBook(item)
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener>? {
        return ClientboundBlockEntityDataPacket.create(this)
    }
}