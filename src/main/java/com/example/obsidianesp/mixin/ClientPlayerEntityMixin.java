package com.example.obsidianesp.mixin;



import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;

import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



import net.minecraft.client.player.AbstractClientPlayer;

import net.minecraft.client.player.LocalPlayer;

import com.example.obsidianesp.ObsidianEspMod;



@Mixin(LocalPlayer.class)

public abstract class ClientPlayerEntityMixin extends AbstractClientPlayer

{

	private ClientPlayerEntityMixin() {

		super(null, null);

	}



	@Inject(at = @At(value = "INVOKE",

			target = "Lnet/minecraft/client/player/AbstractClientPlayer;tick()V",

			ordinal = 0), method = "tick()V")

	private void onTick(CallbackInfo ci)

	{

		ObsidianEspMod obsidianEsp = ObsidianEspMod.getInstance();

		if(obsidianEsp == null)

			return;



		obsidianEsp.onUpdate();

	}

}