package game.bruno.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment
{

    private Button login;
    private Button createAccount;
    private EditText loginText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private TextView confirmPassword;
    private View view;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        setup();


        return view;
    }


    private void setup()
    {
        login = (Button) view.findViewById(R.id.buttonLogin);
        loginText = (EditText) view.findViewById(R.id.usernameText);
        passwordText = (EditText) view.findViewById(R.id.passwordText);
        createAccount = (Button) view.findViewById(R.id.buttonCreateAccount);
        confirmPassword = (TextView) view.findViewById(R.id.confirmPassword);
        confirmPasswordText = (EditText) view.findViewById(R.id.confirmPasswordText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Command buffer = new Command();
                buffer.addCommand("login");
                buffer.addCommand(loginText.getText().toString());
                buffer.addCommand(passwordText.getText().toString());
                NetworkThread.commandArrayList.add(buffer);

                loginText.setText("");
                passwordText.setText("");


            }
        });

        createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (confirmPassword.getVisibility()== View.INVISIBLE && confirmPasswordText.getVisibility() == View.INVISIBLE)
                {
                    confirmPassword.setVisibility(View.VISIBLE);
                    confirmPasswordText.setVisibility(View.VISIBLE);
                }
                else
                {
                    //Check if its the same password
                    if (confirmPasswordText.getText().toString().equals(passwordText.getText().toString()))
                    {
                        //Send action to create account
                        Command buffer = new Command();
                        buffer.addCommand("createAccount");
                        buffer.addCommand(loginText.getText().toString());
                        buffer.addCommand(passwordText.getText().toString());
                        NetworkThread.commandArrayList.add(buffer);
                    }
                    else
                    {
                        MainActivity.createToast("Passwords do not match");
                        passwordText.setText("");
                        confirmPasswordText.setText("");
                    }
                }

            }
        });


    }




}
