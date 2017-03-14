package game.bruno.game.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.bruno.game.Command;
import game.bruno.game.account.UserAccount;


public class PlayerSocket 
{

	private Socket client;
	private UserAccount clientAccount;
	
	
	public PlayerSocket(Socket client)
	{
		this.client = client;
	}
	
	public void login(String username, String password)
	{
		clientAccount = DBHandler.getLogin("brunoUser", "brunoPass");
	}
	
	public Socket getSocket()
	{
		return this.client;
	}

	
	public int isReady()
	{
		try {

			return client.getInputStream().available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public boolean isAlive()
	{
		try 
		{
			new ObjectOutputStream(this.client.getOutputStream()).writeObject(new Command("echo"));
			return true;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public void close()
	{
		try 
		{
			this.client.close();
			
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
