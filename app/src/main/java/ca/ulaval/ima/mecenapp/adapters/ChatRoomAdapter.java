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

    public ChatRoomAdapter(Context mContext, ArrayList<Rooms.Room> rooms, ChatRooms.ChatRoomsListener mlistner) {
        this.listener = mlistner;
        this.mContext = mContext;
        this.mRooms = rooms;
    }

    static class ChatRoomsViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout item;
        private TextView room_name;
        public final View mView;

        ChatRoomsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            item = itemView.findViewById(R.id.room_item);
            room_name = itemView.findViewById(R.id.room_name);
        }
    }

    @NonNull
    @Override
    public ChatRoomAdapter.ChatRoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.room_item, parent, false);
        final ChatRoomsViewHolder vHolder = new ChatRoomsViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomsViewHolder holder, int position) {
        holder.room_name.setText(mRooms.get(position).getMembersInitials());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener && mRooms.size() > 0 && mRooms.get(position) != null) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onItemSelect(mRooms.get(position).id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public void setItems(ArrayList<Rooms.Room> rooms){
        this.mRooms = rooms;
    }

}
