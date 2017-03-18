package game.bruno.game.account;

import java.io.Serializable;
import java.util.ArrayList;

import game.bruno.game.player.Unit;
import game.bruno.game.server.*;


public class UserAccount implements Serializable
{
	private static final long serialVersionUID = 1L;

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
			System.out.println("Username:" + this.username);
		}
		System.out.println("ID " + id);
		System.out.println("unitList " + unitList);
		System.out.println("terrainOwnded " + terrainOwned);
		System.out.println("gold " + gold);
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

	public int getID()
	{
		return this.id;
	}
	//TODO: Add gold to the db
	public void addGold(int gold)
	{
		this.gold += gold;
	}

	@Override
	public String toString() {
		return "UserAccount [unitList=" + unitList + ", terrainOwned=" + terrainOwned + ", username=" + username
				+ ", id=" + id + ", base=" + base + ", gold=" + gold + ", fish=" + fish + ", wood=" + wood + "]";
	}
}
