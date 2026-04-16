package com.example.obsidianesp;

import net.fabricmc.api.ClientModInitializer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class ObsidianEspModInitializer implements ClientModInitializer {
	private static ObsidianEspMod instance;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(ObsidianConfig.class, GsonConfigSerializer::new);

		instance = new ObsidianEspMod();
	}

	public static ObsidianEspMod getInstance() {
		return instance;
	}
}