package com.example.android.emailtemplate.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.android.emailtemplate.Category;
import com.example.android.emailtemplate.database.TemplateDbSchema.CategoryTable;
import java.util.UUID;

/**
 * Created by Huong on 9/14/2015.
 */
public class CategoryCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CategoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Category getCategory() {
        UUID uuid = UUID.fromString(getString(getColumnIndex(
                CategoryTable.Cols.UUID)));
        String category = getString(getColumnIndex(
                CategoryTable.Cols.CATEGORY));
        Category cat = new Category(uuid);
        cat.setCategory(category);;
        return cat;
    }
}
