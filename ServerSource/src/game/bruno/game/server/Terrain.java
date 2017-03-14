package game.bruno.game.server;

import java.io.Serializable;

/**
 * 
 * @author bruno
 * Basic terrain
 */
public class Terrain implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private TerrainData.Resource res;
	private TerrainData.TerrainType type;
	private int towner = 0;
	
	
	public Terrain(int id)
	{
		this.id = id;
		res = TerrainData.Resource.NOTHING;
		type = TerrainData.TerrainType.NOTHING;
	}
	
	public Terrain(int id, TerrainData.Resource res)
	{
		this.id = id;
		this.res = res;
		type = TerrainData.TerrainType.NOTHING;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public TerrainData.Resource getResource()
	{
		return res;
	}

	public TerrainData.TerrainType getType() 
	{
		return type;
	}

	public void setType(TerrainData.TerrainType type) 
	{
		this.type = type;
	}

	public int getTOwner() 
	{
		return towner;
	}

	public void setTOwner(int towner)
	{
		this.towner = towner;
	}

	@Override
	public String toString() 
	{
		return "Terrain [id=" + id + ", res=" + res + ", type=" + type + ", towner=" + towner + "]";
	}
	
	
	
}
