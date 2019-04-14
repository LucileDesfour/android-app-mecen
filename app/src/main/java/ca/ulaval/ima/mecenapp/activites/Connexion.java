package ca.ulaval.ima.mecenapp.activites;


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
import ca.ulaval.ima.mecenapp.models.Service.UserClient;
import ca.ulaval.ima.mecenapp.models.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Connexion extends Fragment {
    Retrofit.Builder builder=new Retrofit.Builder()
            .baseUrl("https://mecenapp-api-dev.herokuapp.com/api/auth/sign-in")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    UserClient userClient= retrofit.create(UserClient.class);
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
        Login login=new Login("mecenapp@gmail.com","ulaval2019");
        Call<Users.User> call=userClient.login(login);

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
        });

    }


}
