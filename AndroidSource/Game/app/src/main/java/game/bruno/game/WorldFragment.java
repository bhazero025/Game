package game.bruno.game;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import game.bruno.game.server.World;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment
{
    private View view;
    private static TextView t;
    public static GetWorldThread getWorldThread;

    public WorldFragment()
    {
        // Required empty public constructor
    }


    public static void appendToTextView(final String textToAppend)
    {
        Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                t.append(textToAppend);
            }
        };
        mHandler.sendMessage(new Message());
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_world, container, false);
        setup();


        return view;
    }


    @Override
    public void onStart()
    {
        super.onStart();
    }


    private void setup()
    {
        Command askWorld = new Command();
        askWorld.addCommand("getWorld");
        NetworkThread.commandArrayList.add(askWorld);

        t = (TextView) view.findViewById(R.id.textView2);

        t.setText("Loading...");

        //start GetWorldThread
        //So we don't hang when the user clicks on World
        getWorldThread = new GetWorldThread();
        new Thread(getWorldThread).start();
    }

}
