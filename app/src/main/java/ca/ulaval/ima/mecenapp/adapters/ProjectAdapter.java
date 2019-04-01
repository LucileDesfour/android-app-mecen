package ca.ulaval.ima.mecenapp.adapters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.Projects;

public class ProjectAdapter  extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private ArrayList<Projects.Project> projects;

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final TextView project_name;
        private final TextView project_orga;
        // each data item is just a string in this case
        public View project;
        public ProjectViewHolder(View v) {
            super(v);
            this.project_name = v.findViewById(R.id.project_name);
            this.project_orga = v.findViewById(R.id.project_orga);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProjectAdapter(ArrayList<Projects.Project> projects_list) {
        this.projects = projects_list;
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

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("size -> ", String.valueOf(projects.size()));
        return projects.size();
    }

    public void setItems(ArrayList<Projects.Project> proj) {
        this.projects = proj;
        Log.d("size :", String.valueOf(proj.size()));
        for (int idx = 0; idx < proj.size(); idx++) {
            Log.d("proj -> ", proj.get(idx).getName());
            Log.d("idx :", String.valueOf(idx));
        }
    }
}
