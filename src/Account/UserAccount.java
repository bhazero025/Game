package Account;

import java.util.ArrayList;

import Player.Unit;
import Server.Terrain;

public class UserAccount
{
	private ArrayList<Unit> unitList;
	private ArrayList<Terrain> terrainOwned;
	private int id;
	private int base;
	
	public UserAccount(int id, int base, ArrayList<Unit> unitList, ArrayList<Terrain> terrainOwned) 
	{
		this.id = id;
		this.base = base;
		this.unitList = unitList;
		this.terrainOwned = terrainOwned;
	}
	
	public void debugMe()
	{
		System.out.println(id);
		System.out.println(unitList);
		System.out.println(terrainOwned);
	}
	

}
