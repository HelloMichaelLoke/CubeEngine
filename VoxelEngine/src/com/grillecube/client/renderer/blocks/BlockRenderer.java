package com.grillecube.client.renderer.blocks;

import java.util.ArrayList;

import com.grillecube.client.renderer.world.TerrainMeshTriangle;
import com.grillecube.client.renderer.world.TerrainMesher;
import com.grillecube.client.renderer.world.flat.BlockFace;
import com.grillecube.client.resources.BlockRendererManager;
import com.grillecube.common.faces.Face;
import com.grillecube.common.maths.Maths;
import com.grillecube.common.maths.Vector3i;
import com.grillecube.common.world.block.Block;
import com.grillecube.common.world.terrain.Terrain;

/** the block renderer class */
public abstract class BlockRenderer {

	/**
	 * 5------------6 /| /| / | / | 3------------2--| | | | | | | | | |
	 * 4---------|--7 | / | / |/ |/ 0------------1
	 */

	/** edges offset */
	public static Vector3i[] VERTICES = new Vector3i[8];

	/** edges for a face */
	public static int[][] FACES_VERTICES = new int[Face.faces.length][4];
	/**
	 * lists the index of the endpoint vertices for each of the 12edges of the cube
	 */
	public static final int EDGES[][] = new int[12][2];

	/**
	 * 12x3 : lists the direction vector (vertex1-vertex0) for each edge in the cube
	 */
	public static final Vector3i[] EDGES_DIRECTIONS = new Vector3i[12];

	/** blocks offset which affect ambiant occlusion */
	public static Vector3i[][][] FACES_NEIGHBORS = new Vector3i[6][4][3];

