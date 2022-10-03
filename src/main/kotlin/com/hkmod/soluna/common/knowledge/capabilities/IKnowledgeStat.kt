package com.hkmod.soluna.common.knowledge.capabilities

import com.hkmod.soluna.common.knowledge.FIELDS
import com.hkmod.soluna.common.knowledge.fields.KnowledgeField
import com.hkmod.soluna.common.util.devException
import com.hkmod.soluna.common.util.get
import com.hkmod.soluna.common.util.resource
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraftforge.common.capabilities.*
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.event.AttachCapabilitiesEvent

@AutoRegisterCapability
interface IKnowledgeStat: INBTSerializable<CompoundTag> {
    companion object {
        val CAPABILITY = CapabilityManager.get(object: CapabilityToken<IKnowledgeStat>() {})
    }

    val knowledgeStats: MutableMap<KnowledgeField, Double>

    fun knows(field: KnowledgeField): Boolean = knowledgeStats.containsKey(field)

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        knowledgeStats.forEach { (field, level) ->
            tag.putDouble(field.registryKey.toString(), level)
        }
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        if (nbt.type != CompoundTag.TYPE) devException("Unable to deserialize tag into Knowledge status! Tag: $nbt")
        val stats = nbt.allKeys.map {
            FIELDS[ResourceLocation(it)]!! to nbt.getDouble(it)
        }

        knowledgeStats.clear()
        knowledgeStats.putAll(stats)
    }
}

fun attachStats(event: AttachCapabilitiesEvent<Entity>) {
    if (event.`object` !is Player) return

    val provider = object: ICapabilitySerializable<CompoundTag> {
        val backend = KnowledgeStat()
        val cache = LazyOptional.of { backend }

        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            return if (cap == IKnowledgeStat.CAPABILITY) cache.cast()
                else LazyOptional.empty()
        }

        override fun serializeNBT() = backend.serializeNBT()

        override fun deserializeNBT(nbt: CompoundTag) = backend.deserializeNBT(nbt)
    }

    event.addCapability("knowledge_stat".resource, provider)
}