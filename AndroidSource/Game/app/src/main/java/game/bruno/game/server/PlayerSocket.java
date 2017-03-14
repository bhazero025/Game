package game.bruno.game.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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
	
	

}
