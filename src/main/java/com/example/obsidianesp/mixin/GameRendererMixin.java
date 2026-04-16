package com.example.obsidianesp.mixin;

import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import com.example.obsidianesp.ObsidianEspMod;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements AutoCloseable
{
	@WrapOperation(at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V",
			ordinal = 0),
			method = "renderLevel(Lnet/minecraft/client/DeltaTracker;)V")
	private void onBobView(GameRenderer instance, PoseStack matrices,
						   float tickDelta, Operation<Void> original)
	{
		ObsidianEspMod obsidianEsp = ObsidianEspMod.getInstance();

		if(obsidianEsp != null && obsidianEsp.isEnabled()) {
		} else {
			original.call(instance, matrices, tickDelta);
		}
	}

	@Inject(
			at = @At(value = "FIELD",
					target = "Lnet/minecraft/client/renderer/GameRenderer;renderHand:Z",
					opcode = Opcodes.GETFIELD,
					ordinal = 0),
			method = "renderLevel(Lnet/minecraft/client/DeltaTracker;)V")
	private void onRenderWorld(DeltaTracker tickCounter,
							   CallbackInfo ci, @Local(ordinal = 2) Matrix4f modelViewMatrix,
							   @Local(ordinal = 1) float tickDelta)
	{
		PoseStack matrixStack = new PoseStack();
		matrixStack.mulPose(modelViewMatrix);

		ObsidianEspMod obsidianEsp = ObsidianEspMod.getInstance();

		if(obsidianEsp != null && obsidianEsp.isEnabled()) {
			obsidianEsp.onRender(matrixStack, tickDelta);
		}
	}
}