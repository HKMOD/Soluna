package com.hkmod.soluna.common.knowledge.fields

import com.hkmod.soluna.common.knowledge.FIELDS
import net.minecraft.resources.ResourceLocation

abstract class KnowledgeField {
    open val registryKey: ResourceLocation by lazy { FIELDS.getKey(this)!! }
    open val nameKey: String by lazy { "knowledgeField.${registryKey.namespace}.${registryKey.path}.name" }
    open val descriptionKey: String by lazy { "knowledgeField.${registryKey.namespace}.${registryKey.path}.description" }
    open val iconTexture: ResourceLocation get() = registryKey
}