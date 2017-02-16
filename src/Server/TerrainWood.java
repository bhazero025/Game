package Server;

public class TerrainWood extends Terrain
{

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
