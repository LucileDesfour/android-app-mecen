package ca.ulaval.ima.mecenapp.activites;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.fragments.ChatRooms;
import ca.ulaval.ima.mecenapp.fragments.CreateChatRoom;
import ca.ulaval.ima.mecenapp.models.Users;
import ca.ulaval.ima.mecenapp.models.network.RoomsNetwork;

public class ChatActivity extends AppCompatActivity implements ChatRooms.ChatRoomsListener, CreateChatRoom.CreateRoomsListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setLogo(getResources().getDrawable(R.drawable.mcp_name_circle));
        setSupportActionBar(myToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChatRooms fragment_chatrooms = new ChatRooms();
        fragmentTransaction.add(R.id.container, fragment_chatrooms, "CHATROOMS");
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelect() {
        //launche the chat fragment
    }

    @Override
    public void onBeginCreateRoom() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateChatRoom fragment_createChatRoom = new CreateChatRoom();
        fragmentTransaction.replace(R.id.container, fragment_createChatRoom, "CREATECHATROOMS");
        fragmentTransaction.addToBackStack("CHATROOMS");
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateRoom(ArrayList<Users.User> users) {
        RoomsNetwork.postRooms(users);
    }
}
