package game.bruno.game.player;

public class UnitWorker extends Unit
{
	private static final long serialVersionUID = 1L;
	public UnitWorker() 
	{
		this.setAttack(0);
		this.setCost(20);
		this.setWork(100);
		this.setFood(0);
	}

}
