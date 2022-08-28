package com.hkmod.soluna

import com.hkmod.soluna.client.keybind.KeyBindings
import com.hkmod.soluna.common.blocks.SolunaBlocks
import com.hkmod.soluna.common.items.SolunaItems
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.DIST
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "soluna"

@Mod(MODID)
object Soluna {
    private val LOGGER: Logger = LogManager.getLogger()

    init {
        LOGGER.log(Level.INFO, "$MODID has started!")

        SolunaItems.registerItems(MOD_BUS)
        SolunaBlocks.registerBlocks(MOD_BUS)

        if (DIST.isClient) {
            MOD_BUS.addListener(KeyBindings::registerKeybindings)
        }
    }
}

object SolunaTab : CreativeModeTab(-1, MODID) {
    override fun makeIcon(): ItemStack {
        return ItemStack(Items.DIAMOND)
    }

}