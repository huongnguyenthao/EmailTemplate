package com.example.android.emailtemplate;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Huong on 9/15/2015.
 */
public class NewCategoryDialog extends AppCompatDialogFragment {

    private static final String ARG_NAME = "name";
    public static final String EXTRA_NAME =
            "com.bignerdranch.android.criminalintent.date";
    private EditText mEditText;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.new_category_fragment, null);
        setRetainInstance(true);
        mEditText = (EditText) v.findViewById(R.id.category_edit_text);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Enter new category:")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK,
                                        mEditText.getText().toString());
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, String name) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, name);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public static NewCategoryDialog newInstance() {
            Bundle args = new Bundle();
            args.putSerializable(ARG_NAME, null);
            NewCategoryDialog fragment = new NewCategoryDialog();
            fragment.setArguments(args);
            return fragment;
    }
}
