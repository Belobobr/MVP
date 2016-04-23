package com.mixailsednev.githubrepo.mvptabletphone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mixailsednev.githubrepo.mvptabletphone.FilteredCases.FilteredCasesFragment;

public class CasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FilteredCasesFragment fragment = FilteredCasesFragment.createInstance();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }
    }
}
