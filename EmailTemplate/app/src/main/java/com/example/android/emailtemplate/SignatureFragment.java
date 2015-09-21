package com.example.android.emailtemplate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Eric Andow on 9/21/2015.
 */
public class SignatureFragment extends AppCompatDialogFragment {
    private EditText mEditText;
    private Signature mSignature;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSignature = Signature.getInstance(getActivity());
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.signature_fragment, null);

        mEditText = (EditText) v.findViewById(R.id.signature_edit_text);
        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.edit_signature_dialog_title)
                .setPositiveButton(R.string.save_signature,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSignature.setSignature(mEditText.getText().toString());
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .create();
    }

    public static SignatureFragment newInstance() {
        return new SignatureFragment();
    }
}
