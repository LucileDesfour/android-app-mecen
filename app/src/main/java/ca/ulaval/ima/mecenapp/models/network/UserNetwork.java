package ca.ulaval.ima.mecenapp.models.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.fragments.Login;
import ca.ulaval.ima.mecenapp.models.Users;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserNetwork {


    public static void getToken(String email, String password, FragmentActivity activity, Login login){
        OkHttpClient client=new OkHttpClient();


        RequestBody formBody = new FormBody.Builder()
                .add("email", email).add("password",password)
                .build();

        Request request=new Request.Builder().url("https://mecenapp-api-dev.herokuapp.com/api/auth/sign-in")
                .post(formBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){
                    final String myResponse = response.body().string();
                    JSONObject myjsonObject= null;
                    try {
                        myjsonObject = new JSONObject(myResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        String token =myjsonObject.getString("token");
                        Users.currentUser=new Users.User(token);
                        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(("token"), token);
                        editor.commit();
                        login.closeFragment();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });


    }
}
