package com.example.obsidianesp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.phys.AABB;

public abstract class ObsidianEspGroup
{
	private final int r, g, b, a;
	protected final ArrayList<AABB> boxes = new ArrayList<>();

	public ObsidianEspGroup(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void clear()
	{
		boxes.clear();
	}

	public final int getColorI(int alphaOverride)
	{
		int alpha = (alphaOverride > 0) ? alphaOverride : this.a;
		return (alpha & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
	}

	public final List<AABB> getBoxes()
	{
		return Collections.unmodifiableList(boxes);
	}

	public final boolean isEnabled()
	{
		return true;
	}
}