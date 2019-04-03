package ca.ulaval.ima.mecenapp.activites;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.fragments.SearchProject;
import ca.ulaval.ima.mecenapp.fragments.ShowProject;
import ca.ulaval.ima.mecenapp.models.Projects;
import ca.ulaval.ima.mecenapp.models.network.ProjectNetwork;

public class ProjectsActivity extends FragmentActivity implements SearchProject.OnProjectInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchProject fragment_search = new SearchProject();
        fragmentTransaction.add(R.id.container, fragment_search);
        fragmentTransaction.commit();
    }

    public void OnProjectInteractionListener(Projects.Project projet) {
        Log.d("Activity", projet.getName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShowProject show_project = new ShowProject();
        Bundle bundle = new Bundle();
        bundle.putString("PROJECT", projet.getId());
        show_project.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, show_project);
        fragmentTransaction.commit();
    }
}

