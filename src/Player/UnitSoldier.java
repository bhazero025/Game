package Player;

public class UnitSoldier extends Unit
{

	public UnitSoldier() 
	{
		this.setAttack(50);
		this.setCost(25);
		this.setFood(20);
		this.setWork(0);
		this.setHealth(110);
	}
	
	/**
	 * Attacks a unit
	 * @param attack
	 * @return
	 */
	public Unit attackUnit(Unit attack)
	{
		attack.setHealth(attack.getHealth() - this.getAttack());
		
		return attack;
	}

}
