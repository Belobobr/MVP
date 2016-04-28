package com.mixailsednev.githubrepo.mvptabletphone;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.CaseTypes;
import com.mixailsednev.githubrepo.mvptabletphone.utils.Display;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilteredCasesScreenTest {

    private static final String ASSIGNED = "Sednev";

    private static Matcher<View> allAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("adapter contains only items: ");
                dataMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (!dataMatcher.matches(adapter.getItem(i))) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    private static BoundedMatcher<Object, Case> caseMatcher(@NonNull final String caseType,
                                                            @Nullable final String assigned) {
        return new BoundedMatcher<Object, Case>(Case.class) {
            @Override
            public boolean matchesSafely(Case aCase) {
                return caseType.equals(aCase.getCaseType())
                        && (assigned == null || assigned.equals(aCase.getAssigned()));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with case type: " + caseType + " and assigned on: " + assigned);
            }
        };
    }

    @Rule
    public ActivityTestRule<CasesActivity> mCasesActivityTestRule =
            new ActivityTestRule<>(CasesActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void filter() throws Exception {
        if (Display.phone(mCasesActivityTestRule.getActivity().getWindowManager())) {
            checkFilter(CaseTypes.ARBITR, null);
            checkFilter(CaseTypes.COMMON, null);
            checkFilter(CaseTypes.ARBITR, "Misha");
            checkFilter(CaseTypes.COMMON, "Misha");
        }
    }

    private void checkFilter(@NonNull String caseType, @Nullable String assigned) {
        onView(withId(R.id.filter)).perform(click());

        onView(withId(R.id.caseTypeSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(caseType))).perform(click());

        if (assigned != null) {
            onView(withId(R.id.assignedEditText)).perform(typeText(assigned));
        }

        onView(withId(R.id.complete)).perform(click());

        onView(withId(R.id.casesListView))
                .check(matches(allAdaptedData(
                        caseMatcher(caseType, assigned)
                )));

        onView(withId(R.id.phoneFilterView))
                .check(matches(hasDescendant(withText(caseType))));
        String notAssignedText = mCasesActivityTestRule.getActivity().getString(R.string.not_assigned);
        onView(withId(R.id.phoneFilterView))
                .check(matches(hasDescendant(withText(assigned != null ? assigned : notAssignedText))));
    }

    @Test
    public void filterByCaseTypeTablet() {
        if (Display.tablet(mCasesActivityTestRule.getActivity().getWindowManager())) {

        }
    }


}
