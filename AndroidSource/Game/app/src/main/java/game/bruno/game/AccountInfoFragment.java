package game.bruno.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfoFragment extends Fragment {

    private View view;

    public AccountInfoFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_info, container, false);
        setup();
        return view;
    }

    private void setup()
    {
        TextView text = (TextView) view.findViewById(R.id.accountInfo);


        text.setText("" +  MainActivity.userAccount.toString());
    }

}
