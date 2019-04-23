package ca.ulaval.ima.mecenapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.network.UserNetwork;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    ImageView imageViewLogin;
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        editTextEmail=(EditText) view.findViewById(R.id.edit_text_email);
        editTextPassword=(EditText) view.findViewById(R.id.edit_text_password);
        buttonLogin=(Button) view.findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserNetwork.getToken(editTextEmail.getText().toString(),editTextPassword.getText().toString(), getActivity());

            }
        });

        return view;
    }


}
