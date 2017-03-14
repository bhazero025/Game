package game.bruno.game.server;

import java.io.Serializable;

public class TerrainWood extends Terrain implements Serializable
{

	private static final long serialVersionUID = 1L;
	public TerrainWood(int id) 
	{
		super(id, TerrainData.Resource.WOOD);
	}
	
	public void collectWood()
	{
		System.out.println("Collecting wood from " + this.getId());
	}

	@Override
	public String toString() {
		return "TerrainWood [getId()=" + getId() + ", getResource()=" + getResource() + ", getType()=" + getType()
				+ "]";
	}

}
