package ca.ulaval.ima.mecenapp.models.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ca.ulaval.ima.mecenapp.fragments.ShowProject;
import ca.ulaval.ima.mecenapp.models.Orgas;
import ca.ulaval.ima.mecenapp.models.Projects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrgaNetwork {
    public static void getOrga(String orgaId, String project_id, final ShowProject sproject) {
        final Orgas.Orga[] orga = {null};
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mecenapp-api-dev.herokuapp.com/api/orgas/" + orgaId)
                .header("token", sproject.getActivity().getPreferences(Context.MODE_PRIVATE).getString("token", null))
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
                    Projects.currentProject.setOrga(orga[0]);
                    sproject.ChangeData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
