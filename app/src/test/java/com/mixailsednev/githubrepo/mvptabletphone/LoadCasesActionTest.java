package com.mixailsednev.githubrepo.mvptabletphone;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterBuilder;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.CasesServiceApi;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.LoadCasesAction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class LoadCasesActionTest {

    private static Filter FILTER = new FilterBuilder().createFilter();
    private static List<Case> LOADED_CASES = new ArrayList<Case>();

    @Mock
    FilteredCasesStore filteredCasesStore;

    @Mock
    CasesServiceApi casesServiceApi;

    @Captor
    ArgumentCaptor<CasesServiceApi.CasesLoadedCallback> casesLoadedCallbackArgumentCaptor;

    private LoadCasesAction loadCasesAction;

    @Before
    public void setupLoadCasesAction() {
        MockitoAnnotations.initMocks(this);

        this.loadCasesAction =  new LoadCasesAction(filteredCasesStore, casesServiceApi);
    }

    @Test
    public void testRun() {
        loadCasesAction.run(FILTER);

        verify(filteredCasesStore).setLoading(true);

//        verify(casesServiceApi).loadCases(eq(FILTER), casesLoadedCallbackArgumentCaptor.capture());
//        casesLoadedCallbackArgumentCaptor.getValue().onCasesLoadedFromApi(LOADED_CASES);

        verify(filteredCasesStore).setFilteredCases(LOADED_CASES);
        verify(filteredCasesStore).setLoading(false);
    }

}
