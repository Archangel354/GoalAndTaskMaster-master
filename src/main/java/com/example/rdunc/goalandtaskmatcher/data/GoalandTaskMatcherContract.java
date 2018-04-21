package com.example.rdunc.goalandtaskmatcher.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class GoalandTaskMatcherContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private GoalandTaskMatcherContract() {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.rdunc.goalandtaskmatcher";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.rdunc.goalandtaskmatcher/goalandtaskmatcher/ is a valid path for
     * looking at inventory data. content://com.example.android.goalandtaskmatcher/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_GOALS = "goals";

    /**
     * Inner class that defines constant values for the goals database table.
     * Each entry in the table represents a single goal.
     */
    public static final class GoalEntry implements BaseColumns {

        /**
         * The content URI to access the goals data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GOALS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of goals.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GOALS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single goal.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GOALS;

        /**
         * Name of database table for the goals
         */
        public final static String TABLE_NAME = "goals";

        /**
         * Unique ID number for each goals (only for use in the goals database table).
         * Type: INTEGER
         */
        public final static String MY_GOAL_ID = BaseColumns._ID;

        /**
         * Description of the goal.
         * Type: TEXT
         */
        public final static String COLUMN_GOAL_DESCRIPTION = "goal";
    }
}
