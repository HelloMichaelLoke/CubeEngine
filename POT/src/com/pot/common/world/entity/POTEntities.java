package com.pot.common.world.entity;

import com.grillecube.engine.mod.Mod;
import com.grillecube.engine.renderer.model.Model;
import com.grillecube.engine.resources.IModResource;
import com.grillecube.engine.resources.ResourceManager;

public class POTEntities implements IModResource {
	
	//entities id
	public static int E_BIPED;
	public static int E_SHROOM;

	//models id
	public static Model M_BIPED;
	public static Model M_SHROOM;
	
	@Override
	public void load(Mod mod, ResourceManager manager) {
		E_BIPED = manager.getEntityManager().registerEntity(EntityBiped.class);
		E_SHROOM = manager.getEntityManager().registerEntity(EntityShroom.class);
	}

	@Override
	public void unload(Mod mod, ResourceManager manager) {
	}

}