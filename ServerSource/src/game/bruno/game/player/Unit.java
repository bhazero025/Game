package game.bruno.game.player;

import java.io.Serializable;

public class Unit implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private int attack;
	private int work;
	private int cost;
	private int food;
	private int health;
	
	public Unit()
	{
		this.attack = 0;
		this.work = 0;
		this.cost = 10;
		this.food = 10;
	}
	
	public int getAttack()
	{
		return this.attack;
	}
	
	public int getWork()
	{
		return this.work;
	}
	
	public int getCost()
	{
		return this.cost;
	}
	
	public void setAttack(int attack)
	{
		this.attack = attack;
	}

	public void setWork(int work) 
	{
		this.work = work;
	}

	public void setCost(int cost) 
	{
		this.cost = cost;
	}
	
	public int getFood() 
	{
		return food;
	}

	public void setFood(int food)
	{
		this.food = food;
	}

	public int getHealth() 
	{
		return health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}

}
