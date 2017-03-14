package game.bruno.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment
{

    private Button login;
    private Button password;
    private EditText loginText;
    private EditText passwordText;
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
    }




}
