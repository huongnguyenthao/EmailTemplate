package com.example.android.emailtemplate;

import android.support.v4.app.Fragment;

/**
 * Created by Huong on 9/19/2015.
 */
public class SingleTemplateActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SingleTemplateFragment();
    }
}
