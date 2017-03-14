package game.bruno.game.server;

import java.io.Serializable;

public class TerrainData implements Serializable
{
	private static final long serialVersionUID = 1L;
	//Type of resource in the land
	public static enum Resource{NOTHING, WOOD, WATER};
	public static enum TerrainType{NOTHING, PLAYER};
	
}
