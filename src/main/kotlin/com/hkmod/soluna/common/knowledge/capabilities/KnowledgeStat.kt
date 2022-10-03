package com.hkmod.soluna.common.knowledge.capabilities

import com.hkmod.soluna.common.knowledge.fields.KnowledgeField

class KnowledgeStat: IKnowledgeStat {
    override val knowledgeStats = HashMap<KnowledgeField, Double>()
}