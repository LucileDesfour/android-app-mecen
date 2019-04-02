package ca.ulaval.ima.mecenapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.Projects;
import ca.ulaval.ima.mecenapp.models.network.ProjectNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowProject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowProject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowProject extends Fragment {


    //private OnFragmentInteractionListener mListener;
    View view;
    Projects.Project projet;

    public ShowProject() {
        // Required empty public constructor
    }


    public static ShowProject newInstance(String projetid) {
        ShowProject fragment = new ShowProject();
        Bundle args = new Bundle();
        args.putString("PROJET", projetid);
        fragment.setArguments(args);
        return fragment;
    }

    public void ChangeData() {
        TextView name = view.findViewById(R.id.project_name);
        name.setText(Projects.currentProject.getName());
        TextView orga = view.findViewById(R.id.project_orga);
        orga.setText(Projects.currentProject.getOrga().getName());
        TextView description = view.findViewById(R.id.project_description);
        description.setText(Projects.currentProject.getDescription());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ProjectNetwork.getProject(this, getArguments().getString("PROJET"));
            projet = Projects.currentProject;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_project, container, false);
        this.view = v;
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
