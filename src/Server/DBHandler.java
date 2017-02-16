package Server;
import java.sql.*;

public class DBHandler 
{

	public static void main(String [] args)
	{
		//new DBHandler().createWorldTable();
	}
	
	
	public static void createWorldTable()
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:World.db");
	  

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE Terrain " +
	                   "(ID INT PRIMARY KEY     NOT NULL, " +
	                   " RESOURCE       INT     NOT NULL, " + 
	                   " RLEFT          INT     NOT NULL, " +
	                   " TType          INT     NOT NULL, " +
	                   " TOWNER         INT     );";
	      
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	private static Terrain TerrainBuilder(int id, int resource, int rleft, int ttype, int towner)
	{
		Terrain buffer = null;

		if (ttype == 1)
		{
			buffer = new TerrainPlayer(id, towner);
			return buffer;
		}
		
		switch (resource)
		{
		case 0:
			buffer = new Terrain(id);
			break;
		case 1:
			buffer = new TerrainWood(id);
			break;
		case 2:
			buffer = new TerrainWater(id, rleft);
		}
		
		return buffer;
	}
	
	public static Terrain[][] getTerrainFromDatabase()
	{
		Terrain[][] buff = new Terrain[World.size][World.size];
		Connection c = null;
	    Statement stmt = null;
	    try
	    {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:World.db");
	      c.setAutoCommit(false);


	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM TERRAIN;" );
	      //Thorw 
	      rs.next();
	      for (int i = 0; i < World.size; i++)
	      {
	    	  for (int j = 0; j < World.size; j++)
	    	  {
	    		 int id = rs.getInt("ID");
	 	         int resource = rs.getInt("RESOURCE");
	 	         int rleft = rs.getInt("RLEFT");
	 	         int ttype = rs.getInt("TType");
	 	         int towner = rs.getInt("TOWNER");
	 	       
	 	        /* switch(resource)
	 	         {
	 	         case 0:
	 	        	 buff[i][j] = new Terrain(id);
	 	        	 break;
	 	         case 1:
	 	        	 buff[i][j] = new TerrainWood(id);
	 	        	 break;
	 	         case 2:
	 	        	 buff[i][j] = new TerrainWater(id, rleft);
	 	        	 break;
	 	         }*/
	 	         buff[i][j] = TerrainBuilder(id, resource, rleft, ttype, towner);
	 	         
	 	         //System.out.println(buff[i][j]);
	 	         rs.next();
	    	  }
	      }
	      
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		
		return buff;
	}
	
	public static void overwriteTerrain(Terrain t)
	{
		if (!(t instanceof TerrainPlayer))
		{
			System.err.println("Terrain is not instance of TerrainPlayer");
			return;
		}
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:World.db");
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      String sql = "UPDATE TERRAIN set RESOURCE = " + t.getResource().ordinal() + ", RLEFT = 0, TTYPE="+ t.getType().ordinal() + ", TOWNER = " +t.getTOwner() +  " where ID=" + t.getId() +";";
	      System.out.println(sql);
	      stmt.executeUpdate(sql);
	      c.commit();

	
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	public static void addWorldToDatabase(World world)
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:World.db");
	      c.setAutoCommit(false);


	      stmt = c.createStatement();
	     // String sql = "INSERT INTO Terrain (ID,NAME,AGE,ADDRESS,SALARY) " +
	     //              "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
	      //stmt.executeUpdate(sql);

	      String sql = "";
	      for (int i = 0; i < world.size; i++)
	      {
	    	  for (int j = 0; j < world.size; j++)
	    	  {
	    		  Terrain terrain = world.getTerrain(i, j);
	    		  sql = "INSERT INTO Terrain(ID, RESOURCE, RLEFT,TTYPE)" +
	    				" VALUES (" + terrain.getId()+ ", " +
	    				         terrain.getResource().ordinal();
	    		  
	    		  if (terrain instanceof TerrainWater)
	    		  {
	    			  sql += ", " + ((TerrainWater) terrain).getNumberFish();
	    		  }
	    		  else
	    		  {
	    			  sql += ", 0";
	    		  }
	    		  
	    		  sql += ", " +terrain.getType().ordinal() + ");";
	    		  System.out.println(sql);
	    		  
	    		  stmt.executeUpdate(sql);
	    	  }
	      }

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
}
