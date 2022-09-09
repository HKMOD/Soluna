package com.hkmod.soluna.client.renderer

import com.hkmod.soluna.common.blocks.entity.SolunaBlockEntities.ANALYSIS_TABLE_ENTITY
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraftforge.client.event.EntityRenderersEvent

fun registerRenderers(event: EntityRenderersEvent.RegisterRenderers) {
    event.registerBlockEntityRenderer(ANALYSIS_TABLE_ENTITY) {
        BlockEntityRenderer { pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay ->
            renderAnalysisTable(pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay) }
    }
}