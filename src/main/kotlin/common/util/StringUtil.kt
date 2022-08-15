package com.pleahmacaka.examplemod.common.util

import com.pleahmacaka.examplemod.MODID
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

val String.resource: ResourceLocation
    get() = ResourceLocation(MODID, this)

val String.component: Component
    get() = Component.literal(this)
