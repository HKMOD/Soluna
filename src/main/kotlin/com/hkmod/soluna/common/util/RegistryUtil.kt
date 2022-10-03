package com.hkmod.soluna.common.util

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.IForgeRegistry

operator fun <T> IForgeRegistry<T>.get(key: ResourceLocation): T? {
    return this.getValue(key)
}