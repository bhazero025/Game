package Server;

import Account.UserAccount;

public class Action 
{
	private UserAccount user;
	private volatile long startingTime;
	private volatile long endTime;
	private int gold;
	
	
	public Action(UserAccount user) 
	{
		this.user = user;
		this.gold = 0;
	}

	/**
	 * Start the timer in minutes
	 * @param minutes
	 * @param terrainID
	 */
	public void startTimer(int minutes, int terrainID)
	{
		Terrain t = World.getWorld().getTerrainById(terrainID);
		if (t instanceof TerrainWater)
		{
			gold += 10;
		}
		else if (t instanceof TerrainWood)
		{
			gold += 15;
		}
		else
		{
			System.err.println("ACTION: Terrain isn't wood or water");
		}
		gold *= minutes == 0 ? 1 : minutes;
		this.startingTime = System.currentTimeMillis();
		this.endTime = startingTime + (minutes * 60000);
		
		//Add to ActionThread
		ActionThread.actionList.add(this);
	}
	
	
	public boolean isFinished()
	{
		return System.currentTimeMillis() > this.endTime;
	}
	
	public void rewardGold()
	{
		this.user.addGold(gold);
	}
	
}
