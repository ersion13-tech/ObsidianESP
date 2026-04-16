package com.example.obsidianesp.util;

import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public enum ChunkUtils
{
	;

	private static final Minecraft MC = Minecraft.getInstance();

	public static Stream<BlockEntity> getLoadedBlockEntities()
	{
		return getLoadedChunks()
				.flatMap(chunk -> chunk.getBlockEntities().values().stream());
	}

	public static Stream<LevelChunk> getLoadedChunks()
	{
		int radius = Math.max(2, MC.options.getEffectiveRenderDistance()) + 1;
		int diameter = radius * 2 + 1;

		ChunkPos center = MC.player.chunkPosition();
		ChunkPos min = new ChunkPos(center.x - radius, center.z - radius);
		ChunkPos max = new ChunkPos(center.x + radius, center.z + radius);

		return Stream.<ChunkPos> iterate(min, pos -> {
					int x = pos.x;
					int z = pos.z;

					x++;

					if(x > max.x)
					{
						x = min.x;
						z++;
					}

					return new ChunkPos(x, z);

				}).limit((long) diameter * diameter)
				.filter(c -> MC.level.hasChunk(c.x, c.z))
				.map(c -> MC.level.getChunk(c.x, c.z))
				.filter(Objects::nonNull);
	}
}