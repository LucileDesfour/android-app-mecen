package ca.ulaval.ima.mecenapp.activites;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.Login;
import ca.ulaval.ima.mecenapp.models.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class Connexion extends Fragment {
    Button buttonLogin;
    TextView textViewUsername, textViewpassword;


    public Connexion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_connexion, container, false);
       buttonLogin=(Button) v.findViewById(R.id.idbuttonLogin);
       buttonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               login();
           }
       });

        return v;
    }

    private static String token;

    private void login(){
        //Login login=new Login("mecenapp@gmail.com","ulaval2019"); // Recuperer celles de l'UI
        // Faire le call dans une fonction dans UserNetwork
        Activity activity=null;
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String token = null;
        token=sharedPref.getString("token", token);
        /*Call<Users.User> call=userClient.login(login);

        call.enqueue(new Callback<Users.User>() {
            @Override
            public void onResponse(Call<Users.User> call, Response<Users.User> response) {

                if (response.isSuccessful()){

                    Toast.makeText(getContext(), response.body().getToken(), Toast.LENGTH_SHORT).show();

                    //On recupere le token
                    token=response.body().getToken();

                }else{

                    Toast.makeText(getContext(),"not correct :(", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Users.User> call, Throwable t) {
                Toast.makeText(getContext(),"error :(", Toast.LENGTH_SHORT).show();

            }
        });*/

    }


}
