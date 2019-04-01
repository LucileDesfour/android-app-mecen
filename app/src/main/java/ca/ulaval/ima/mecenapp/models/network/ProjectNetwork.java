package ca.ulaval.ima.mecenapp.models.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.adapters.ProjectAdapter;
import ca.ulaval.ima.mecenapp.fragments.SearchProject;
import ca.ulaval.ima.mecenapp.models.Projects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProjectNetwork  {
    public String url= "https://mecenapp-api-dev.herokuapp.com/";
    public String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhMGMwNzgyMS05MWYyLTQyZTctODYzOS04Nzg3ZjdkMmZkYzUiLCJpYXQiOjE1NTQxNTAwNjEsImV4cCI6MTU1NDIzNjQ2MX0.gzXZo2qyK8WE0G0W83aw4MLt3IJyLcqlnt2Uac9PktE";

    static public void getProjects(SearchProject sproject) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mecenapp-api-dev.herokuapp.com/api/projects")
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhMGMwNzgyMS05MWYyLTQyZTctODYzOS04Nzg3ZjdkMmZkYzUiLCJpYXQiOjE1NTQxNTI3NzQsImV4cCI6MTU1NDIzOTE3NH0.n47zH3SNHgTrQw6cdaSdvFgGfH2m2_ZRM-Y21ok-6LQ")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                Log.d("Reponse", myResponse);
                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(myResponse);
                    JSONArray Jarray = Jobject.getJSONArray("projects");

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        JSONObject data = new JSONObject(object.get("data").toString());
                        Log.d("Proj :", data.toString());
                        Projects.project_list.add(new Projects.Project(object.get("id").toString(),
                                object.get("title").toString(),
                                object.get("isPublic").toString(),
                                object.get("orgaId").toString(),
                                data.get("description").toString()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sproject.ChangeData();
            }
        });
    }
}
