package com.mixailsednev.githubrepo.mvptabletphone.cases;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.R;

import java.util.List;

public class CasesFragment extends Fragment implements CasesView{

    ListView mCasesListView;

    CasesAdapter mCasesAdapter;
    View mProgressLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCasesAdapter = new CasesAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_cases, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCasesListView = (ListView)view.findViewById(R.id.casesListView);
        mProgressLayout = view.findViewById(R.id.progressLayout);
        mCasesListView.setAdapter(mCasesAdapter);
    }

    @Override
    public void setCases(@NonNull List<Case> cases) {
        mCasesAdapter.clear();
        mCasesAdapter.addAll(cases);
    }

    @Override
    public void setLoading(boolean loading) {
        Log.i("CasesFragment", "Loading: " + loading);
        mProgressLayout.setVisibility(loading ? View.VISIBLE : View.GONE);
    }
}
