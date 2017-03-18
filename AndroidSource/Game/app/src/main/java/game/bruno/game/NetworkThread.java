package game.bruno.game;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import game.bruno.game.account.UserAccount;
import game.bruno.game.server.World;

/**
 * Created by bruno on 3/10/2017.
 */

public class NetworkThread implements Runnable
{

    public static volatile ArrayList<Command> commandArrayList = new ArrayList<>();
    private Socket client;
    private long lastEchoCall;

    /**
     * Used on WorldFragment
     */
    public static volatile boolean worldUpdated = false;

    @Override
    public void run()
    {
        while(true)
        {
            try {
                Log.w("SERVER", "Trying to log in");
                client = new Socket("192.168.0.2", 25801);


                Log.w("SERVER", "Client connection " + (client.isConnected() ? "true" : "false"));
                Log.w("SERVER", "Connected");
                MainActivity.createToast("Connected to server");
                lastEchoCall = System.currentTimeMillis();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try
        {
            while (true) {


                long currentTime = System.currentTimeMillis();



                System.out.println(commandArrayList.size() != 0 ? commandArrayList.size() : "No commands on queue");
                for (int i = 0; i < commandArrayList.size(); i++) {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                        commandArrayList.get(i).addCommand(client.getInetAddress().toString());
                        oos.writeObject(commandArrayList.get(i));
                        System.out.println("Wrote command to server " + commandArrayList.get(i).getCommand());
                        commandArrayList.remove(i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (client.getInputStream().available() != 0)
                {

                    Log.w("SERVER", "SERVER SEND AN OBJECT");
                    ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

                    Object buffer = ois.readObject();

                    if (buffer instanceof World)
                    {
                        World.setWorld(((World) buffer));

                        worldUpdated = true;
                    }
                    else if (buffer instanceof Command)
                    {
                        Command cBuffer = ((Command)buffer);
                        Log.w("SERVER", cBuffer.getCommand().equals("echo") ? "" : cBuffer.getCommand());

                        ArrayList<String> commandList = cBuffer.toArrayList();
                        if (commandList.get(0).equals("loginError"))
                        {
                            MainActivity.createToast("Wrong Credentials");
                        }
                        else if (commandList.get(0).equals("creatingAccountError"))
                        {
                            MainActivity.createToast("Username already used");
                        }

                    }
                    else if (buffer instanceof UserAccount)
                    {
                        MainActivity.userAccount = ((UserAccount) buffer);
                        Log.w("SERVER", "UserAccount set");
                        MainActivity.updateLogin();
                    }


                }

                Thread.sleep(1000);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }
}


