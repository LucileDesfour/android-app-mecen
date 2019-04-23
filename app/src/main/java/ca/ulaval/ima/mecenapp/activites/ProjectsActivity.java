package ca.ulaval.ima.mecenapp.activites;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.fragments.Login;
import ca.ulaval.ima.mecenapp.fragments.SearchProject;
import ca.ulaval.ima.mecenapp.fragments.ShowProject;
import ca.ulaval.ima.mecenapp.models.Projects;
import ca.ulaval.ima.mecenapp.models.network.ProjectNetwork;
import android.app.SearchManager;
import android.widget.SearchView;
public class ProjectsActivity extends AppCompatActivity implements SearchProject.OnProjectInteractionListener, Login.OnLoginInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setLogo(getResources().getDrawable(R.drawable.mcp_name_circle));
        setSupportActionBar(myToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if (sharedPref.contains("token")) {
            Log.e("Token :", sharedPref.getString("token", null));
            SearchProject fragment_search = new SearchProject();
            fragmentTransaction.add(R.id.container, fragment_search, "SEARCH");
            fragmentTransaction.commit();
        } else {
            Login login_fragment = new Login();
            fragmentTransaction.add(R.id.container, login_fragment, "LOGIN");
            fragmentTransaction.commit();
        }
    }

    public void OnProjectInteractionListener(Projects.Project projet) {
        Log.d("Activity", projet.getName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShowProject show_project = new ShowProject();
        Bundle bundle = new Bundle();
        bundle.putString("PROJECT", projet.getId());
        show_project.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, show_project, "PROJECT");
        fragmentTransaction.addToBackStack("SEARCH");
        fragmentTransaction.commit();
    }

    @Override
    public void OnLoginInteractionListener() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchProject fragment_search = new SearchProject();
        fragmentTransaction.replace(R.id.container, fragment_search, "SEARCH");
        fragmentTransaction.commit();
    }
}

