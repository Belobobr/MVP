package com.mixailsednev.githubrepo.mvptabletphone.FilteredCases;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mixailsednev.githubrepo.mvptabletphone.Injection;
import com.mixailsednev.githubrepo.mvptabletphone.common.BaseFragment;
import com.mixailsednev.githubrepo.mvptabletphone.cases.CasesView;
import com.mixailsednev.githubrepo.mvptabletphone.filter.PhoneFilterView;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.filter.FilterActivity;
import com.mixailsednev.githubrepo.mvptabletphone.filter.FilterSelectedCallback;
import com.mixailsednev.githubrepo.mvptabletphone.R;
import com.mixailsednev.githubrepo.mvptabletphone.filter.TabletFilterView;

import java.util.List;

public class FilteredCasesFragment extends BaseFragment<FilteredCasesPresenter> implements FilterSelectedCallback, FilteredCasesView {

    public static int FILTER_REQUEST = 0;

    @Nullable private TabletFilterView tabletFilterView;
    @Nullable private PhoneFilterView phoneFilterView;
    @Nullable private CasesView casesView;

    public static FilteredCasesFragment createInstance() {
        return new FilteredCasesFragment();
    }

    @Override
    public FilteredCasesPresenter createPresenter() {
        return  new FilteredCasesPresenter(this, Injection.provideFilteredCasesStore(), Injection.provideFilterStore(), Injection.provideActionCreator().createLoadFilteredCasesAction());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_filtered_cases, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabletFilterView = (TabletFilterView)view.findViewById(R.id.tabletFilterView);
        phoneFilterView = (PhoneFilterView)view.findViewById(R.id.phoneFilterView);

        if (tabletFilterView != null) {
            tabletFilterView.setFilterSelectedCallback(this);
        }

        casesView = (CasesView)getChildFragmentManager().findFragmentById(R.id.casesView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_filtered_cases, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter) {
            presenter.selectFilter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectFilter() {
        Intent filterIntent = new Intent(getContext(), FilterActivity.class);
        startActivityForResult(filterIntent, FILTER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILTER_REQUEST && resultCode == Activity.RESULT_OK) {
            Filter filter = (Filter)data.getSerializableExtra(FilterActivity.ARG_FILTER);

            onFilterSelected(filter);

            if (phoneFilterView != null) {
                phoneFilterView.setFilter(filter);
            }
        }
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.loadCases(null);
    }

    @Override
    public void onFilterSelected(@Nullable  Filter filter) {
        presenter.saveFilter(filter);
        presenter.loadCases(filter);

    }

    @Override
    public void setCases(@NonNull List<Case> cases) {
        if (casesView != null) {
            casesView.setCases(cases);
        }
    }

    @Override
    public void setFilter(@Nullable Filter filter) {
        if (phoneFilterView != null) {
            phoneFilterView.setFilter(filter);
        } else if (tabletFilterView != null) {
            tabletFilterView.setFilter(filter);
        }
    }

    @Override
    public void setLoading(boolean loading) {
        Log.i("FilteredCasesFragment", "FilteredCasesFragment: loading " + loading + " casesView: " + casesView);
        if (casesView != null) {
            casesView.setLoading(loading);
        }
    }
}
