package ca.ulaval.ima.mecenapp.models.network;

import android.content.Context;
import android.content.SharedPreferences;
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

        Log.d("Token :", project_id);

        Request request = new Request.Builder()
                .url("https://mecenapp-api-dev.herokuapp.com/api/projects/" + project_id)
                .header("token", sproject.getActivity().getPreferences(Context.MODE_PRIVATE).getString("token", null))
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
                    Log.d("Data :", data.toString());
                    ArrayList<String> ressources = new ArrayList<String>();
                    JSONArray jsonArray_resources = data.getJSONArray("resources");
                    if (jsonArray_resources != null) {
                        for (int idx_ressources=0; idx_ressources<jsonArray_resources.length(); idx_ressources++){
                            ressources.add(jsonArray_resources.get(idx_ressources).toString());
                        }
                    }
                    Log.d("ressources ", String.valueOf(ressources));

                    ArrayList<String> domains = new ArrayList<String>();
                    JSONArray jsonArray_domains = data.getJSONArray("domains");
                    if (jsonArray_domains != null) {
                        for (int idx_domains=0; idx_domains<jsonArray_domains.length(); idx_domains++){
                            domains.add(jsonArray_domains.get(idx_domains).toString());
                        }
                    }
                    Log.d("domains", String.valueOf(domains));
                    Projects.currentProject = new Projects.Project(object.get("id").toString(),
                                object.get("title").toString(),
                                object.get("isPublic").toString(),
                                data.get("description").toString(),
                                ressources,
                                domains);
                    OrgaNetwork.getOrga(object.get("orgaId").toString(), object.get("id").toString(), sproject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //sproject.ChangeData();
            }
        });

    }

    static public void getProjects(SearchProject sproject) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mecenapp-api-dev.herokuapp.com/api/projects")
                .header("token", sproject.getActivity().getPreferences(Context.MODE_PRIVATE).getString("token", null))
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
                    Projects.project_list.clear();
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        JSONObject data = new JSONObject(object.get("data").toString());
                        ArrayList<String> ressources = new ArrayList<String>();
                        JSONArray jsonArray_resources = data.getJSONArray("resources");
                        if (jsonArray_resources != null) {
                            for (int idx_ressources=0; idx_ressources<jsonArray_resources.length(); idx_ressources++){
                                ressources.add(jsonArray_resources.get(idx_ressources).toString());
                            }
                        }
                        Log.d("ressources ", String.valueOf(ressources));

                        ArrayList<String> domains = new ArrayList<String>();
                        JSONArray jsonArray_domains = data.getJSONArray("domains");
                        if (jsonArray_domains != null) {
                            for (int idx_domains=0; idx_domains<jsonArray_domains.length(); idx_domains++){
                                domains.add(jsonArray_domains.get(idx_domains).toString());
                            }
                        }
                        Log.d("domains", String.valueOf(domains));
                        Log.d("Proj :", data.toString());
                        Projects.project_list.add(new Projects.Project(object.get("id").toString(),
                                object.get("title").toString(),
                                object.get("isPublic").toString(),
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
