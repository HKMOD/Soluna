package com.hkmod.soluna.client.keybind

import com.hkmod.soluna.client.keybind.KeyBindings.KEYBINDINGS
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraftforge.client.event.InputEvent
import org.lwjgl.glfw.GLFW

object KeyBindHandler {

    fun onKeyInput(event: InputEvent.Key) {
        val key = KEYBINDINGS.find { keyMapping ->
            keyMapping.key.value == event.key
        } ?: return

        when (event.action) {
            GLFW.GLFW_PRESS -> {
                pressed(key)
            }
        }
    }

    private fun pressed(kb: KeyMapping) {
        val minecraft: Minecraft = Minecraft.getInstance() ?: return
        val player: LocalPlayer = minecraft.player ?: return

        when (kb) {

        }
    }

}