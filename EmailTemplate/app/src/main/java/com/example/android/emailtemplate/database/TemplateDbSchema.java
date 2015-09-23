package com.example.android.emailtemplate.database;

/**
 * Created by Huong on 9/18/2015.
 * * <img src="../../../../../screenshots/databaseschema.JPG" alt="Database Schema" style="width:479px;height:274px;">
 */
public class TemplateDbSchema {
    /**
     * The schema for the categories table.
     */
    public static final class CategoryTable{
        public final static String NAME = "categories";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String CATEGORY = "category";
        }
    }

    /***
     * The schema for the templates table. Contains a UUID link
     * to the categories table.
     */
    public static final class TemplateTable{
        public final static String NAME = "templates";
        public static final class Cols {
            public static final String CATEGORYUUID = "categoryUUID";
            public static final String UUID = "UUID";
            public static final String NAME = "name";
            public static final String TEMPLATE = "template";
            public static final String ISFAVORITE = "isFavorite";
            public static final String LASTACCESSED = "lastaccessed";
        }
    }
}
