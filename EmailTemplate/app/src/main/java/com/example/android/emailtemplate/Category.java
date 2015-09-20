package com.example.android.emailtemplate;

import java.util.UUID;

/**
 * Created by Huong on 9/18/2015.
 */
public class Category {
    private UUID mUUID;
    private String mCategory;

    public Category(UUID uuid) {
        mUUID = uuid;
    }

    public Category() {
        this(UUID.randomUUID());
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }
}
