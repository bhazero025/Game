package game.bruno.game.server;

import java.util.Scanner;

public class ScannerThread implements Runnable
{

	private final String SOCKET_QUEUE_SIZE = "queue_size";
	private final String ONLINE_PLAYERS = "online_players";
	private final String HELP = "help";
	
	private Scanner stdin;
	
	public ScannerThread() 
	{
		stdin = new Scanner(System.in);
	}

	@Override
	public void run()
	{
		System.out.println("Scanner sucess");
		while (true)
		{
			String input = stdin.nextLine();
			
			switch(input)
			{
			case HELP:
				System.out.println("TODO THIS");
				break;
			case SOCKET_QUEUE_SIZE:
				System.out.println(OpenConnectionHandler.getSocketQueueSize());
				break;
				
			case ONLINE_PLAYERS:
				System.out.println(OpenConnectionHandler.getCurrentUsersOnline());
				break;
			default:
				System.out.println("Unkown command, use help to show list of available commands");
				break;
			}
					
			if(input.equals(SOCKET_QUEUE_SIZE))
			{
				System.out.println(OpenConnectionHandler.getSocketQueueSize());
			}
			
		}
		
	}

}
