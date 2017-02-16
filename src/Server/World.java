package Server;
import java.util.Random;

/**
 * 
 * @author bruno
 * 
 * A world
 *
 */
public class World
{

	final static int size = 10;
	Terrain[][] terrain = new Terrain[size][size];
	
	private static World world = null;
	
	public static World getWorld()
	{
		if (world == null)
		{
			world = new World();
		}
		
		return world;
	}
	
	private World()
	{
		
	}
	
	public void createRandomWorld()
	{
		int id = 0;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++, id++)
			{
				Random rnd = new Random();
				int randomVal = rnd.nextInt(TerrainData.Resource.values().length);
				
				switch(randomVal)
				{
				case 0:
					terrain[i][j] = new Terrain(id);
					break;
				case 1:
					terrain[i][j] = new TerrainWater(id);
					break;
				case 2:
					terrain[i][j] = new TerrainWood(id);
					break;
				}
			}
		}
	}
	
	public void loadWorld()
	{
		terrain = DBHandler.getTerrainFromDatabase();
		//System.out.println(terrain);
	}
	
	public Terrain getTerrain(int i, int j)
	{
		if (i < size && j < size)
		{
			return terrain[i][j];
		}
		
		return null;
	}
	
	public Terrain getTerrainById(int id)
	{
		for (int i = 0; i < size; i ++)
		{
			for (int j = 0; j < size; j++)
			{
				if (id == this.terrain[i][j].getId())
				{
					return this.terrain[i][j];
				}
			}
		}
		return null;
	}
	
	/**
	 * Overwrites terrain, by checking for id
	 * @param t
	 */
	public void overwriteTerrain(Terrain t)
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (this.terrain[i][j].getId() == t.getId())
				{
					this.terrain[i][j] = t;
					DBHandler.overwriteTerrain(t);
				}
			}
		}
		
		//System.out.println("COULD NOT OVERWRITE TERRAIN " + t);
	}
	
}
