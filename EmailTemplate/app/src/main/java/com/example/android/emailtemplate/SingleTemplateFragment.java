package com.example.android.emailtemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Huong on 9/19/2015.
 */
public class SingleTemplateFragment extends Fragment {
    private EditText mTitleEditText;
    private EditText mEmailEditText;
    private Button mSaveButton;
    private Template mTemplate;
    private DataInterface mDataInterface;
    private boolean mNewTemplate;

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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout
                .single_template_fragment, container, false);
        mTitleEditText = (EditText) view.findViewById(R.id.title_edit_text);
        mEmailEditText = (EditText) view.findViewById(R.id.email_text_edit_text);
        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTemplate();
            }
        });
        mTitleEditText.setText(mTemplate.getName());
        mEmailEditText.setText(mTemplate.getTemplate());
        return view;
    }

    private void saveTemplate() {
        mTemplate.setName(mTitleEditText.getText().toString());
        mTemplate.setTemplate(mEmailEditText.getText().toString());
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
