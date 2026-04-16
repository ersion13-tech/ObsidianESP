package com.example.obsidianesp.groups;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import com.example.obsidianesp.ObsidianEspBlockGroup;

public class ObsidianGroup extends ObsidianEspBlockGroup {

	public ObsidianGroup() {
		// Цвет: Фиолетовый (Alpha 255, Red 255, Green 0, Blue 255)
		super(255, 0, 255, 255);
	}

	@Override
	public boolean matches(BlockState state) {
		return state.is(Blocks.OBSIDIAN)
				|| state.is(Blocks.CRYING_OBSIDIAN)
				|| state.is(Blocks.REINFORCED_DEEPSLATE);
	}
}