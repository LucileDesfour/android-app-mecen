package ca.ulaval.ima.mecenapp.adapters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.fragments.SearchProject;
import ca.ulaval.ima.mecenapp.models.Projects;

import static android.os.Build.VERSION_CODES.O;

public class ProjectAdapter  extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private ArrayList<Projects.Project> projects;
    private SearchProject.OnProjectInteractionListener mListener;

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final TextView project_name;
        private final TextView project_orga;
        public final View mView;
        private SearchProject.OnProjectInteractionListener listener;


        // each data item is just a string in this case
        public View project;
        public ProjectViewHolder(View v) {
            super(v);
            mView = v;
            this.project_name = v.findViewById(R.id.project_name);
            this.project_orga = v.findViewById(R.id.project_orga);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProjectAdapter(ArrayList<Projects.Project> projects_list, SearchProject.OnProjectInteractionListener listener) {
        this.projects = projects_list;
        this.mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_card, parent, false);
        ProjectViewHolder vh = new ProjectViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Projects.Project proj = projects.get(position);
        holder.project_name.setText(proj.getName());
        holder.project_orga.setText(proj.getDescription());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnProjectInteractionListener(projects.get(position));
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setItems(ArrayList<Projects.Project> proj) {
        this.projects = proj;
    }
}
