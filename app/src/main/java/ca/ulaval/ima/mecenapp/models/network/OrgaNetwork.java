package ca.ulaval.ima.mecenapp.models.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ca.ulaval.ima.mecenapp.models.Orgas;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrgaNetwork {
    public static Orgas.Orga getOrga(String orgaId) {
        final Orgas.Orga[] orga = {null};
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mecenapp-api-dev.herokuapp.com/api/orgas/" + orgaId)
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhMGMwNzgyMS05MWYyLTQyZTctODYzOS04Nzg3ZjdkMmZkYzUiLCJpYXQiOjE1NTQxNTI3NzQsImV4cCI6MTU1NDIzOTE3NH0.n47zH3SNHgTrQw6cdaSdvFgGfH2m2_ZRM-Y21ok-6LQ")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                final String myResponse = response.body().string();
                try {
                    JSONObject body = new JSONObject(myResponse);
                    JSONObject o = new JSONObject(body.get("orga").toString());
                    orga[0] = new Orgas.Orga(o.get("name").toString(), o.get("id").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return orga[0];
    }
}
