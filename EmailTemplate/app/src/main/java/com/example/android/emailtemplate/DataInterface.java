package com.example.android.emailtemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.emailtemplate.database.CategoryCursorWrapper;
import com.example.android.emailtemplate.database.TemplateBaseHelper;
import com.example.android.emailtemplate.database.TemplateCursorWrapper;
import com.example.android.emailtemplate.database.TemplateDbSchema;
import com.example.android.emailtemplate.database.TemplateDbSchema.CategoryTable;
import com.example.android.emailtemplate.database.TemplateDbSchema.TemplateTable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Huong on 9/19/2015.
 */
public class DataInterface {
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private static DataInterface sDataInterface;

    /**
     * Database layer
     * @param context
     * @return instance of DatabaseInterface
     */
    public static DataInterface getDataInterface(Context context) {

        if (sDataInterface == null) {
            sDataInterface = new DataInterface(context);
        }
        return sDataInterface;
    }

    private DataInterface (Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TemplateBaseHelper(mContext)
                .getWritableDatabase();
    }

    private CategoryCursorWrapper queryCategory(
            String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CategoryTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new CategoryCursorWrapper(cursor);
    }

    private TemplateCursorWrapper queryTemplate(
            String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TemplateTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new TemplateCursorWrapper(cursor);
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        CategoryCursorWrapper cursor = queryCategory(
                null, null);
        try {
            if (cursor.getCount() == 0) {
                return categories;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                categories.add(cursor.getCategory());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return categories;
    }

    public List<Template> getTemplates() {
        List<Template> templates = new ArrayList<>();
        TemplateCursorWrapper cursor = queryTemplate(
                null, null);
        try {
            if (cursor.getCount() == 0) {
                return templates;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                templates.add(cursor.getTemplate());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return templates;
    }

    public List<Template> getTemplates(UUID categoryUUID) {
        List<Template> templates = new ArrayList<>();
        TemplateCursorWrapper cursor = queryTemplate(
                TemplateTable.Cols
                        .CATEGORYUUID + " = ?",
                new String[]{categoryUUID.toString()});
        try {
            if (cursor.getCount() == 0) {
                return templates;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                templates.add(cursor.getTemplate());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return templates;
    }

    public List<Template> getRecentTemplates() {
        List<Template> templates = getTemplates();
        Collections.sort(templates, getTemplateTimeComparator);
        return templates;
    }

    public List<Template> getFavoriteTemplates() {
        List<Template> templates = new ArrayList<>();
        TemplateCursorWrapper cursor = queryTemplate(null, null);
        try {
            if (cursor.getCount() == 0) {
                return templates;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Template template = cursor.getTemplate();
                if (template.isFavorite()){
                    templates.add(template);
                }
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return templates;
    }

    public static Comparator<Template> getTemplateTimeComparator
            = new Comparator<Template>() {
        @Override
        public int compare(Template lhs, Template rhs) {
            return lhs.getLastAccessed().compareTo(rhs.getLastAccessed());
        }
    };

    public Category getCategory(UUID uuid) {
        Category category = new Category(uuid);
        CategoryCursorWrapper cursor = queryCategory(
                CategoryTable.Cols
                        .UUID + " = ?",
                new String[]{uuid.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            category = (cursor.getCategory());
        }
        finally {
            cursor.close();
        }
        return category;
    }

    public Template getTemplate(UUID uuid) {
        Template template = new Template(uuid);
        TemplateCursorWrapper cursor = queryTemplate(
                TemplateTable.Cols
                        .UUID + " = ?",
                new String[]{uuid.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            template = (cursor.getTemplate());
        }
        finally {
            cursor.close();
        }
        return template;
    }

    public void addCategory(Category category) {
        ContentValues values = getContentValuesCategory(category);
        mDatabase.insert(CategoryTable.NAME, null, values);
    }

    private static ContentValues getContentValuesCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(CategoryTable.Cols.CATEGORY, category.getCategory());
        values.put(CategoryTable.Cols.UUID, category.getUUID().toString());
        return values;
    }

    public void addTemplate(Template template) {
        ContentValues values = getContentValuesTemplate(template);
        mDatabase.insert(TemplateTable.NAME, null, values);
    }

    private static ContentValues getContentValuesTemplate(Template template) {
        ContentValues values = new ContentValues();
        values.put(TemplateTable.Cols.UUID,
                template.getUUID().toString());
        values.put(TemplateTable.Cols.CATEGORYUUID,
                template.getCategoryUUID().toString());
        values.put(TemplateTable.Cols.TEMPLATE,
                template.getTemplate());
        values.put(TemplateTable.Cols.NAME,
                template.getName());
        values.put(TemplateTable.Cols.ISFAVORITE,
                template.isFavorite() ? 1 : 0);
        values.put(TemplateTable.Cols.LASTACCESSED,
                template.getLastAccessed().toString());
        return values;
    }

    public void updateTemplate(Template template) {
        String uuidString = template.getUUID().toString();
        ContentValues values = getContentValuesTemplate(template);
        mDatabase.update(TemplateTable.NAME, values,
                TemplateTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }
}
