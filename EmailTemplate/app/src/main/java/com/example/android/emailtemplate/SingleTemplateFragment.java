package com.example.android.emailtemplate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Huong on 9/19/2015.
 */
public class SingleTemplateFragment extends Fragment {
    private EditText mTitleEditText;
    private EditText mEmailEditText;
    private Button mSaveButton;
    private Button mSendButton;
    private CheckBox mFavoriteCheckBox;
    private Template mTemplate;
    private DataInterface mDataInterface;
    private boolean mNewTemplate;
    private CheckBox mSigCheckBox;
    private Signature mSignature;
    private boolean includeSignature;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mDataInterface = DataInterface.getDataInterface(getContext());
        String uuid = getActivity().getIntent()
                .getStringExtra(TEMPLATE_UUID);
        if (uuid != null) {
            mTemplate = mDataInterface.getTemplate(
                    UUID.fromString(uuid));
        }
        else {
            mNewTemplate = true;
            mTemplate = new Template();
            String cUUID = getActivity().getIntent()
                    .getStringExtra(CATEGORY_UUID);
            if (cUUID != null) {
                mTemplate.setCategoryUUID(UUID.fromString(cUUID));
            }
        }
        Category category = mDataInterface.getCategory(
                mTemplate.getCategoryUUID());
        getActivity().setTitle(category.getCategory());

        mSignature = Signature.getInstance(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout
                .single_template_fragment, container, false);
        mTitleEditText = (EditText) view.findViewById(R.id.title_edit_text);
        mEmailEditText = (EditText) view.findViewById(R.id.email_text_edit_text);

        mSigCheckBox = (CheckBox) view.findViewById(R.id.include_signature_checkbox);
        mSigCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSignature = ((CheckBox) v).isChecked();
            }
        });

        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTemplate();
            }
        });
        mSendButton = (Button) view.findViewById(R.id.send_email_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTemplate();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                String message = mTemplate.getTemplate();
                if(includeSignature) {
                    message = message + mSignature.getSignature();
                }
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        mTitleEditText.setText(mTemplate.getName());
        mEmailEditText.setText(mTemplate.getTemplate());

        mFavoriteCheckBox = (CheckBox) view.findViewById(R.id.favorite_check_box);
        mFavoriteCheckBox.setChecked(mTemplate.isFavorite());
        return view;
    }

    private void saveTemplate() {
        mTemplate.setName(mTitleEditText.getText().toString());
        mTemplate.setTemplate(mEmailEditText.getText().toString());
        Calendar calendar = Calendar.getInstance();
        mTemplate.setLastAccessed(new Timestamp(calendar.getTime().getTime()));
        mTemplate.setIsFavorite(mFavoriteCheckBox.isChecked());
        if (mNewTemplate) {
            mDataInterface.addTemplate(mTemplate);
        }
        else {
            mDataInterface.updateTemplate(mTemplate);
        }
        getActivity().finish();
    }

    public static Intent newIntent(Context packageContext, UUID templateUUID,
                                   UUID categoryUUID) {
        Intent i = new Intent(packageContext, SingleTemplateActivity.class);
        if (templateUUID != null){
            i.putExtra(TEMPLATE_UUID, templateUUID.toString());
        }
        if (categoryUUID != null) {
            i.putExtra(CATEGORY_UUID, categoryUUID.toString());
        }
        return i;
    }

    public static final String TEMPLATE_UUID = "template_uuid";
    public static final String CATEGORY_UUID = "category_uuid";
}
