package com.hkmod.soluna.client.renderer

import com.hkmod.soluna.common.blocks.AnalysisTable
import com.hkmod.soluna.common.blocks.entity.AnalysisTableEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.Minecraft
import net.minecraft.client.model.BookModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.ItemTransforms
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class AnalysisTableRenderer(context: BlockEntityRendererProvider.Context): BlockEntityRenderer<AnalysisTableEntity> {
    private val bookModel: BookModel = BookModel(context.bakeLayer(ModelLayers.BOOK))
    private val itemRenderer: ItemRenderer by lazy { Minecraft.getInstance().itemRenderer }
    private val feather = ItemStack(Items.FEATHER)
    override fun render(
        blockEntity: AnalysisTableEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val blockState = blockEntity.blockState
        if (blockState.getValue(AnalysisTable.HAS_BOOK)) {
            poseStack.pushPose()
            poseStack.translate(0.5, 1.1, 0.5)
            val yDegree = blockState.getValue(AnalysisTable.FACING).clockWise.toYRot()
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-yDegree))
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(67.5f))
            poseStack.translate(0.0, -0.125, 0.0)
            bookModel.setupAnim(0.0F, 0.0F, 0.9F, 1.2F)
            val vertexConsumer = EnchantTableRenderer.BOOK_LOCATION.buffer(bufferSource) { location: ResourceLocation ->
                RenderType.entitySolid(location)
            }

            val foilConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, RenderType.glintTranslucent(), false, true)

            bookModel.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f)

            bookModel.render(poseStack, foilConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f)
            poseStack.popPose()

            poseStack.pushPose()
            when(blockState.getValue(AnalysisTable.FACING)) {
                Direction.NORTH -> {}
                Direction.SOUTH -> {
                    poseStack.translate(1.0, 0.0, 1.0)
                }
                Direction.WEST -> {
                    poseStack.translate(0.0, 0.0, 1.0)
                }
                Direction.EAST -> {
                    poseStack.translate(1.0, 0.0, 0.0)
                }
                else -> {}
            }
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-yDegree))
            poseStack.translate(-0.2, 1.08, 0.3)
            poseStack.mulPose(Vector3f.YP.rotationDegrees(90F))
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-67.5F))
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(45F))
            poseStack.scale(1.2F, 1.2F, 1.2F)
            itemRenderer.renderStatic(feather, ItemTransforms.TransformType.GROUND, packedLight, packedOverlay, poseStack, bufferSource, 0)
            poseStack.popPose()
        }
    }

}