package com.mixailsednev.githubrepo.mvptabletphone.filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mixailsednev.githubrepo.mvptabletphone.R;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.CaseTypes;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterBuilder;

public class FilterActivity extends AppCompatActivity {

    public static final String ARG_FILTER = "ARG_FILTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.complete) {
            Intent intent = new Intent();
            intent.putExtra(
                    ARG_FILTER,
                    new FilterBuilder()
                            .setCaseType(CaseTypes.ARBITR)
                            .setDate(100l)
                            .createFilter()
            );
            setResult(RESULT_OK, intent);
            finish();
            return true;
        } else if (id == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
