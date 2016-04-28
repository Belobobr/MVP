package com.mixailsednev.githubrepo.mvptabletphone.filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mixailsednev.githubrepo.mvptabletphone.R;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.CaseTypes;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterBuilder;
import com.mixailsednev.githubrepo.mvptabletphone.utils.TextWatcherAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String ARG_FILTER = "ARG_FILTER";

    private static final String FILTER_STATE = "FILTER_STATE";

    private ArrayAdapter<String> caseTypesAdapter;
    private Spinner casesTypesSpinner;
    private EditText assignedEditText;
    private Filter selectedFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null && savedInstanceState.getSerializable(FILTER_STATE) != null) {
            selectedFilter = (Filter)savedInstanceState.getSerializable(FILTER_STATE);
        } else {
            selectedFilter = getIntent().getSerializableExtra(ARG_FILTER) != null ?
                    (Filter)getIntent().getSerializableExtra(ARG_FILTER)
                    : new FilterBuilder().createFilter();
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
            intent.putExtra(ARG_FILTER, selectedFilter);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FILTER_STATE, selectedFilter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        caseTypesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(Arrays.asList(CaseTypes.COMMON, CaseTypes.ARBITR)));

        casesTypesSpinner = ((Spinner)findViewById(R.id.caseTypeSpinner));
        casesTypesSpinner.setAdapter(caseTypesAdapter);
        casesTypesSpinner.setOnItemSelectedListener(this);

        assignedEditText = ((EditText)findViewById(R.id.assignedEditText));
        assignedEditText.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selectedFilter.setAssigned(s.toString());
            }
        });

        casesTypesSpinner.setSelection(caseTypesAdapter.getPosition(selectedFilter.getCaseType()));
        assignedEditText.setText(selectedFilter.getAssigned());
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCaseType = caseTypesAdapter.getItem(position);
        selectedFilter.setCaseType(selectedCaseType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
