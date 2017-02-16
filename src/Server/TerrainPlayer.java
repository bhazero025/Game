package Server;


public class TerrainPlayer extends Terrain
{
	
	public TerrainPlayer(int id, int ownerID)
	{
		super(id);
		this.setType(TerrainData.TerrainType.PLAYER);
		this.setTOwner(ownerID);
	}
	
	
	public void overwriteTerrain()
	{
		//Need to find terrain that user picked
		for (int i = 0; i < World.size; i++)
		{
			for (int j = 0; j < World.size; j++)
			{
				Terrain buff;
				buff = World.getWorld().getTerrain(i, j);
				
				if (buff.getId() == this.getId())
				{
					World.getWorld().overwriteTerrain(this);
				}
			}
		}
	}


	@Override
	public String toString() {
		return "TerrainPlayer [getId()=" + getId() + ", getResource()=" + getResource() + ", getType()=" + getType()
				+ ", getTOwner()=" + getTOwner() + "]";
	}
		
}
