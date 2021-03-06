package game.bruno.game.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.bruno.game.Command;
import game.bruno.game.account.UserAccount;

public class OpenConnectionHandler implements Runnable
{

	private volatile static ArrayList<PlayerSocket> currentUsers = new ArrayList<>();
	private volatile static ArrayList<Socket> socketQueue = new ArrayList<>();
	
	private static OpenConnectionHandler singleton;
	
	public static OpenConnectionHandler getInstance()
	{
		if (singleton == null)
		{
			singleton = new OpenConnectionHandler();
		}
		
		return singleton;
	}
	
	protected static int getSocketQueueSize()
	{
		return socketQueue.size();
	}
	
	protected static int getCurrentUsersOnline()
	{
		return currentUsers.size();
	}
	
	private OpenConnectionHandler() 
	{
		
	}

	public void addPlayerSocketQueue(Socket s)
	{
		socketQueue.add(s);
		System.out.println("Added socket to queue " + s);
	}
	
	/**
	 * Updates the playerSocket array with new elements
	 */
	private void updatePlayerSocket()
	{
		//check if we have elements on the list
		if (socketQueue.size() != 0)
		{
			//add all the sockets to the PlayerSocket array
			for (int i = 0; i < socketQueue.size(); i++)
			{
				addPlayerSocket(socketQueue.get(i));
				System.out.println("Added socket to PlayerSocket array " + socketQueue.get(i));
			}
			
			//now remove all the elements from our socketQueue
			for (int i = 0; i < socketQueue.size(); i++)
			{
				socketQueue.remove(i);
			}
		}
	}
	
	private void addPlayerSocket(Socket s)
	{
		PlayerSocket buff = new PlayerSocket(s);
		currentUsers.add(buff);
	}
	
	@Override
	public void run() 
	{
		while (true)
		{
			updatePlayerSocket();
			try 
			{
				//System.out.println("Users online " + currentUsers.size());
				
				for (int i = 0; i < currentUsers.size(); i++)
				{
				
					//System.out.println("---Processing IP " + currentUsers.get(i).getSocket().getInetAddress() + "---");
					
					//I think this feature works
					if (!currentUsers.get(i).isAlive())
					{
						System.out.println("user not alive");
						currentUsers.get(i).close();
						currentUsers.remove(i);
						continue;
					}
										
					if (currentUsers.get(i).isReady() != 0)
					{
						System.out.println("Reading command from " + currentUsers.get(i));
						ObjectInputStream ois = new ObjectInputStream(currentUsers.get(i).getSocket().getInputStream());
						Command buffer = (Command) ois.readObject();
						System.out.println(buffer.getCommand());
				
						
						if (buffer.getCommand().contains("getWorld"))
						{
							ObjectOutputStream obs = new ObjectOutputStream(currentUsers.get(i).getSocket().getOutputStream());
							obs.writeObject(World.getWorld());
							System.out.println("Wrote to client World");
						}
						else if (buffer.getCommand().contains("login"))
						{
							/*List<String> bufferList = Arrays.asList(buffer.getCommand().split("#splitchar#"));
							ArrayList<String> commandList = new ArrayList<>();
							commandList.addAll(bufferList);*/
							ArrayList<String> commandList = buffer.toArrayList();
							
							/**
							 * 0 = login tag
							 * 1 = username
							 * 2= password
							 */
							
							ObjectOutputStream obs = new ObjectOutputStream(currentUsers.get(i).getSocket().getOutputStream());
							UserAccount account = DBHandler.getLogin(commandList.get(1), commandList.get(2));
							System.out.println(account);
							if (account != null)
							{
								obs.writeObject(account);
							}
							else
							{
								Command error = new Command();
								error.addCommand("loginError");
								error.addCommand("wrongCredentials");
								obs.writeObject(error);
							}
							
							System.out.println("Gave login info");
							
						}
						else if (buffer.getCommand().contains("createAccount"))
						{
							ArrayList<String> commandList = buffer.toArrayList();
							String login = commandList.get(1);
							String pass = commandList.get(2);
							String ip = commandList.get(3);
							
							UserAccount newAccount = DBHandler.createNewAccount(login, pass, ip);
							ObjectOutputStream obs = new ObjectOutputStream(currentUsers.get(i).getSocket().getOutputStream());
							
							if (newAccount != null)
							{
								obs.writeObject(newAccount);
							}
							else
							{
								Command error = new Command();
								error.addCommand("creatingAccountError");
								System.out.println("Error creating account");
								obs.writeObject(error);
								
							}
							
						}
						else if (buffer.getCommand().contains("echo"))
						{
							System.out.println("Got echo from client");
							//now we have to send an echo back to the client
							
						}
						
						
					}
					
					//System.out.println("---Finished IP   " + currentUsers.get(i).getSocket().getInetAddress() + "---");
					//System.out.println("\n");
				}
				
				
				Thread.sleep(1000);
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
			}
		}
		
	}

}
