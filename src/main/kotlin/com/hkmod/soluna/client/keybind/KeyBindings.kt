package com.hkmod.soluna.client.keybind

import net.minecraft.client.KeyMapping
import net.minecraftforge.client.event.RegisterKeyMappingsEvent

object KeyBindings {

    private const val CATEGORY = "key.categories.soluna"

    val KEYBINDINGS = hashSetOf<KeyMapping>()

    fun registerKeybindings(event: RegisterKeyMappingsEvent) {
        KEYBINDINGS.forEach {
            event.register(it)
        }
    }
}