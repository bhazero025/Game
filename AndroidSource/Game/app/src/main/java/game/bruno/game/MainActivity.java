package game.bruno.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

import game.bruno.game.account.UserAccount;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    public static NetworkThread networkThread = new NetworkThread();
    //TODO: FIX this ugly thing
    public static android.content.Context context;
    public static FragmentManager fragmentManager;
    private static LoginFragment loginFragment;
    private static WorldFragment worldFragment;
    private static AccountInfoFragment accountInfoFragment;

    public static volatile UserAccount userAccount;
    protected static TextView loginInfo;


    public static void createToast(final String text)
    {

        Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Toast.makeText(MainActivity.context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        };
        mHandler.sendMessage(new Message());

    }

    public static void updateLogin()
    {
        if (userAccount != null)
        {
            Log.w("SERVER", "userAccount assigned");
            Handler mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message message)
                {
                    loginInfo.setText("Username: " + userAccount.getUsername() + " ID:" + userAccount.getID());
                }
            };
            mHandler.sendMessage(new Message());


            //now kill the login fragment

            fragmentManager.beginTransaction().remove(loginFragment).commit();
        }
        else
        {
            MainActivity.createToast("Something went wrong, check your username and password");
            Log.w("SERVER", "Setting up account failure, userAccount == null");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loginInfo = (TextView) findViewById(R.id.loginInfo);
        /*Button log = (Button) findViewById(R.id.bLogin);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Command buffer = new Command();
                buffer.addCommand("brunoUser");
                buffer.addCommand("brunoPass");
                NetworkThread.commandArrayList.add(buffer);
            }
        });

        Button getWorldBtn = (Button) findViewById(R.id.bAskMap);
        getWorldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Command buffer = new Command();
                buffer.addCommand("getWorld");

                NetworkThread.commandArrayList.add(buffer);
            }
        });

        Button net = (Button) findViewById(R.id.initializeNetworkThread);
        net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread a = new Thread(networkThread);
                a.start();
            }
        });*/

        Thread a = new Thread(networkThread);
        a.start();
        context = getApplicationContext();


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transition = fragmentManager.beginTransaction();
        loginFragment = new LoginFragment();
        transition.add(R.id.content_main, loginFragment);
        transition.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {

        }
        else if (id == R.id.nav_gallery)
        {
            worldFragment = new WorldFragment();
            FragmentTransaction transition = fragmentManager.beginTransaction();
            transition.replace(R.id.content_main, worldFragment);
            transition.commit();
        }
        else if (id == R.id.nav_slideshow)
        {
            accountInfoFragment = new AccountInfoFragment();
            FragmentTransaction transition = fragmentManager.beginTransaction();
            transition.replace(R.id.content_main, accountInfoFragment);
            transition.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
