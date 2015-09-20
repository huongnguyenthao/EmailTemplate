package com.example.android.emailtemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Huong on 9/19/2015.
 */
public class TemplateRecyclerFragment extends Fragment {
    private RecyclerView mTemplateRecyclerView;
    private TemplateAdapter mTemplateAdapter;
    private static final String NEW_TEMPLATE = "NewTemplate";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCategoryUUID = UUID.fromString(getActivity()
                .getIntent().getStringExtra(CATEGORY_UUID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mTemplateRecyclerView = (RecyclerView) view
                .findViewById(R.id.fragment_category_recycler_view);
        mTemplateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_player:
                Intent intent = SingleTemplateFragment
                        .newIntent(getContext(), null, mCategoryUUID);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_category_fragment, menu);
        MenuItem menuItem = (MenuItem) menu.findItem(R.id.menu_item_new_player);
        menuItem.setTitle(R.string.add_new_template);
    }

    private void updateUI() {
        DataInterface dataInterface = DataInterface.getDataInterface(getActivity());
        List<Template> templates = dataInterface.getTemplates(mCategoryUUID);
        mTemplateAdapter = new TemplateAdapter(templates);
        mTemplateRecyclerView.setAdapter(mTemplateAdapter);
    }

    private class TemplateHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public TextView mTitleTextView;
        private Template mTemplate;
        public TemplateHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView
                    .findViewById(R.id.item_text_view);
            itemView.setOnClickListener(this);
        }

        public void bindPlayer(Template template) {
            mTemplate = template;
            mTitleTextView.setText(template.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = SingleTemplateFragment
                    .newIntent(getContext(), mTemplate.getUUID(),
                            mCategoryUUID);
            startActivity(intent);
        }
    }

    private class TemplateAdapter extends RecyclerView.Adapter<TemplateHolder> {
        private List<Template> mTemplates;
        public TemplateAdapter(List<Template> templates) {
            mTemplates = templates;
        }

        @Override
        public TemplateHolder onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.view_holder_layout, parent, false);
            return new TemplateHolder(view);
        }

        @Override
        public void onBindViewHolder(TemplateHolder templateHolder, int i) {
            Template template = mTemplates.get(i);
            templateHolder.bindPlayer(template);
        }

        @Override
        public int getItemCount() {
            return mTemplates.size();
        }
    }

    public static Intent newIntent(Context packageContext, UUID categoryUUID) {
        Intent i = new Intent(packageContext, TemplatesActivity.class);
        i.putExtra(CATEGORY_UUID, categoryUUID.toString());
        return i;
    }

    public static final String CATEGORY_UUID = "category_uuid";
    private UUID mCategoryUUID;

}
