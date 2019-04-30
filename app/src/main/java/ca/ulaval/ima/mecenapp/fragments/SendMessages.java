package ca.ulaval.ima.mecenapp.fragments;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.adapters.MessageListAdapter;
import ca.ulaval.ima.mecenapp.models.Messages;
import ca.ulaval.ima.mecenapp.models.Rooms;
import ca.ulaval.ima.mecenapp.models.Users;
import ca.ulaval.ima.mecenapp.models.network.MessageNetwork;
import ca.ulaval.ima.mecenapp.models.network.RoomsNetwork;
import ca.ulaval.ima.mecenapp.models.network.UserNetwork;

import static android.app.Activity.RESULT_OK;

public class SendMessages extends Fragment {
    private OnMessageInteractionListener mListener;

    private RecyclerView recyclerView;
    private MessageListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText message;
    int REQUEST_CODE = 1;

    public SendMessages() {
        // Required empty public constructor
    }

    public static SearchProject newInstance() {
        SearchProject fragment = new SearchProject();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_send_messages, container, false);



        this.recyclerView = view.findViewById(R.id.recyclerview_message_list);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.mAdapter = new MessageListAdapter(this.getContext(), Messages.messages_list);
        recyclerView.setAdapter(this.mAdapter);
        Intent intent = getActivity().getIntent();

        if (intent.getStringExtra("room_id") != null) {
            RoomsNetwork.getRoom(intent.getStringExtra("room_id"), this);

        } else if (intent.getStringExtra("manager_id") != null) {
            ArrayList<Users.User> users = new ArrayList<>();
            UserNetwork.getUser(intent.getStringExtra("manager_id"), this);
        }
        EditText edittext_chatbox = view.findViewById(R.id.edittext_chatbox);
        Button button = view.findViewById(R.id.button_chatbox_send);
        Button button_micro = view.findViewById(R.id.button_micro);
        SendMessages sendMessages = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageNetwork.sendMessage(Rooms.current_room.id, sendMessages, edittext_chatbox.getText().toString());
                InputMethodManager imm = (InputMethodManager) sendMessages.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                edittext_chatbox.setText("");
            }
        });
        this.message = edittext_chatbox;

        button_micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String DIALOG_TEXT = "Dictez votre text";
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, DIALOG_TEXT);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, REQUEST_CODE);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");
                    startActivityForResult(intent, REQUEST_CODE);
                } catch(ActivityNotFoundException e)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://market.android.com/details?id=com.google.android.googlequicksearchbox"));
                    startActivity(browserIntent);

                }
            }
        });

        //MessageNetwork.getMessages(String room_id, this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            this.message.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SendMessages.OnMessageInteractionListener) {
            mListener = (SendMessages.OnMessageInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMessageInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onCreatedRoom() {
        //MessageNetwork.getMessages(this, Rooms.current_room.id);
        mAdapter.setItems(Rooms.current_room.getMessages());
        CreateConversationName();
        Activity activity = getActivity();
        if (isAdded() && activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }


    }

    public void CreateConversationName() {
        ArrayList<Users.User> members =  Rooms.current_room.members;
        String convName = "";
        for (int i = 0; i < members.size(); i++) {
            if (!members.get(i).getId().equals(Users.currentUser.getId())) {
                convName += members.get(i).getFirstName() + " ";
            }
        }
        if (convName != "" && mListener != null) {
            mListener.SetConversationName(convName);
        }
    }

    public interface OnMessageInteractionListener {
        void SetConversationName(String name);
    }
}
