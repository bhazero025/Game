package game.bruno.game;

import android.util.Log;

import game.bruno.game.server.World;

/**
 * Created by bruno on 3/16/2017.
 */

public class GetWorldThread implements Runnable
{

    @Override
    public void run()
    {
        int count = 0;
        while(!NetworkThread.worldUpdated)
        {
            Log.w("GetWorldThread", "IS THIS TAKING TOO LONG? " + count);
            count++;
        }

        NetworkThread.worldUpdated = true;


        for (int i = 0; i < World.size; i++)
        {
            for (int j = 0; j < World.size; j++)
            {
                WorldFragment.appendToTextView(World.getWorld().getTerrain(i, j) + " ");
            }
        }

        Log.w("getWorldThread", "Exiting getWorldThread...");
    }
}
