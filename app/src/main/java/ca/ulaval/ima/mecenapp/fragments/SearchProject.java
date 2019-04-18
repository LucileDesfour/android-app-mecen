package ca.ulaval.ima.mecenapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import android.app.SearchManager;
import android.widget.SearchView;
import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.adapters.ProjectAdapter;
import ca.ulaval.ima.mecenapp.models.Projects;
import ca.ulaval.ima.mecenapp.models.network.ProjectNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchProject.OnProjectInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchProject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchProject extends Fragment {


    private OnProjectInteractionListener mListener;
    private RecyclerView recyclerView;
    private ProjectAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public SearchProject() {
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
        setupSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search_project, container, false);
        this.recyclerView = view.findViewById(R.id.recycler_view);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.mAdapter = new ProjectAdapter(Projects.project_list, this.mListener);
        recyclerView.setAdapter(this.mAdapter);
        ProjectNetwork.getProjects(this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProjectInteractionListener) {
            mListener = (OnProjectInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProjectInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void setupSearch() {
        SearchView searchView = getView().findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processQuery(newText);
                return false;
            }
        });

    }

    private void processQuery(String query) {
        // in real app you'd have it instantiated just once
        ArrayList<Projects.Project> result = new ArrayList<>();

        // case insensitive search
        for (Projects.Project project : Projects.project_list) {
            if (project.getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(project);
                Log.d("Result", project.getName());
            }
        }
        Log.d("Query", query);
        mAdapter.setItems(result);
        mAdapter.notifyDataSetChanged();
    }
    public void ChangeData() {
        Log.d("Projects", String.valueOf(Projects.project_list.size()));
        mAdapter.setItems(Projects.project_list);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    public interface OnProjectInteractionListener {
        void OnProjectInteractionListener(Projects.Project projet);
    }
}
