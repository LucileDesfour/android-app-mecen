package ca.ulaval.ima.mecenapp.models.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.fragments.SendMessages;
import ca.ulaval.ima.mecenapp.models.Messages;
import ca.ulaval.ima.mecenapp.models.Rooms;
import ca.ulaval.ima.mecenapp.models.Users;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageNetwork {
    private static final String BASE_URL = "https://mecenapp-chat-dev.herokuapp.com/";



    static public void getMessages(SendMessages smessage, String room_id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "chat/rooms/"+ room_id +"/messages/")
                .header("token", Users.currentUser.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                JSONObject res = null;
                try {
                    res = new JSONObject(myResponse);

                    JSONArray jmessage = res.getJSONArray("messages");
                    ArrayList<Messages.Message> messages = new ArrayList<>();
                    for (int i = 0; i < jmessage.length(); i++) {
                        Users.User user = null;
                        for (int j = 0; j < Rooms.current_room.members.size(); j++) {
                            if (Rooms.current_room.members.get(j).getId().equals(jmessage.getJSONObject(i).get("fromUserId").toString())) {
                                user = new Users.User(Rooms.current_room.members.get(j).getId(),
                                        Rooms.current_room.members.get(j).getEmail(),
                                        Rooms.current_room.members.get(j).getFirstName(),
                                        Rooms.current_room.members.get(j).getLastName());
                            }
                        }
                        messages.add(new Messages.Message(jmessage.getJSONObject(i).get("id").toString(),
                                jmessage.getJSONObject(i).getJSONObject("content").get("text").toString(),
                                user));
                        Rooms.current_room.setMessages(messages);
                        smessage.onCreatedRoom();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sendMessage(String id, SendMessages sendMessages, String message) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        JSONObject jtext = new JSONObject();
        try {
            jtext.put("text", message);
            json.put("content", jtext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, json.toString());

        Request request = new Request.Builder()
                .url(BASE_URL + "chat/rooms/" + id +"/messages")
                .method("POST", body)
                .header("token", Users.currentUser.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getMessages(sendMessages, Rooms.current_room.id);
                //sendMessages.onCreatedRoom();
            }
        });
    }
}
