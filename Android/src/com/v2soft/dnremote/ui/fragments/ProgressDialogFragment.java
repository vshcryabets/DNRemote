package com.v2soft.dnremote.ui.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.v2soft.dnremote.R;

public class ProgressDialogFragment 
extends DialogFragment {
    private static final String EXTRA_TEXT = "text";

    public static ProgressDialogFragment newInstance(String text) {
        final Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, text);
        final ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProgressDialogFragment newInstance(Context context, int textResource) {
        final Bundle bundle = new Bundle();
        final String text = context.getResources().getString(textResource);
        bundle.putString(EXTRA_TEXT, text);
        final ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_dialog, null);
        final String text = getArguments().getString(EXTRA_TEXT);
        ((TextView)view.findViewById(R.id.txtDescription)).setText(text);
        return view;
    }
}