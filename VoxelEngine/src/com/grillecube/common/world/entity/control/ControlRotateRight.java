package com.grillecube.common.world.entity.control;

import com.grillecube.common.maths.Vector3f;
import com.grillecube.common.world.entity.Entity;

public class ControlRotateRight extends Control<Entity> {
	@Override
	public void run(Entity entity, Vector3f resultant) {
		entity.rotate(0, 2.0f, 0);
	}
}