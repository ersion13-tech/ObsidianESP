package com.example.obsidianesp;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "obsidianesp")
public class ObsidianConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 10, max = 100)
    public int scanRange = 32;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int updateInterval = 20;

    @ConfigEntry.Gui.RequiresRestart(value = false)
    public boolean enabled = true;
}