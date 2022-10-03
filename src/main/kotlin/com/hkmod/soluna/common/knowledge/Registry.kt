package com.hkmod.soluna.common.knowledge

import com.hkmod.soluna.MODID
import com.hkmod.soluna.Soluna.LOGGER
import com.hkmod.soluna.common.knowledge.fields.*
import com.hkmod.soluna.common.util.resource
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryBuilder
import net.minecraftforge.registries.RegistryManager
import thedarkcolour.kotlinforforge.forge.registerObject

private val DEFERRED = DeferredRegister.create<KnowledgeField>("fields".resource, MODID)

val FIELDS: IForgeRegistry<KnowledgeField> by DEFERRED.makeRegistry {
    val builder = RegistryBuilder<KnowledgeField>()

    builder.onAdd { owner, stage, id, key, obj, oldObj ->
        if (stage == RegistryManager.ACTIVE) {
            LOGGER.debug("Registered field <${obj.registryKey}>")
        }
    }
    builder.onCreate { _, stage ->
        if (stage == RegistryManager.ACTIVE) {
            LOGGER.debug("Soluna Knowledge registry has been created during stage ${stage.name}")
        }
    }

    return@makeRegistry builder
}.run { lazy { get() } }

val ANALYTICS: KnowledgeField by DEFERRED.registerObject("analytics") { Analytics }
val ENCHANTING: KnowledgeField by DEFERRED.registerObject("enchanting") { Enchanting }
val PHENOMENOLOGY: KnowledgeField by DEFERRED.registerObject("phenomenology") { Phenomenology }
val DYNAMICS: KnowledgeField by DEFERRED.registerObject("dynamics") { Dynamics }
val ASTRONOMY: KnowledgeField by DEFERRED.registerObject("astronomy") { Astronomy }
val PYROLOGY: KnowledgeField by DEFERRED.registerObject("pyrology") { Pyrology }
val VITALOGY: KnowledgeField by DEFERRED.registerObject("vitalogy") { Vitalogy }
val HYDROLOGY: KnowledgeField by DEFERRED.registerObject("hydrology") { Hydrology }
val DARK_MAGIC: KnowledgeField by DEFERRED.registerObject("dark_magic") { DarkMagic }

fun registerFields(bus: IEventBus) {
    DEFERRED.register(bus)
}