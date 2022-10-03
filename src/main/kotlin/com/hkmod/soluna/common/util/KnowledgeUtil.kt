package com.hkmod.soluna.common.util

import com.hkmod.soluna.common.knowledge.capabilities.IKnowledgeStat
import net.minecraft.world.entity.player.Player

private val capCache = HashMap<Player, IKnowledgeStat>()

val Player.knowledge: IKnowledgeStat?
    get() {
        if (capCache.containsKey(this)) return capCache[this]

        val capability = getCapability(IKnowledgeStat.CAPABILITY)
        return if (capability.isPresent) {
            capability.addListener { capCache.remove(this) }

            val stat = capability.resolve().get()
            capCache[this] = stat
            stat
        } else null
    }