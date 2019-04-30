package ca.ulaval.ima.mecenapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.fragments.SendMessages;

public class MessageListActivity extends AppCompatActivity implements SendMessages.OnMessageInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        if (intent.getStringExtra("manager_id") != null) {
            String Id = intent.getStringExtra("manager_id");
            bundle.putString("manager_id", Id);
        } else if (intent.getStringExtra("room_id") != null) {
            String Id = intent.getStringExtra("room_id");
            bundle.putString("room_id", Id);
        }
        SendMessages fragment_send = new SendMessages();
        fragment_send.setArguments(bundle);
        fragmentTransaction.add(R.id.container, fragment_send, "SEND");
        fragmentTransaction.commit();
    }

    public void SetConversationName(String name) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().setTitle(name);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
