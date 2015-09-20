package com.example.android.emailtemplate;

import android.app.Activity;
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

/**
 * Created by Huong on 9/18/2015.
 */
public class CategoryFragment extends Fragment {
    private RecyclerView mPlayerRecyclerView;
    private CategoriesAdapter mRosterAdapter;
    private static final String DIALOG_NEW_CATEGORY = "DialogNewCategory";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mPlayerRecyclerView = (RecyclerView) view
                .findViewById(R.id.fragment_category_recycler_view);
        mPlayerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }
    private static final int REQUEST_NAME = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_player:
                FragmentManager manager = getFragmentManager();
                NewCategoryDialog dialog = new NewCategoryDialog();
                dialog.setTargetFragment(CategoryFragment.this, REQUEST_NAME);
                dialog.show(manager, DIALOG_NEW_CATEGORY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_category_fragment, menu);
        MenuItem menuItem = (MenuItem) menu.findItem(R.id.menu_item_new_player);
        menuItem.setTitle(R.string.add_new_category);
    }

    private void updateUI() {
        DataInterface dataInterface = DataInterface.getDataInterface(getActivity());
        List<Category> categories = dataInterface.getCategories();
        mRosterAdapter = new CategoriesAdapter(categories);
        mPlayerRecyclerView.setAdapter(mRosterAdapter);
    }

    private class CategoryHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public TextView mTitleTextView;
        private Category mCategory;
        public CategoryHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView
                    .findViewById(R.id.item_text_view);
            itemView.setOnClickListener(this);
        }

        public void bindPlayer(Category category) {
            mCategory = category;
            mTitleTextView.setText(category.getCategory());
        }

        @Override
        public void onClick(View v) {
            Intent intent = TemplateRecyclerFragment
                    .newIntent(getContext(), mCategory.getUUID());
            startActivity(intent);
            //setNameChosen(mDatabasePlayer.getName());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_NAME) {
            String name = (String) data
                    .getSerializableExtra(NewCategoryDialog.EXTRA_NAME);
            Category category = new Category();
            DataInterface dataInterface = DataInterface
                    .getDataInterface(getActivity());
            category.setCategory(name);
            dataInterface.addCategory(category);
            updateUI();
        }
    }

    private class CategoriesAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private List<Category> mCategories;
        public CategoriesAdapter(List<Category> categories) {
            mCategories = categories;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.view_holder_layout, parent, false);
            return new CategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryHolder categoryHolder, int i) {
            Category category = mCategories.get(i);
            categoryHolder.bindPlayer(category);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }

}
