package com.example.obsidianesp;

public enum ObsidianEspStyle
{
	BOXES(true, false),
	LINES(false, true),
	LINES_AND_BOXES(true, true);

	private final boolean boxes;
	private final boolean lines;

	private ObsidianEspStyle(boolean boxes, boolean lines)
	{
		this.boxes = boxes;
		this.lines = lines;
	}

	public boolean hasBoxes()
	{
		return boxes;
	}

	public boolean hasLines()
	{
		return lines;
	}
}