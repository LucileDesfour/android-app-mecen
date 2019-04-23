package ca.ulaval.ima.mecenapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.fragments.ChatRooms;
import ca.ulaval.ima.mecenapp.models.Rooms;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomsViewHolder> {

    private ChatRooms.ChatRoomsListener listener;
    private Context mContext;
    private ArrayList<Rooms.Room> mRooms;

    public ChatRoomAdapter(Context mContext, ArrayList<Rooms.Room> rooms) {
        this.listener = (ChatRooms.ChatRoomsListener) mContext;
        this.mContext = mContext;
        this.mRooms = rooms;
    }

    static class ChatRoomsViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout item;
        private TextView room_name;

        ChatRoomsViewHolder(View itemView){
            super(itemView);
            item = itemView.findViewById(R.id.room_item);
            room_name = itemView.findViewById(R.id.room_name);
        }
    }

    @NonNull
    @Override
    public ChatRoomAdapter.ChatRoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.room_item, parent, false);
        final ChatRoomsViewHolder vHolder = new ChatRoomsViewHolder(v);
        vHolder.item.setOnClickListener(view -> listener.onItemSelect());
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomsViewHolder holder, int position) {
        holder.room_name.setText(mRooms.get(position).getMembersInitials());
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public void setItems(ArrayList<Rooms.Room> rooms){
        this.mRooms = rooms;
    }

}
