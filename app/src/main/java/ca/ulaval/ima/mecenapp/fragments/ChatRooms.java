package ca.ulaval.ima.mecenapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.adapters.ChatRoomAdapter;
import ca.ulaval.ima.mecenapp.models.Rooms;
import ca.ulaval.ima.mecenapp.models.network.RoomsNetwork;

public class ChatRooms extends Fragment {

    private ChatRoomsListener mListener;
    private RecyclerView recyclerView;
    private ChatRoomAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView title;

    public ChatRooms() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_chat_rooms, container, false);

        title = v.findViewById(R.id.chatrooms_title);

        FloatingActionButton newRoom = v.findViewById(R.id.newRoom);
        newRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBeginCreateRoom();
            }
        });

        this.recyclerView = v.findViewById(R.id.rooms_recycler_view);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.mAdapter = new ChatRoomAdapter(getContext(), Rooms.rooms);
        this.recyclerView.setAdapter(this.mAdapter);

        String newTitle = getContext().getString(R.string.my_chats) + " [Chargement]";
        title.setText(newTitle);
        RoomsNetwork.getRooms(this);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChatRoomsListener ) {
            mListener = (ChatRoomsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ChatRoomsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Rooms.rooms.clear();
        updateAdapter();
    }

    public void updateAdapter(){
        mAdapter.setItems(Rooms.rooms);
        String newTitle = getContext().getString(R.string.my_chats) + " ["+ Rooms.rooms.size() +"]";
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(newTitle);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public interface ChatRoomsListener {
        void onItemSelect();
        void onBeginCreateRoom();
    }


}
