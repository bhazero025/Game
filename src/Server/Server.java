package Server;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 * @author bruno
 * Initialize the server
 *
 */
public class Server
{
	
	private int port;
	private ServerSocket ss;
	
	
	public static void main(String[] args)
	{
		new Server(25801).start();
	}
	
	public Server(int port)
	{
		this.port = port;
	}
	
	public void start()
	{
		try 
		{
			this.ss = new ServerSocket(this.port);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//World.getWorld().createRandomWorld();
		//DBHandler.createWorldTable();
		//DBHandler.addWorldToDatabase(World.getWorld());
		World.getWorld().loadWorld();
		
		//TerrainPlayer p = new TerrainPlayer(6, 3);
		//World.getWorld().overwriteTerrain(p);
		for (int i = 0; i < World.size; i++)
		{
			for (int j = 0; j < World.size; j++)
			{
				System.out.println(World.getWorld().getTerrain(i, j));
			}
		}
		//DBHandler.addWorldToDatabase(World.getWorld());
	}

}


