package ca.ulaval.ima.mecenapp.models.network;

import android.support.v4.app.FragmentActivity;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MessageNetwork {

    public static void getToken(String email, String password, FragmentActivity activity){
        OkHttpClient client=new OkHttpClient();


        RequestBody formBody = new FormBody.Builder()
                .add("email", email).add("password",password)
                .build();
    }
}
