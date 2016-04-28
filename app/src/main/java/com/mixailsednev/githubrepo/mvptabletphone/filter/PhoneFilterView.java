package com.mixailsednev.githubrepo.mvptabletphone.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.R;

import java.util.Date;

public class PhoneFilterView extends LinearLayout {

    @NonNull
    TextView dateTextView;

    @NonNull
    TextView caseTypeTextView;

    @NonNull
    TextView assignedTextView;

    public PhoneFilterView(Context context) {
        this(context, null);
    }

    public PhoneFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_phone_filter, this, true);

        dateTextView = (TextView)findViewById(R.id.date);
        caseTypeTextView = (TextView)findViewById(R.id.caseType);
        assignedTextView = (TextView)findViewById(R.id.assigned);
    }

    public void setFilter(@Nullable Filter filter) {
        if (filter != null) {
            dateTextView.setText(
                    filter.getDate() != null ?
                            new Date(filter.getDate()).toString()
                            : getResources().getString(R.string.not_selected));
            caseTypeTextView.setText(
                    TextUtils.isEmpty(filter.getCaseType()) ?
                            getResources().getString(R.string.case_type_not_selected)
                            :filter.getCaseType());
            assignedTextView.setText(
                    TextUtils.isEmpty(filter.getAssigned()) ?
                            getResources().getString(R.string.not_assigned)
                            :filter.getAssigned());
        }
    }
}
