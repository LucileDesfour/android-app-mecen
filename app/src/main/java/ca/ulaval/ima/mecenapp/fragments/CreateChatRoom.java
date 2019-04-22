package ca.ulaval.ima.mecenapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.adapters.UsersAdapter;
import ca.ulaval.ima.mecenapp.models.Orgas;
import ca.ulaval.ima.mecenapp.models.Users;
import ca.ulaval.ima.mecenapp.models.network.OrgaNetwork;

public class CreateChatRoom extends Fragment {

    private CreateRoomsListener mListener;
    private RecyclerView selectionRecyclerView;
    private RecyclerView selectedRecyclerView;
    private UsersAdapter selectionAdapter;
    private UsersAdapter selectedAdapter;
    private RecyclerView.LayoutManager selectionLayoutManager;
    private RecyclerView.LayoutManager selectedLayoutManager;
    private ArrayAdapter<String> orgaArrayAdapter;
    private Spinner orgaSpinner;
    private Button submitBtn;

    private TextView selectOrga;
    private TextView selectionMembers;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.fragment_create_chat_room, container, false);
        submitBtn = v.findViewById(R.id.submit_button);
        selectOrga = v.findViewById(R.id.select_orga);
        selectionMembers = v.findViewById(R.id.selection_members);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRoom();
            }
        });

        orgaSpinner = v.findViewById(R.id.orga_spinner);
        orgaArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, Orgas.orgas_names);
        orgaSpinner.setAdapter(orgaArrayAdapter);
        orgaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clearSelection();
                selectionMembers.setText(R.string.loading);
                runGetMembers(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.selectionRecyclerView = v.findViewById(R.id.selection_recycler_view);
        this.selectionLayoutManager = new LinearLayoutManager(getActivity());
        this.selectionRecyclerView.setLayoutManager(this.selectionLayoutManager);
        this.selectionAdapter = new UsersAdapter(getContext(), this,"Selection");
        this.selectionRecyclerView.setAdapter(this.selectionAdapter);

        this.selectedRecyclerView = v.findViewById(R.id.selected_recycler_view);
        this.selectedLayoutManager = new LinearLayoutManager(getActivity());
        this.selectedRecyclerView.setLayoutManager(this.selectedLayoutManager);
        this.selectedAdapter = new UsersAdapter(getContext(), this, "Selected");
        this.selectedRecyclerView.setAdapter(this.selectedAdapter);

        selectOrga.setText(R.string.loading);
        OrgaNetwork.getOrgas(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateRoomsListener ) {
            mListener = (CreateRoomsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CreateRoomsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Orgas.orgas_list.clear();
        Orgas.orgas_names.clear();
        setupSpinnerAdapter();
    }

    public interface CreateRoomsListener{
        void onCreateRoom(ArrayList<Users.User> users, CreateChatRoom createChatRoom);
    }

    public void setupSpinnerAdapter(){
        orgaArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, Orgas.orgas_names);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectOrga.setText(R.string.select_orga);
                orgaArrayAdapter.notifyDataSetChanged();
                orgaSpinner.setAdapter(orgaArrayAdapter);
            }
        });
    }

    public void updateSelection(){
        selectionAdapter.setItems(Users.users_list);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectionMembers.setText(R.string.selection);
                selectionAdapter.notifyDataSetChanged();
            }
        });
    }

    public void clearSelection(){
        Users.users_list.clear();
        selectionAdapter.setItems(Users.users_list);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectionAdapter.notifyDataSetChanged();
            }
        });
    }

    public void updateSelected(String tag, Users.User user){
        switch (tag){
            case "del":
                selectedAdapter.delItem(user);
                break;
            case "add":
                selectedAdapter.addItem(user);
                break;
            default:
                return;
        }
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectedAdapter.notifyDataSetChanged();
            }
        });
    }

    public void runGetMembers(int i){
        OrgaNetwork.getOrgaMembers(Orgas.orgas_list.get(i).getId(),this);
    }

    public void createRoom(){
        mListener.onCreateRoom(selectedAdapter.getItems(), this);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                submitBtn.setEnabled(false);
                submitBtn.setText(R.string.creation);
                submitBtn.setTextColor(Color.parseColor("#000000"));
                submitBtn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_grey_border_item));
            }
        });
    }

    public void onCreatedRoom(){
        //do something (go backs to the list or open the new room)
        //for now his just update the UI
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                submitBtn.setEnabled(true);
                submitBtn.setText(R.string.create_room);
                submitBtn.setTextColor(Color.parseColor("#FFFFFF"));
                submitBtn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.create_room_button));
            }
        });
    }


}