	static {
		VERTICES[0] = new Vector3i(0, 0, 0);
		VERTICES[1] = new Vector3i(1, 0, 0);
		VERTICES[2] = new Vector3i(1, 1, 0);
		VERTICES[3] = new Vector3i(0, 1, 0);
		VERTICES[4] = new Vector3i(0, 0, 1);
		VERTICES[5] = new Vector3i(1, 0, 1);
		VERTICES[6] = new Vector3i(1, 1, 1);
		VERTICES[7] = new Vector3i(0, 1, 1);

		/** edges connections */
		EDGES[0][0] = 0;
		EDGES[0][1] = 1;
		EDGES[1][0] = 1;
		EDGES[1][1] = 2;
		EDGES[2][0] = 2;
		EDGES[2][1] = 3;
		EDGES[3][0] = 3;
		EDGES[3][1] = 0;

		EDGES[4][0] = 4;
		EDGES[4][1] = 5;
		EDGES[5][0] = 5;
		EDGES[5][1] = 6;
		EDGES[6][0] = 6;
		EDGES[6][1] = 7;
		EDGES[7][0] = 7;
		EDGES[7][1] = 4;

		EDGES[8][0] = 0;
		EDGES[8][1] = 4;
		EDGES[9][0] = 1;
		EDGES[9][1] = 5;
		EDGES[10][0] = 2;
		EDGES[10][1] = 6;
		EDGES[11][0] = 3;
		EDGES[11][1] = 7;

		/** edge directions */
		for (int i = 0; i < EDGES.length; i++) {
			EDGES_DIRECTIONS[i] = Vector3i.sub(VERTICES[EDGES[i][1]], VERTICES[EDGES[i][0]], null);
		}

		/** left face */
		FACES_VERTICES[Face.LEFT][0] = 2;
		FACES_VERTICES[Face.LEFT][1] = 1;
		FACES_VERTICES[Face.LEFT][2] = 0;
		FACES_VERTICES[Face.LEFT][3] = 3;

		FACES_NEIGHBORS[Face.LEFT][0][0] = new Vector3i(0, 1, -1);
		FACES_NEIGHBORS[Face.LEFT][0][1] = new Vector3i(1, 0, -1);
		FACES_NEIGHBORS[Face.LEFT][0][2] = new Vector3i(1, 1, -1);

		FACES_NEIGHBORS[Face.LEFT][1][0] = new Vector3i(0, -1, -1);
		FACES_NEIGHBORS[Face.LEFT][1][1] = new Vector3i(1, 0, -1);
		FACES_NEIGHBORS[Face.LEFT][1][2] = new Vector3i(1, -1, -1);

		FACES_NEIGHBORS[Face.LEFT][2][0] = new Vector3i(0, -1, -1);
		FACES_NEIGHBORS[Face.LEFT][2][1] = new Vector3i(-1, 0, -1);
		FACES_NEIGHBORS[Face.LEFT][2][2] = new Vector3i(-1, -1, -1);

		FACES_NEIGHBORS[Face.LEFT][3][0] = new Vector3i(0, 1, -1);
		FACES_NEIGHBORS[Face.LEFT][3][1] = new Vector3i(-1, 0, -1);
		FACES_NEIGHBORS[Face.LEFT][3][2] = new Vector3i(-1, 1, -1);

		/** right face */
		FACES_VERTICES[Face.RIGHT][0] = 7;
		FACES_VERTICES[Face.RIGHT][1] = 4;
		FACES_VERTICES[Face.RIGHT][2] = 5;
		FACES_VERTICES[Face.RIGHT][3] = 6;

		FACES_NEIGHBORS[Face.RIGHT][0][0] = new Vector3i(0, 1, 1);
		FACES_NEIGHBORS[Face.RIGHT][0][1] = new Vector3i(-1, 0, 1);
		FACES_NEIGHBORS[Face.RIGHT][0][2] = new Vector3i(-1, 1, 1);

		FACES_NEIGHBORS[Face.RIGHT][1][0] = new Vector3i(0, -1, 1);
		FACES_NEIGHBORS[Face.RIGHT][1][1] = new Vector3i(-1, 0, 1);
		FACES_NEIGHBORS[Face.RIGHT][1][2] = new Vector3i(-1, -1, 1);

		FACES_NEIGHBORS[Face.RIGHT][2][0] = new Vector3i(0, -1, 1);
		FACES_NEIGHBORS[Face.RIGHT][2][1] = new Vector3i(1, 0, 1);
		FACES_NEIGHBORS[Face.RIGHT][2][2] = new Vector3i(1, -1, 1);

		FACES_NEIGHBORS[Face.RIGHT][3][0] = new Vector3i(0, 1, 1);
		FACES_NEIGHBORS[Face.RIGHT][3][1] = new Vector3i(1, 0, 1);
		FACES_NEIGHBORS[Face.RIGHT][3][2] = new Vector3i(1, 1, 1);

		/** back face */
		FACES_VERTICES[Face.BACK][0] = 6;
		FACES_VERTICES[Face.BACK][1] = 5;
		FACES_VERTICES[Face.BACK][2] = 1;
		FACES_VERTICES[Face.BACK][3] = 2;

		FACES_NEIGHBORS[Face.BACK][0][0] = new Vector3i(1, 1, 0);
		FACES_NEIGHBORS[Face.BACK][0][1] = new Vector3i(1, 0, 1);
		FACES_NEIGHBORS[Face.BACK][0][2] = new Vector3i(1, 1, 1);

		FACES_NEIGHBORS[Face.BACK][1][0] = new Vector3i(1, -1, 0);
		FACES_NEIGHBORS[Face.BACK][1][1] = new Vector3i(1, 0, 1);
		FACES_NEIGHBORS[Face.BACK][1][2] = new Vector3i(1, -1, 1);

		FACES_NEIGHBORS[Face.BACK][2][0] = new Vector3i(1, -1, 0);
		FACES_NEIGHBORS[Face.BACK][2][1] = new Vector3i(1, 0, -1);
		FACES_NEIGHBORS[Face.BACK][2][2] = new Vector3i(1, -1, -1);

		FACES_NEIGHBORS[Face.BACK][3][0] = new Vector3i(1, 1, 0);
		FACES_NEIGHBORS[Face.BACK][3][1] = new Vector3i(1, 0, -1);
		FACES_NEIGHBORS[Face.BACK][3][2] = new Vector3i(1, 1, -1);

		/** front face */
		FACES_VERTICES[Face.FRONT][0] = 3;
		FACES_VERTICES[Face.FRONT][1] = 0;
		FACES_VERTICES[Face.FRONT][2] = 4;
		FACES_VERTICES[Face.FRONT][3] = 7;

		FACES_NEIGHBORS[Face.FRONT][0][0] = new Vector3i(-1, 1, 0);
		FACES_NEIGHBORS[Face.FRONT][0][1] = new Vector3i(-1, 0, -1);
		FACES_NEIGHBORS[Face.FRONT][0][2] = new Vector3i(-1, 1, -1);

		FACES_NEIGHBORS[Face.FRONT][1][0] = new Vector3i(-1, -1, 0);
		FACES_NEIGHBORS[Face.FRONT][1][1] = new Vector3i(-1, 0, -1);
		FACES_NEIGHBORS[Face.FRONT][1][2] = new Vector3i(-1, -1, -1);

		FACES_NEIGHBORS[Face.FRONT][2][0] = new Vector3i(-1, -1, 0);
		FACES_NEIGHBORS[Face.FRONT][2][1] = new Vector3i(-1, 0, 1);
		FACES_NEIGHBORS[Face.FRONT][2][2] = new Vector3i(-1, -1, 1);

		FACES_NEIGHBORS[Face.FRONT][3][0] = new Vector3i(-1, 1, 0);
		FACES_NEIGHBORS[Face.FRONT][3][1] = new Vector3i(-1, 0, 1);
		FACES_NEIGHBORS[Face.FRONT][3][2] = new Vector3i(-1, 1, 1);

		/** bottom face */
		FACES_VERTICES[Face.BOT][0] = 0;
		FACES_VERTICES[Face.BOT][1] = 1;
		FACES_VERTICES[Face.BOT][2] = 5;
		FACES_VERTICES[Face.BOT][3] = 4;

		FACES_NEIGHBORS[Face.BOT][0][0] = new Vector3i(0, -1, -1);
		FACES_NEIGHBORS[Face.BOT][0][1] = new Vector3i(-1, -1, 0);
		FACES_NEIGHBORS[Face.BOT][0][2] = new Vector3i(-1, -1, -1);

		FACES_NEIGHBORS[Face.BOT][1][0] = new Vector3i(0, -1, -1);
		FACES_NEIGHBORS[Face.BOT][1][1] = new Vector3i(1, -1, 0);
		FACES_NEIGHBORS[Face.BOT][1][2] = new Vector3i(1, -1, -1);

		FACES_NEIGHBORS[Face.BOT][2][0] = new Vector3i(0, -1, 1);
		FACES_NEIGHBORS[Face.BOT][2][1] = new Vector3i(1, -1, 0);
		FACES_NEIGHBORS[Face.BOT][2][2] = new Vector3i(1, -1, 1);

		FACES_NEIGHBORS[Face.BOT][3][0] = new Vector3i(0, -1, 1);
		FACES_NEIGHBORS[Face.BOT][3][1] = new Vector3i(-1, -1, 0);
		FACES_NEIGHBORS[Face.BOT][3][2] = new Vector3i(-1, -1, 1);

		/** top face */
		FACES_VERTICES[Face.TOP][0] = 2;
		FACES_VERTICES[Face.TOP][1] = 3;
		FACES_VERTICES[Face.TOP][2] = 7;
		FACES_VERTICES[Face.TOP][3] = 6;

		FACES_NEIGHBORS[Face.TOP][0][0] = new Vector3i(0, 1, -1);
		FACES_NEIGHBORS[Face.TOP][0][1] = new Vector3i(1, 1, 0);
		FACES_NEIGHBORS[Face.TOP][0][2] = new Vector3i(1, 1, -1);

		FACES_NEIGHBORS[Face.TOP][1][0] = new Vector3i(0, 1, -1);
		FACES_NEIGHBORS[Face.TOP][1][1] = new Vector3i(-1, 1, 0);
		FACES_NEIGHBORS[Face.TOP][1][2] = new Vector3i(-1, 1, -1);

		FACES_NEIGHBORS[Face.TOP][2][0] = new Vector3i(0, 1, 1);
		FACES_NEIGHBORS[Face.TOP][2][1] = new Vector3i(-1, 1, 0);
		FACES_NEIGHBORS[Face.TOP][2][2] = new Vector3i(-1, 1, 1);

		FACES_NEIGHBORS[Face.TOP][3][0] = new Vector3i(0, 1, 1);
		FACES_NEIGHBORS[Face.TOP][3][1] = new Vector3i(1, 1, 0);
		FACES_NEIGHBORS[Face.TOP][3][2] = new Vector3i(1, 1, 1);

	};

