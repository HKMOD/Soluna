package com.hkmod.soluna.common.util

import com.hkmod.soluna.Soluna
import net.minecraftforge.fml.loading.FMLEnvironment

fun devException(message: String) {
    if (FMLEnvironment.production) {
        Soluna.LOGGER.error(message)
    } else {
        throw IllegalStateException(message)
    }
}