package Server;

public class TerrainWater extends Terrain 
{
	private int numberFish;
	
	public TerrainWater(int id) 
	{
		super(id, TerrainData.Resource.WATER);
		numberFish = 50;
	}
	
	public TerrainWater(int id, int numberFish)
	{
		super(id, TerrainData.Resource.WATER);
		this.numberFish = numberFish;
	}
	
	//Test function
	public void collectFish()
	{
		System.out.println("Collecting fish on id " + this.getId() + " fish left: " + this.numberFish);
		this.numberFish--;
	}
	
	public int getNumberFish()
	{
		return this.numberFish;
	}

	@Override
	public String toString() {
		return "TerrainWater [numberFish=" + numberFish + ", getNumberFish()=" + getNumberFish() + ", getId()="
				+ getId() + ", getResource()=" + getResource() + ", getType()=" + getType() + "]";
	}
	
}
