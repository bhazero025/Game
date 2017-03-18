package game.bruno.game.server;


import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import game.bruno.game.account.*;
import game.bruno.game.player.*;


public class DBHandler 
{

	public static void main(String [] args)
	{

		//DBHandler.createLoginTable();
		//DBHandler.createNewAccount("brunoUser", "brunoPass");
		//UserAccount u = DBHandler.getLogin("brunoUser", "brunoPass");
		//u.debugMe();
		//DBHandler.createNewAccount("bruno", "bruno", "localhost");
		
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
	    System.out.println("DB: OVERWROTE TERRAIN SUCESS");
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
	
	public static int findOpenTerrain()
	{
		World.getWorld().loadWorld();
		
		while (true)
		{
			int rnd = new Random().nextInt(World.size * World.size);
			Terrain buff = World.getWorld().getTerrainById(rnd);
			if (buff.getResource() == TerrainData.Resource.NOTHING && buff.getTOwner() == 0)
			{
				
				return buff.getId();
			}
			
		}
		
	}
	
	/**
	 * Transforms current terrain to TerrainPlayer
	 * @param openTerrain
	 */
	private static void createAccountHelper(int openTerrain, String login)
	{
		TerrainPlayer t;
		
		//find out player id
		int id = getPlayerID(login);
		System.out.println("Got player id");
		
		t = new TerrainPlayer(openTerrain, id);
		
		World.getWorld().overwriteTerrain(t);
		System.out.println("Terrain overwrote");
		
	}
	
	public static int getPlayerID(String login)
	{
		int id = 0;
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Login.db");
	      c.setAutoCommit(false);


	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Login where USERNAME='"+login+"';" );
	      rs.next();
	      
	      id = rs.getInt("id");
	      
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    
		return id;
	}
	
	public static UserAccount createNewAccount(String login, String password, String Ip)
	{
		UserAccount newAccount = null;
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Login.db");
	      c.setAutoCommit(false);


	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM LOGIN where Username='" + login + "'");
	      c.commit();
	      
	      if (!rs.next())
	      {
	    	  /**
	    	   * Unit syntax
	    	   * Worker,Soldier,Ranged,Tank
	    	   */
	    	  System.out.println("Creating account");
	    	  stmt = c.createStatement();
	    	  int openTerrain = findOpenTerrain();
	    	  String sql = "INSERT INTO LOGIN (USERNAME, PASSWORD, TBASE, TOWNED, UNITS, GOLD, CIP, CDATE, LLOGIN, IP) " +
	    			  		"VALUES ('"+ login +"', '" + password +"', '" + openTerrain +"','-1', '0,0,0,0', 10, '"+Ip+"', '"+System.currentTimeMillis()+"', '0', '0');";
	    	  System.out.println(sql);
	    	  stmt.executeUpdate(sql);
	    	  c.commit();
	    	  
	    	  //Now convert terrain to TerrainPlayer
	    	  createAccountHelper(openTerrain, login);
	    	  System.out.println("Created account sucesfully");
	    	  newAccount = DBHandler.getLogin(login, password);
	    	  
	      }
	      else
	      {
	    	  System.out.println("Username already used"); 
	    	  return null;
	      }
	      
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return newAccount;

	}
	
	
	public static UserAccount getLogin(String username, String password)
	{
		UserAccount buffer = null;
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Login.db");
	      c.setAutoCommit(false);
	     // System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Login where USERNAME='"+ username +"' and PASSWORD='" + password +"';" );
	      while ( rs.next() ) 
	      {
	    	  //Get the data from the db
	    	  int id;
	    	  String tOwned, units, tBase;
	    	  id = rs.getInt("ID");
	    	  tOwned = rs.getString("TOWNED");
	    	  units = rs.getString("UNITS");
	    	  tBase = rs.getString("TBASE");
	    	  
	    	  //Build this lists
	    	  String[] bufTOwned = tOwned.split(",");
	    	  String[] bufUnits = units.split(",");
	    	  
	    	  ArrayList<Terrain> tList = new ArrayList<>();
	    	  ArrayList<Unit> uList = new ArrayList<>();
	
	    	  //Add data to the lists
	    	  for (String a : bufTOwned)
	    	  {
	    		  if (!a.equals("-1"))
	    		  {
	    			  tList.add(World.getWorld().getTerrainById(Integer.parseInt(a)));
	    		  }
	    	  }
	    	
	    	  
	    	  for (int i = 0; i < bufUnits.length; i++)
	    	  {
	    		  switch(i)
	    		  {
	    		  case 0:
	    			  for (int cc = 0; cc < Integer.parseInt(bufUnits[i]); cc++)
	    			  {
	    				  uList.add(new UnitWorker()); 
	    			  }
	    			  break;
	    		  case 1:
	    			  for (int cc = 0; cc < Integer.parseInt(bufUnits[i]); cc++)
	    			  {
	    				  uList.add(new UnitSoldier()); 
	    			  }
	    			  break;
	    		  }
	    	  }

	    	  buffer = new UserAccount(id, Integer.parseInt(tBase), uList, tList);
	    	  buffer.setUsername(username);
	    	  
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		
		
		return buffer;
	}
	
	public static void createLoginTable()
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Login.db");
	  

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE Login " +
	                   "(ID INTEGER PRIMARY KEY      AUTOINCREMENT, " +
	                   " USERNAME       TEXT     NOT NULL, " + 
	                   " PASSWORD       TEXT     NOT NULL, " +
	                   " TBASE          TEXT     NOT NULL, " + //Terrrain Base
	                   " TOWNED         TEXT     NOT NULL, " + //Terrains Owned (not base)
	                   " UNITS          TEXT     NOT NULL, " +
	                   " GOLD           INTEGER  NOT NULL, " +
	                   " CIP            TEXT     NOT NULL, " + //Creation IP
	                   " CDATE          TEXT     NOT NULL, " + //Creation Date
	                   " LLOGIN         TEXT     NOT NULL, " + //Last login date
	                   " IP             TEXT     NOT NULL );"; //IP
	      
	      
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
}
