package ca.ulaval.ima.mecenapp.models.network;
import ca.ulaval.ima.mecenapp.fragments.ChatRooms;
import ca.ulaval.ima.mecenapp.fragments.CreateChatRoom;
import ca.ulaval.ima.mecenapp.models.Rooms;
import ca.ulaval.ima.mecenapp.models.Users;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RoomsNetwork {
    private static final String BASE_URL ="https://mecenapp-chat-dev.herokuapp.com/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NWQ1ODMwOC0zY2E1LTQ4MTQtOThlMC00OWE0MzFjZDFiMDEiLCJpYXQiOjE1NTUxOTg4MjgsImV4cCI6MTU1NTI4NTIyOH0.GUBMMpNwWN7N3TwUo-N_L69n75DVMoSjYa0feGPLh2o";

    private static String inProcessRoomId;

    public static void getRooms(ChatRooms chatRooms){
        OkHttpClient client = new OkHttpClient();
        Rooms.rooms.clear();
        Request request = new Request.Builder()
                .url( BASE_URL + "chat/rooms/" )
                .header("token", token)
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
                JSONObject res = null;
                try {
                    res = new JSONObject(myResponse);
                    JSONArray jrooms = res.getJSONArray("rooms");
                    for (int i = 0; i < jrooms.length(); i++){
                        JSONObject jroom = jrooms.getJSONObject(i);
                        JSONArray jmembers = jroom.getJSONArray("members");
                        ArrayList<Users.User> members = new ArrayList<>();
                        Users.User manager = null;
                        for (int j = 0; j < jmembers.length(); j++){
                            JSONObject jmember = jmembers.getJSONObject(j);
                            JSONObject jprofile = jmember.getJSONObject("profile");
                            if (jmember.getString("id").equals(jroom.getString("managerId"))){
                                manager = new Users.User(
                                        jmember.getString("id"),
                                        jmember.getString("email"),
                                        jprofile.getString("firstName"),
                                        jprofile.getString("lastName"));
                            }
                            members.add(new Users.User(
                                    jmember.getString("id"),
                                    jmember.getString("email"),
                                    jprofile.getString("firstName"),
                                    jprofile.getString("lastName")
                            ));
                        }
                        Rooms.rooms.add(new Rooms.Room(
                                jroom.getString("id"),
                                members,
                                manager,
                                "noName"));
                    }
                    chatRooms.UpdateAdapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    static public void postRooms(ArrayList<Users.User> users,CreateChatRoom createChatRoom){
        OkHttpClient client = new OkHttpClient();

        Log.d("TEST",String.valueOf(users.size()));

        RequestBody body = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url( BASE_URL + "chat/rooms" )
                .method("POST",body)
                .header("token", token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                final String myResponse = response.body().string();;
                JSONObject res;
                try {
                    res = new JSONObject(myResponse);
                    JSONObject jroom = res.getJSONObject("room");
                    inProcessRoomId = jroom.getString("id");
                    Log.d("TEST", inProcessRoomId);
                    if (users.size() == 0){
                        createChatRoom.onCreatedRoom();
                    } else {
                        postMembers(inProcessRoomId, users, createChatRoom, 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static public void postMembers(String roomId, ArrayList<Users.User> members, CreateChatRoom createChatRoom,int index){
        OkHttpClient client = new OkHttpClient();

        String jmemberId = "{\"userId\": \""+members.get(index).getId()+"\"}";
        Log.d("TEST",jmemberId);
        Log.d("TEST",BASE_URL + "chat/rooms/"+roomId+"/members");
        RequestBody body = RequestBody.create(JSON,jmemberId);

        Request request = new Request.Builder()
                .url( BASE_URL + "chat/rooms/"+roomId+"/members" )
                .method("POST",body)
                .header("token", token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                Log.d("TEST",response.body().string());
                if (index == members.size()-1) {
                    createChatRoom.onCreatedRoom();
                } else {
                    postMembers(roomId,members,createChatRoom,index+1);
                }
            }
        });
    }
}

