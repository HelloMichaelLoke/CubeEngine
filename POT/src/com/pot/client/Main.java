package com.pot.client;

import com.grillecube.common.ModSample;
import com.grillecube.engine.VoxelEngineClient;
import com.grillecube.engine.renderer.camera.CameraPerspectiveWorldFree;
import com.grillecube.engine.renderer.gui.components.GuiViewIngame;
import com.pot.client.renderer.gui.GuiViewDebug;
import com.pot.common.ModCommon;
import com.pot.common.world.POTWorlds;

public class Main {

	public static void main(String[] args) {

		/* 1 */
		// initialize engine
		VoxelEngineClient engine = new VoxelEngineClient();

		/* 2 */
		// inject resources to be loaded
		engine.injectInternalMod(new ModCommon());
		engine.injectInternalMod(new ModSample());

		// load resources (mods)
		engine.load();

		/* prepare engine before looping */
		prepareEngine(engine);

		/* 3 */
		// loop, every allocated memory will be released properly on program
		// termination */
		try {
			engine.loop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		engine.stopAll();
	}

	private static void prepareEngine(VoxelEngineClient engine) {
		// engine.getGLFWWindow().setCursor(false);

		// int s = 2;
		// for (int i = 0; i < s; i++) {
		// for (int j = 0; j < s; j++) {
		// EntityModeled entity = new EntityShroom(Worlds.DEFAULT);
		// Worlds.DEFAULT.spawnEntity(entity, i, 100, j);
		// entity.toggleSkin(i % 2);
		// }
		// }

		// EntityModeled entity = new EntityShroom(POTWorlds.DEFAULT);
		// POTWorlds.DEFAULT.getEntityStorage().add(entity, 64, 256, 64);
		// CameraPerspectiveWorldEntity cam = new
		// CameraPerspectiveWorldEntity(engine.getGLFWWindow());
		// cam.setEntity(entity);
		// engine.getRenderer().setCamera(cam);

		engine.getRenderer().setCamera(new CameraPerspectiveWorldFree(engine.getGLFWWindow()));
		engine.setWorld(POTWorlds.DEFAULT);
		engine.getGLFWWindow().swapInterval(1);
		engine.getGLFWWindow().setScreenPosition(100, 100);
		engine.getRenderer().getGuiRenderer().addView(new GuiViewDebug());
		engine.getRenderer().getGuiRenderer().addView(new GuiViewIngame());

	}
}