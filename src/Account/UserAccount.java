package Account;

import java.util.ArrayList;

import Player.Unit;
import Server.Terrain;

public class UserAccount
{
	//List of units
	private ArrayList<Unit> unitList;
	
	//List units on the terrain
	private ArrayList<Terrain> terrainOwned;
	private String username;
	private int id;
	private int base;
	private int gold;
	private int fish;
	private int wood;
	
	public UserAccount(int id, int base, ArrayList<Unit> unitList, ArrayList<Terrain> terrainOwned) 
	{
		this.id = id;
		this.base = base;
		this.unitList = unitList;
		this.terrainOwned = terrainOwned;
		this.username = null;
	}
	
	public void debugMe()
	{
		if(this.username != null)
		{
			System.out.println(this.username);
		}
		System.out.println(id);
		System.out.println(unitList);
		System.out.println(terrainOwned);
		System.out.println(gold);
	}
	
	public int getBase()
	{
		return this.base;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return this.username;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getFish() {
		return fish;
	}

	public void setFish(int fish) {
		this.fish = fish;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public void addGold(int gold)
	{
		this.gold += gold;
	}
}
