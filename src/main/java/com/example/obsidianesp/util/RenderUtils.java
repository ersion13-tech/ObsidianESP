package com.example.obsidianesp.util;

import java.util.List;
import org.joml.Vector3f;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.example.obsidianesp.ObsidianEspRenderLayers;

public enum RenderUtils
{
	;

	private static final Minecraft MC = Minecraft.getInstance();

	public static Vec3 getCameraPos()
	{
		Camera camera = MC.gameRenderer.getMainCamera();
		return (camera == null) ? Vec3.ZERO : camera.getPosition();
	}

	public static MultiBufferSource.BufferSource getVCP()
	{
		return MC.renderBuffers().bufferSource();
	}

	private static Vec3 getTracerOrigin()
	{
		if (MC.player == null) return Vec3.ZERO;
		Vec3 start = MC.player.getViewVector(1.0F).scale(0.5);
		if(MC.options.getCameraType() == CameraType.THIRD_PERSON_FRONT)
			start = start.reverse();

		return start;
	}

	public static void drawTracers(PoseStack matrices, float partialTicks,
								   List<Vec3> ends, int color, boolean depthTest)
	{
		int depthFunc = depthTest ? GlConst.GL_LEQUAL : GlConst.GL_ALWAYS;
		RenderSystem.enableDepthTest();
		RenderSystem.depthFunc(depthFunc);

		MultiBufferSource.BufferSource vcp = getVCP();
		RenderType layer = ObsidianEspRenderLayers.getLines(depthTest);
		VertexConsumer buffer = vcp.getBuffer(layer);

		Vec3 start = getTracerOrigin();
		Vec3 offset = getCameraPos().reverse();
		for(Vec3 end : ends)
			drawTraceLine(matrices, buffer, start, end.add(offset), color);

		vcp.endBatch(layer);
	}

	// Вспомогательный метод для трейсеров (линии от глаз)
	private static void drawTraceLine(PoseStack matrices, VertexConsumer buffer,
									  Vec3 start, Vec3 end, int color)
	{
		Pose entry = matrices.last();
		drawLine(entry, buffer, (float)start.x, (float)start.y, (float)start.z,
				(float)end.x, (float)end.y, (float)end.z, color);
	}

	public static void drawSolidBoxes(PoseStack matrices, List<AABB> boxes,
									  int color, boolean depthTest)
	{
		int depthFunc = depthTest ? GlConst.GL_LEQUAL : GlConst.GL_ALWAYS;
		RenderSystem.enableDepthTest();
		RenderSystem.depthFunc(depthFunc);

		MultiBufferSource.BufferSource vcp = getVCP();
		RenderType layer = ObsidianEspRenderLayers.getQuads(depthTest);
		VertexConsumer buffer = vcp.getBuffer(layer);

		Vec3 camOffset = getCameraPos().reverse();
		for(AABB box : boxes)
			drawSolidBox(matrices, buffer, box.move(camOffset), color);

		vcp.endBatch(layer);
	}

	public static void drawSolidBox(PoseStack matrices, VertexConsumer buffer,
									AABB box, int color)
	{
		PoseStack.Pose entry = matrices.last();
		float x1 = (float)box.minX; float y1 = (float)box.minY; float z1 = (float)box.minZ;
		float x2 = (float)box.maxX; float y2 = (float)box.maxY; float z2 = (float)box.maxZ;

		addQuad(entry, buffer, x1, y1, z1, x2, y1, z1, x2, y1, z2, x1, y1, z2, color);
		addQuad(entry, buffer, x1, y2, z1, x1, y2, z2, x2, y2, z2, x2, y2, z1, color);
		addQuad(entry, buffer, x1, y1, z1, x1, y2, z1, x2, y2, z1, x2, y1, z1, color);
		addQuad(entry, buffer, x2, y1, z1, x2, y2, z1, x2, y2, z2, x2, y1, z2, color);
		addQuad(entry, buffer, x1, y1, z2, x2, y1, z2, x2, y2, z2, x1, y2, z2, color);
		addQuad(entry, buffer, x1, y1, z1, x1, y1, z2, x1, y2, z2, x1, y2, z1, color);
	}

	private static void addQuad(PoseStack.Pose entry, VertexConsumer buffer,
								float x1, float y1, float z1, float x2, float y2, float z2,
								float x3, float y3, float z3, float x4, float y4, float z4, int color) {
		buffer.addVertex(entry, x1, y1, z1).setColor(color);
		buffer.addVertex(entry, x2, y2, z2).setColor(color);
		buffer.addVertex(entry, x3, y3, z3).setColor(color);
		buffer.addVertex(entry, x4, y4, z4).setColor(color);
	}

	public static void drawOutlinedBoxes(PoseStack matrices, List<AABB> boxes,
										 int color, boolean depthTest)
	{
		int depthFunc = depthTest ? GlConst.GL_LEQUAL : GlConst.GL_ALWAYS;
		RenderSystem.enableDepthTest();
		RenderSystem.depthFunc(depthFunc);

		MultiBufferSource.BufferSource vcp = getVCP();
		RenderType layer = ObsidianEspRenderLayers.getLines(depthTest);
		VertexConsumer buffer = vcp.getBuffer(layer);

		Vec3 camOffset = getCameraPos().reverse();
		for(AABB box : boxes)
			drawOutlinedBox(matrices, buffer, box.move(camOffset), color);

		vcp.endBatch(layer);
	}

	public static void drawOutlinedBox(PoseStack matrices,
									   VertexConsumer buffer, AABB box, int color)
	{
		PoseStack.Pose entry = matrices.last();
		float x1 = (float)box.minX; float y1 = (float)box.minY; float z1 = (float)box.minZ;
		float x2 = (float)box.maxX; float y2 = (float)box.maxY; float z2 = (float)box.maxZ;

		drawLine(entry, buffer, x1, y1, z1, x2, y1, z1, color);
		drawLine(entry, buffer, x1, y1, z1, x1, y1, z2, color);
		drawLine(entry, buffer, x2, y1, z1, x2, y1, z2, color);
		drawLine(entry, buffer, x1, y1, z2, x2, y1, z2, color);

		drawLine(entry, buffer, x1, y2, z1, x2, y2, z1, color);
		drawLine(entry, buffer, x1, y2, z1, x1, y2, z2, color);
		drawLine(entry, buffer, x2, y2, z1, x2, y2, z2, color);
		drawLine(entry, buffer, x1, y2, z2, x2, y2, z2, color);

		drawLine(entry, buffer, x1, y1, z1, x1, y2, z1, color);
		drawLine(entry, buffer, x2, y1, z1, x2, y2, z1, color);
		drawLine(entry, buffer, x1, y1, z2, x1, y2, z2, color);
		drawLine(entry, buffer, x2, y1, z2, x2, y2, z2, color);
	}

	// Единственный и верный метод отрисовки линии
	public static void drawLine(PoseStack.Pose entry, VertexConsumer buffer,
								float x1, float y1, float z1, float x2, float y2, float z2, int color) {
		Vector3f normal = new Vector3f(x2 - x1, y2 - y1, z2 - z1).normalize();
		buffer.addVertex(entry, x1, y1, z1).setColor(color).setNormal(entry, normal.x, normal.y, normal.z);
		buffer.addVertex(entry, x2, y2, z2).setColor(color).setNormal(entry, normal.x, normal.y, normal.z);
	}
}