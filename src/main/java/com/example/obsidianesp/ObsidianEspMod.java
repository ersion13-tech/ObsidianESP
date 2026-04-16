package com.example.obsidianesp;

import com.example.obsidianesp.util.RenderUtils;
import com.example.obsidianesp.ObsidianEspBlockGroup;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import me.shedaniel.autoconfig.AutoConfig;

public final class ObsidianEspMod
{
	private static final Minecraft MC = Minecraft.getInstance();
	public static final Logger LOGGER = LoggerFactory.getLogger("ObsidianESP");

	private final ObsidianEspGroupManager groups;
	private final KeyMapping toggleKey;
	private boolean enabled = true;

	private int ticksElapsed = 0;

	public ObsidianEspMod()
	{
		LOGGER.info("Starting ObsidianESP with Config support...");

		groups = new ObsidianEspGroupManager();

		toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.obsidianesp.toggle",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_O,
				"ObsidianESP"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(toggleKey.consumeClick()) {
				setEnabled(!enabled);
				if (client.player != null) {
					client.player.displayClientMessage(
							Component.literal("ObsidianESP: " + (enabled ? "§aON" : "§cOFF")),
							true
					);
				}
			}
		});
	}

	public void setEnabled(boolean enabled)
	{
		if(this.enabled == enabled) return;
		this.enabled = enabled;

		if(!enabled)
			groups.blockGroups.forEach(ObsidianEspGroup::clear);
	}

	public void onUpdate()
	{
		if(!enabled) return;

		ObsidianConfig config = AutoConfig.getConfigHolder(ObsidianConfig.class).getConfig();

		ticksElapsed++;
		if (ticksElapsed < config.updateInterval) {
			return;
		}
		ticksElapsed = 0;

		if (MC.level == null || MC.player == null) return;

		groups.blockGroups.forEach(ObsidianEspGroup::clear);

		BlockPos playerPos = MC.player.blockPosition();
		int range = config.scanRange;

		for (int x = -range; x <= range; x++) {
			for (int y = -range; y <= range; y++) {
				for (int z = -range; z <= range; z++) {
					BlockPos pos = playerPos.offset(x, y, z);

					for (ObsidianEspBlockGroup group : groups.blockGroups) {
						group.addIfMatches(MC.level.getBlockState(pos), pos);
					}
				}
			}
		}
	}

	public void onRender(PoseStack matrixStack, float partialTicks)
	{
		if(!enabled) return;
		renderBoxes(matrixStack);
	}

	private void renderBoxes(PoseStack matrixStack)
	{
		for(ObsidianEspBlockGroup group : groups.blockGroups)
		{
			List<AABB> boxes = group.getBoxes();
			if (boxes.isEmpty()) continue;

			int quadsColor = group.getColorI(0x40);
			int linesColor = group.getColorI(0x80);

			RenderUtils.drawSolidBoxes(matrixStack, boxes, quadsColor, false);
			RenderUtils.drawOutlinedBoxes(matrixStack, boxes, linesColor, false);
		}
	}

	public boolean isEnabled() { return enabled; }

	public static ObsidianEspMod getInstance() {
		return ObsidianEspModInitializer.getInstance();
	}

	public ObsidianEspGroupManager getGroups() {
		return groups;
	}
}