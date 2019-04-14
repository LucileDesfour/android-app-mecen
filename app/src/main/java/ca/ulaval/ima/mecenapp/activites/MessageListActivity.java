package ca.ulaval.ima.mecenapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sendbird.android.BaseChannel;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.MessageListAdapter;
import ca.ulaval.ima.mecenapp.models.Users;

import static android.provider.UserDictionary.Words.APP_ID;

public class MessageListActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        SendBird.init(APP_ID, this);

        /*SendBird.connect(USER_ID, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {

            }
        });

        OpenChannel.createChannel(new OpenChannel.OpenChannelCreateHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });

        OpenChannel.getChannel(CHANNEL_URL, new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }

                openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        if (e != null) {    // Error.
                            return;
                        }
                    }
                });
            }
        });

        channel.sendUserMessage(MESSAGE, new BaseChannel.SendUserMessageHandler() {
            @Override
            public void onSent(UserMessage userMessage, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });*/
        mMessageRecycler = (RecyclerView) findViewById(R.id.recyclerview_message_list);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
