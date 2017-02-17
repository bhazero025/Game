package Server;

import java.util.ArrayList;

public class ActionThread implements Runnable
{
	public static volatile ArrayList<Action> actionList;

	public ActionThread()
	{
		actionList = new ArrayList<>();
	}

	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				for (int i = 0; i < actionList.size(); i++)
				{
					System.out.println(actionList.size());
					if (actionList.get(i).isFinished())
					{
						System.out.println("Rewarding gold");
						actionList.get(i).rewardGold();
						actionList.remove(i);
						break;
					}
					
				}
				
				Thread.sleep(1000);
				
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}
		
	}

}
