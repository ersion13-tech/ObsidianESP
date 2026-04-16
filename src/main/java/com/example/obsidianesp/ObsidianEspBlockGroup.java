package com.example.obsidianesp;

import com.example.obsidianesp.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ObsidianEspBlockGroup extends ObsidianEspGroup {
	public ObsidianEspBlockGroup(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	public abstract boolean matches(BlockState state);

	public final void addIfMatches(BlockState state, BlockPos pos) {
		if (!matches(state))
			return;

		boxes.add(BlockUtils.getBoundingBox(pos));
	}
}