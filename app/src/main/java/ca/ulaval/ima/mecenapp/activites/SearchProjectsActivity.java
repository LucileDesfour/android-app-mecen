package ca.ulaval.ima.mecenapp.activites;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.ulaval.ima.mecenapp.Connexion;
import ca.ulaval.ima.mecenapp.R;

public class SearchProjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connexion connexion=new Connexion();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,connexion).commit();


    }


}

