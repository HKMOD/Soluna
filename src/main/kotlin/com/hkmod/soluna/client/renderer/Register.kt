package com.hkmod.soluna.client.renderer

import com.hkmod.soluna.common.blocks.entity.SolunaBlockEntities.ANALYSIS_TABLE_ENTITY
import net.minecraftforge.client.event.EntityRenderersEvent

fun registerRenderers(event: EntityRenderersEvent.RegisterRenderers) {
    event.registerBlockEntityRenderer(ANALYSIS_TABLE_ENTITY) {
        AnalysisTableRenderer(it)
    }
}