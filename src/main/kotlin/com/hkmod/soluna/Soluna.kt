package com.hkmod.soluna

import com.hkmod.soluna.client.keybind.KeyBindings
import com.hkmod.soluna.client.renderer.registerRenderers
import com.hkmod.soluna.common.blocks.SolunaBlocks
import com.hkmod.soluna.common.blocks.entity.SolunaBlockEntities
import com.hkmod.soluna.common.items.SolunaItems
import com.hkmod.soluna.common.knowledge.capabilities.attachStats
import com.hkmod.soluna.common.knowledge.registerFields
import com.hkmod.soluna.datagen.datagen
import net.minecraft.world.entity.Entity
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.DIST
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "soluna"

@Mod(MODID)
object Soluna {
    val LOGGER: Logger = LogManager.getLogger()

    init {
        LOGGER.log(Level.INFO, "$MODID has started!")

        SolunaItems.registerItems(MOD_BUS)
        SolunaBlocks.registerBlocks(MOD_BUS)
        SolunaBlockEntities.registerBEs(MOD_BUS)
        MOD_BUS.addListener(::datagen)
        FORGE_BUS.addGenericListener(Entity::class.java, ::attachStats)

        registerFields(MOD_BUS)

        if (DIST.isClient) {
            MOD_BUS.addListener(KeyBindings::registerKeybindings)
            MOD_BUS.addListener(::registerRenderers)
        }
    }
}