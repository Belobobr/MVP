package com.mixailsednev.githubrepo.mvptabletphone.model.cases;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mixailsednev.githubrepo.mvptabletphone.CasebookApplication;
import com.mixailsednev.githubrepo.mvptabletphone.model.CaseBookContentProviderContract;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class FilteredCaseDao {

    private final static String TAG = FilteredCaseDao.class.getSimpleName();

    public static Observable<List<Case>> getCasesObservable(@Nullable final Filter filter) {
        return Observable.create(subscriber ->  {
            Observable.just(getCases(filter)).subscribe(subscriber);
        });
    }

    @NonNull
    public static List<Case> getCases(@Nullable final Filter filter) {
        String selectionClause = null;
        String selectionArgs[] = null;
        if (filter != null && filter.getCaseType() != null) {
            selectionClause = CaseBookContentProviderContract.CASE_TYPE + " = ?";
            selectionArgs = new String[]{filter.getCaseType()};
        }

        Cursor casesCursor = CasebookApplication.getContext().getContentResolver()
                .query(CaseBookContentProviderContract.CASE_CONTENT_URI, null, selectionClause, selectionArgs, null);

        ArrayList<Case> cases = new ArrayList<Case>();
        if (casesCursor != null) {
            try {
                while (casesCursor.moveToNext()) {
                    int caseTitleIndex = casesCursor.getColumnIndex(
                            CaseBookContentProviderContract.CASE_TITLE);
                    int caseTypeIndex = casesCursor.getColumnIndex(
                            CaseBookContentProviderContract.CASE_TYPE);
                    Case caseFromCursor = new CaseBuilder()
                            .setTitle(casesCursor.getString(caseTitleIndex))
                            .setCaseType(casesCursor.getString(caseTypeIndex))
                            .createCase();
                    cases.add(caseFromCursor);
                }
            } finally {
                casesCursor.close();
            }
        }
        return cases;
    }

    public static boolean saveCases(@NonNull List<Case> cases) {
        ArrayList<ContentValues> contentValues = new ArrayList<ContentValues>(cases.size());

        for (Case savedCase : cases) {
            ContentValues cv = new ContentValues();
            cv.put(CaseBookContentProviderContract.CASE_TITLE, savedCase.getTitle());
            cv.put(CaseBookContentProviderContract.CASE_TYPE, savedCase.getCaseType());
            contentValues.add(cv);
        }

        CasebookApplication.getContext().getContentResolver().delete(CaseBookContentProviderContract.CASE_CONTENT_URI, null, null);

        int casesInserted = CasebookApplication.getContext()
                .getContentResolver().bulkInsert(
                        CaseBookContentProviderContract.CASE_CONTENT_URI,
                        contentValues.toArray(new ContentValues[contentValues.size()]));
        Log.d(TAG, " insert to : "  + CaseBookContentProviderContract.CASE_CONTENT_URI +
                " " + casesInserted + " cases.");
        return true;
    }

}
