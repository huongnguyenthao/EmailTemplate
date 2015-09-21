package com.example.android.emailtemplate.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.android.emailtemplate.Template;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

import static com.example.android.emailtemplate.database.TemplateDbSchema.*;

/**
 * Created by Huong on 9/15/2015.
 */
public class TemplateCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TemplateCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Template getTemplate() {
        UUID categoryUUID = UUID.fromString(getString(
                getColumnIndex(TemplateTable.Cols.CATEGORYUUID)));
        UUID uuid = UUID.fromString(getString(
                getColumnIndex(TemplateTable.Cols.UUID)));
        String name = getString(getColumnIndex(TemplateTable.Cols.NAME));
        String templateString = getString(getColumnIndex
                (TemplateTable.Cols.TEMPLATE));
        int isFave = getInt(getColumnIndex
                (TemplateTable.Cols.ISFAVORITE));
        String lastAccessed = getString(getColumnIndex(
                TemplateTable.Cols.LASTACCESSED));
        Timestamp timestamp = Timestamp.valueOf(lastAccessed);
        Template template = new Template(uuid);
        template.setCategoryUUID(categoryUUID);
        template.setName(name);
        template.setTemplate(templateString);
        template.setIsFavorite(isFave != 0);
        template.setLastAccessed(timestamp);
        return template;
    }
}
