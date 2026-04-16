package com.example.obsidianesp;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.OptionalDouble;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public enum ObsidianEspRenderLayers
{
	;

	public static final RenderType.CompositeRenderType LINES = RenderType
			.create("obsidianesp:lines", DefaultVertexFormat.POSITION_COLOR_NORMAL,
					VertexFormat.Mode.LINES, 1536, false, true,
					RenderType.CompositeState.builder()
							.setShaderState(RenderType.RENDERTYPE_LINES_SHADER)
							.setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(2)))
							.setLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING)
							.setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
							.setOutputState(RenderType.ITEM_ENTITY_TARGET)
							.setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
							.setDepthTestState(RenderType.LEQUAL_DEPTH_TEST)
							.setCullState(RenderType.NO_CULL).createCompositeState(false));

	public static final RenderType.CompositeRenderType ESP_LINES = RenderType
			.create("obsidianesp:esp_lines", DefaultVertexFormat.POSITION_COLOR_NORMAL,
					VertexFormat.Mode.LINES, 1536, false, true,
					RenderType.CompositeState.builder()
							.setShaderState(RenderType.RENDERTYPE_LINES_SHADER)
							.setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(2)))
							.setLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING)
							.setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
							.setOutputState(RenderType.ITEM_ENTITY_TARGET)
							.setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
							.setDepthTestState(RenderType.NO_DEPTH_TEST)
							.setCullState(RenderType.NO_CULL).createCompositeState(false));

	public static final RenderType.CompositeRenderType QUADS =
			RenderType.create("obsidianesp:quads", DefaultVertexFormat.POSITION_COLOR,
					VertexFormat.Mode.QUADS, 1536, false, true,
					RenderType.CompositeState.builder()
							.setShaderState(RenderType.POSITION_COLOR_SHADER)
							.setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
							.setDepthTestState(RenderType.LEQUAL_DEPTH_TEST)
							.createCompositeState(false));

	public static final RenderType.CompositeRenderType ESP_QUADS = RenderType
			.create("obsidianesp:esp_quads", DefaultVertexFormat.POSITION_COLOR,
					VertexFormat.Mode.QUADS, 1536, false, true,
					RenderType.CompositeState.builder()
							.setShaderState(RenderType.POSITION_COLOR_SHADER)
							.setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
							.setDepthTestState(RenderType.NO_DEPTH_TEST)
							.createCompositeState(false));

	public static RenderType getQuads(boolean depthTest)
	{
		return depthTest ? QUADS : ESP_QUADS;
	}

	public static RenderType getLines(boolean depthTest)
	{
		return depthTest ? LINES : ESP_LINES;
	}
}