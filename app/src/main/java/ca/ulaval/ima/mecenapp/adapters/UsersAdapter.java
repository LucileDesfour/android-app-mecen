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
import ca.ulaval.ima.mecenapp.fragments.CreateChatRoom;
import ca.ulaval.ima.mecenapp.models.Users;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private String TAG;

    CreateChatRoom fragContext = null;
    Context mContext;
    ArrayList<Users.User> mUsers = new ArrayList<>();

    public UsersAdapter (Context mContext, CreateChatRoom fragContext, String tag){
        this.mContext = mContext;
        this.fragContext = fragContext;
        this.TAG = tag;
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout item;
        private TextView user_info;
        private TextView user_action;
        private Users.User user;
        public UsersViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.user_item);
            user_info = itemView.findViewById(R.id.user_info);
            user_action = itemView.findViewById(R.id.user_action);
            if (TAG.equals("Selection")){
                item.setOnClickListener(view -> fragContext.updateSelected("add",user));
                user_action.setText(R.string.add_user);
            } else {
                item.setOnClickListener(view -> fragContext.updateSelected("del",user));
                user_action.setText(R.string.del_user);
            }
        }
    }

    @NonNull
    @Override
    public UsersAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        final  UsersViewHolder viewHolder = new UsersViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        String info =mUsers.get(position).getFirstName()+" "+mUsers.get(position).getLastName();
        holder.user_info.setText(info);
        holder.user = mUsers.get(position);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public ArrayList<Users.User> getItems(){
        return mUsers;
    }

    public void setItems(ArrayList<Users.User> users){
        this.mUsers = users;
    }

    public void addItem(Users.User user){
        for (int i = 0; i< mUsers.size(); i++){
            if (mUsers.get(i).getId().equals(user.getId())) return;
        }
        this.mUsers.add(user);
    }

    public void delItem(Users.User user){
        this.mUsers.remove(user);
    }
}
