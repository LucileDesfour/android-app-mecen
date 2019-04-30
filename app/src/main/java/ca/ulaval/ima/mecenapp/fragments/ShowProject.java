package ca.ulaval.ima.mecenapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.Projects;
import ca.ulaval.ima.mecenapp.models.network.ProjectNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ShowProject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowProject extends Fragment {


    //private OnFragmentInteractionListener mListener;
    View view;
    Projects.Project projet;
    private OnShowProjectInteractionListener mListener;


    public ShowProject() {
        // Required empty public constructor
    }


    public static ShowProject newInstance(String projetid) {
        ShowProject fragment = new ShowProject();
        Bundle args = new Bundle();
        args.putString("PROJECT", projetid);
        fragment.setArguments(args);
        return fragment;
    }

    public void ChangeData() {
        this.projet = Projects.currentProject;
        ArrayList<TextView> domains = new ArrayList<>();
        for (int idx_domains = 0; idx_domains < Projects.currentProject.getDomaine().size(); idx_domains++) {
            domains.add(new TextView(this.getContext()));
            domains.get(idx_domains).setText(Projects.currentProject.getDomaine().get(idx_domains));
        }

        ArrayList<TextView> ressources = new ArrayList<>();
        for (int idx_ressources = 0; idx_ressources < Projects.currentProject.getRessources().size(); idx_ressources++) {
            ressources.add(new TextView(this.getContext()));
            ressources.get(idx_ressources).setText(Projects.currentProject.getRessources().get(idx_ressources));
        }

        this.getActivity().runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 TextView name = view.findViewById(R.id.project_name);
                 name.setText(Projects.currentProject.getName());
                 if (Projects.currentProject.getOrga() != null) {
                     TextView orga = view.findViewById(R.id.project_orga);
                     orga.setText(Projects.currentProject.getOrga().getName());
                 }
                 TextView description = view.findViewById(R.id.project_description);
                 description.setText(Projects.currentProject.getDescription());
                 LinearLayout linearLayoutdomain = view.findViewById(R.id.project_domains);
                 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                         LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                 layoutParams.setMargins(16, 16, 16, 0);


                 int idx_color = 0;

                 for (int idx_domains = 0; idx_domains < Projects.currentProject.getDomaine().size(); idx_domains++) {

                     ArrayList<Integer> color = getColor(idx_color);
                     GradientDrawable gd = new GradientDrawable(
                             GradientDrawable.Orientation.TOP_BOTTOM,
                             new int[] {color.get(0), color.get(0), color.get(1)});
                     gd.setCornerRadius(30);
                     domains.get(idx_domains).setBackground(gd);
                     domains.get(idx_domains).setTextColor(Color.WHITE);
                     domains.get(idx_domains).setPadding(16, 16 , 16,16);
                     linearLayoutdomain.addView(domains.get(idx_domains), layoutParams);
                     if (idx_color < 3) {
                         idx_color++;
                     } else {
                         idx_color = 0;
                     }
                 }
                 idx_color = 0;
                 LinearLayout linearLayoutressources = view.findViewById(R.id.project_resources);
                 for (int idx_ressources = 0; idx_ressources < Projects.currentProject.getRessources().size(); idx_ressources++) {
                     ArrayList<Integer> color = getColor(idx_color);
                     GradientDrawable gd = new GradientDrawable(
                             GradientDrawable.Orientation.TOP_BOTTOM,
                             new int[] {color.get(0), color.get(0), color.get(1)});
                     gd.setCornerRadius(30);
                     ressources.get(idx_ressources).setBackground(gd);
                     ressources.get(idx_ressources).setTextColor(Color.WHITE);
                     ressources.get(idx_ressources).setPadding(16, 16 , 16,16);
                     linearLayoutressources.addView(ressources.get(idx_ressources), layoutParams);
                     if (idx_color < 3) {
                         idx_color++;
                     } else {
                         idx_color = 0;
                     }
                 }
             }
         });
    }

    public ArrayList<Integer> getColor(int idx) {
        ArrayList<Integer> color = new ArrayList<>();
        if (idx == 0) {
            color.add(getResources().getColor(R.color.colorAccent));
            color.add(getResources().getColor(R.color.colorAccentLight));
        }
        else if (idx == 1) {
            color.add(getResources().getColor(R.color.colorPrimary));
            color.add(getResources().getColor(R.color.colorPrimaryLight));
        }
        else  {
            color.add(getResources().getColor(R.color.secondaryColor));
            color.add(getResources().getColor(R.color.secondaryColorLight));
        }
        return color;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ProjectNetwork.getProject(this, getArguments().getString("PROJECT"));
            projet = Projects.currentProject;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_project, container, false);
        this.view = v;
        FloatingActionButton fab = view.findViewById(R.id.create_conversation_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manager_id = Projects.currentProject.getManagerId();
                mListener.StartMessagesActivity(manager_id);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnShowProjectInteractionListener) {
            mListener = (OnShowProjectInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnShowProjectInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public interface OnShowProjectInteractionListener {
        // TODO: Update argument type and name
        void StartMessagesActivity(String managerid);
    }
}
