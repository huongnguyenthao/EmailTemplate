package com.example.android.emailtemplate;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Huong on 9/18/2015.
 */
public class Template {
    private UUID mCategoryUUID;

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    private UUID mUUID;
    private String mName;
    private String mTemplate;

    public Timestamp getLastAccessed() {
        return mLastAccessed;
    }

    public void setLastAccessed(Timestamp lastAccessed) {
        mLastAccessed = lastAccessed;
    }

    private Timestamp mLastAccessed;

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    private boolean mIsFavorite;

    public Template(UUID uuid) {
        mUUID = uuid;
        Calendar calendar = Calendar.getInstance();
        mLastAccessed = new Timestamp(calendar.getTime().getTime());
    }

    public Template() {
        this(UUID.randomUUID());
    }

    public UUID getCategoryUUID() {
        return mCategoryUUID;
    }

    public void setCategoryUUID(UUID categoryUUID) {
        mCategoryUUID = categoryUUID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTemplate() {
        return mTemplate;
    }

    public void setTemplate(String template) {
        mTemplate = template;
    }
}
