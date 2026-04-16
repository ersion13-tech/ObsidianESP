package com.example.obsidianesp;

import java.util.List;
import com.example.obsidianesp.groups.ObsidianGroup;

public final class ObsidianEspGroupManager
{
	public final ObsidianGroup obsidian;

	public final List<ObsidianEspBlockGroup> blockGroups;
	public final List<ObsidianEspGroup> allGroups;

	public ObsidianEspGroupManager()
	{
		obsidian = new ObsidianGroup();

		blockGroups = List.<ObsidianEspBlockGroup>of(obsidian);

		allGroups = List.<ObsidianEspGroup>of(obsidian);
	}
}