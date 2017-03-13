package game.bruno.game;




import java.io.Serializable;

/**
 * Has to match android package
 * TODO Change the name of the package
 * @author bruno
 *
 */
public class Command implements Serializable
{

	private String commandList = "";
	
	public Command() 
	{
		
	}
	
	public void addCommand(String command)
	{
		commandList += command;
	}
	
	
	public String getCommand()
	{
		return this.commandList;
	}
	

}
