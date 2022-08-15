package com.pleahmacaka.examplemod.client.keybind

import net.minecraft.client.KeyMapping
import net.minecraftforge.client.event.RegisterKeyMappingsEvent

object KeyBinding {

    private const val CATEGORY = "key.categories.soluna"

    val KEYBINDINGS = hashSetOf<KeyMapping>()

    fun registerKeybindings(event: RegisterKeyMappingsEvent) {
        KEYBINDINGS.forEach {
            event.register(it)
        }
    }
}