	public static float[][] FACES_UV = { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 0 } };

	/**
	 * generate the vertices for the given block, in the given terrain and (x, y, z)
	 * terrain-relative coordinates.
	 *
	 * If this block has faces (1x1), it should be set: faces[faceID][x][y][z] =
	 * blockFace, so it can be cull if needed
	 * 
	 * If the block has a special rendering, add the vertices directly onto the
	 * stack
	 */
	public abstract void generateBlockVertices(TerrainMesher terrainMesher, Terrain terrain, Block block, int x, int y,
			int z, BlockFace[][][][] faces, ArrayList<TerrainMeshTriangle> stack);

	/** return true if this block has transparency */
	// public abstract boolean hasTransparency(); //TODO : implement it here,
	// instead of #Terrain#opaqueBlockCount and stuff...

	/** return the x texture coodinates for this textureID */
	public int getAtlasX(int textureID) {
		return (textureID % BlockRendererManager.TEXTURE_PER_LINE);
	}

	/** return the y texture coodinates for this textureID */
	public int getAtlasY(int textureID) {
		return (textureID / BlockRendererManager.TEXTURE_PER_LINE);
	}

	/** get block light by getting the average of neighboors blocks */
	public float getBlockLight(Terrain terrain, int x, int y, int z, Vector3i... neighboors) {
		float blockLight = 0.0f;
		for (Vector3i n : neighboors) {
			blockLight += terrain.getBlockLight(x + n.x, y + n.y, z + n.z);
		}
		return (blockLight / (neighboors.length * 16.0f));
	}

	/** get block light by getting the average of neighboors blocks */
	public static final float getSunLight(Terrain terrain, int x, int y, int z, Vector3i... neighboors) {
		float sunLight = 0.0f;
		for (Vector3i n : neighboors) {
			sunLight += terrain.getSunLight(x + n.x, y + Maths.abs(n.y), z + n.z);
		}
		return (sunLight / (neighboors.length * 16.0f));
	}

}
