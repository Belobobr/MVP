package com.mixailsednev.githubrepo.mvptabletphone;

import com.mixailsednev.githubrepo.mvptabletphone.FilteredCases.FilteredCasesPresenter;
import com.mixailsednev.githubrepo.mvptabletphone.FilteredCases.FilteredCasesView;
import com.mixailsednev.githubrepo.mvptabletphone.model.DataChangeListener;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.CaseBuilder;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterBuilder;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterState;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesState;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.LoadCasesAction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

public class FilteredCasesPresenterTest {

    private static List<Case> filteredCases = new ArrayList<Case>(Arrays.asList(new CaseBuilder().setTitle("Пам пам").createCase(), new CaseBuilder().setTitle("Пам пам").createCase()));
    private static FilteredCasesState FILTERED_CASES_STATE =  new FilteredCasesState(filteredCases, true);
    private static FilterState FILTER_CASES_STATE = new FilterState();
    private static Filter SAVED_FILTER = new FilterBuilder().createFilter();

    @Mock
    private FilteredCasesStore filteredCasesStore;

    @Mock
    private FilterStore filterStore;

    @Mock
    private FilteredCasesView filteredCasesView;

    @Mock
    private LoadCasesAction filteredCasesAction;

    @Captor
    private ArgumentCaptor<DataChangeListener<FilteredCasesState>> filteredCasesStateUpdatedCallbackCaptor;

    @Captor ArgumentCaptor<DataChangeListener<FilterState>> filterStateUpdatedCallbackCaptor;

    private FilteredCasesPresenter filteredCasesPresenter;

    @Before
    public void setupFilteredCasesPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        filteredCasesPresenter = new FilteredCasesPresenter(filteredCasesView, filteredCasesStore, filterStore, filteredCasesAction);
    }

    @Test
    public void subscribeToDataStore() {
        // Given an initialized NotesPresenter with initialized notes
        // When loading of Notes is requested
        filteredCasesPresenter.subscribeToDataStore();

        verify(filteredCasesStore).addListener(filteredCasesStateUpdatedCallbackCaptor.capture());
        filteredCasesStateUpdatedCallbackCaptor.getValue().newDataReceived(FILTERED_CASES_STATE);

        verify(filteredCasesView).setCases(filteredCases);
        verify(filteredCasesView).setLoading(true);

        verify(filterStore).addListener(filterStateUpdatedCallbackCaptor.capture());
        filterStateUpdatedCallbackCaptor.getValue().newDataReceived(FILTER_CASES_STATE);

        verify(filteredCasesView).setFilter(FILTER_CASES_STATE.getFilter());
    }

    @Test
    public void  loadCases() {
        filteredCasesPresenter.loadCases(null);

        verify(filteredCasesAction).run(null);
    }

    @Test
    public void selectFilter() {
        filteredCasesPresenter.selectFilter();

        verify(filteredCasesView).selectFilter(null);
    }

    @Test
    public void saveFilter() {
        filteredCasesPresenter.saveFilter(SAVED_FILTER);

        verify(filterStore).updateFilter(SAVED_FILTER);
    }
}
