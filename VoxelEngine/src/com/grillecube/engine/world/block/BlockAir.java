/**
**	This file is part of the project https://github.com/toss-dev/VoxelEngine
**
**	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
**
**	PEREIRA Romain
**                                       4-----7          
**                                      /|    /|
**                                     0-----3 |
**                                     | 5___|_6
**                                     |/    | /
**                                     1-----2
*/

package com.grillecube.engine.world.block;

import java.util.Stack;

import com.grillecube.engine.renderer.world.terrain.MeshVertex;
import com.grillecube.engine.renderer.world.terrain.TerrainMesher;
import com.grillecube.engine.world.Terrain;

public class BlockAir extends Block {
	public BlockAir() {
		super((short) Blocks.AIR_ID, 0);
	}

	@Override
	public String getName() {
		return ("air");
	}

	@Override
	public boolean isVisible() {
		return (false);
	}

	@Override
	public boolean isOpaque() {
		return (false);
	}

	@Override
	public boolean influenceAO() {
		return (false);
	}

	@Override
	public void update(Terrain terrain, int x, int y, int z) {
	}

	@Override
	public void onSet(Terrain terrain, int x, int y, int z) {
	}

	@Override
	public void onUnset(Terrain terrain, int x, int y, int z) {
	}

	public boolean influenceCollisions() {
		return (false);
	}
	
	public void pushVertices(TerrainMesher mesher, Terrain terrain, Stack<MeshVertex> stack, int x, int y, int z) {
	}
	
	/** this method should be overriden for every block having a special rendering (liquid, chair...) */
	public boolean hasSpecialRendering() {
		return (false);
	}
}