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
import ca.ulaval.ima.mecenapp.fragments.ShowProject;
import ca.ulaval.ima.mecenapp.models.Projects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProjectNetwork  {

    static public void getProject(ShowProject sproject, String project_id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mecenapp-api-dev.herokuapp.com/api/projects/" + project_id)
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
                    JSONObject object = Jobject.getJSONObject("project");
                    JSONObject data = object.getJSONObject("data");
                    ArrayList<String> ressources = new ArrayList<String>();
                    JSONArray jsonArray_resources = data.getJSONArray("resources");
                    if (jsonArray_resources != null) {
                        int len = jsonArray_resources.length();
                        for (int i=0; i<len; i++){
                            ressources.add(jsonArray_resources.get(i).toString());
                        }
                    }
                    ArrayList<String> domains = new ArrayList<String>();
                    JSONArray jsonArray_domains = data.getJSONArray("domains");
                    if (jsonArray_domains != null) {
                        int len = jsonArray_domains.length();
                        for (int i=0; i<len; i++){
                            domains.add(jsonArray_domains.get(i).toString());
                        }
                    }
                    Projects.currentProject = new Projects.Project(object.get("id").toString(),
                                object.get("title").toString(),
                                object.get("isPublic").toString(),
                                object.get("orgaId").toString(),
                                data.get("description").toString(),
                                ressources,
                                domains);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sproject.ChangeData();
            }
        });

    }

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
                        ArrayList<String> ressources = new ArrayList<String>();
                        JSONArray jsonArray_resources = data.getJSONArray("resources");
                        if (jsonArray_resources != null) {
                            int len = jsonArray_resources.length();
                            for (int idx=0; idx<len; idx++){
                                ressources.add(jsonArray_resources.get(i).toString());
                            }
                        }
                        Log.d("ressources ", String.valueOf(ressources));
                        ArrayList<String> domains = new ArrayList<String>();
                        JSONArray jsonArray_domains = data.getJSONArray("domains");
                        if (jsonArray_domains != null) {
                            int len = jsonArray_domains.length();
                            for (int idx=0; idx<len; idx++){
                                domains.add(jsonArray_domains.get(i).toString());
                            }
                        }
                        Log.d("domains", String.valueOf(domains));
                        Log.d("Proj :", data.toString());
                        Projects.project_list.add(new Projects.Project(object.get("id").toString(),
                                object.get("title").toString(),
                                object.get("isPublic").toString(),
                                object.get("orgaId").toString(),
                                data.get("description").toString(),
                                ressources,
                                domains));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sproject.ChangeData();
            }
        });
    }
}
