package com.pleahmacaka.examplemod

import com.pleahmacaka.examplemod.common.init.BlockInit
import com.pleahmacaka.examplemod.common.init.ItemInit
import com.pleahmacaka.examplemod.common.init.SolunaTab
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "soluna"

@Mod(MODID)
object ExampleMod {
    private val LOGGER: Logger = LogManager.getLogger()

    init {
        LOGGER.log(Level.INFO, "$MODID has started!")

        ItemInit.register(MOD_BUS)
        BlockInit.register(MOD_BUS)
        SolunaTab
    }
}