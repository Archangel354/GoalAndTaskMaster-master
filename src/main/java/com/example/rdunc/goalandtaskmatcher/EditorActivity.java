package com.example.rdunc.goalandtaskmatcher;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the goal data loader
     */
    private static final int EXISTING_GOAL_LOADER = 0;

    /**
     * Content URI for the existing goal (null if it's a new goal)
     */
    private Uri mCurrentGoalUri;

    /**
     * EditText field to enter the goal's description
     */
    private EditText mDescriptionEditText;

    /**
     * Boolean flag that keeps track of whether the goal has been edited (true) or not (false)
     */
    private boolean mGoalHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mGoalHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGoalHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new goal or editing an existing one.
        Intent intent = getIntent();
        mCurrentGoalUri = intent.getData();

        // If the intent DOES NOT contain a goal content URI, then we know that we are
        // creating a new goal.
        if (mCurrentGoalUri == null) {
            // This is a new goal, so change the app bar to say "Add a Goal"
            setTitle(getString(R.string.editor_activity_title_new_goal));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a goal that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing goal, so change app bar to say "Edit Goal"
            setTitle(getString(R.string.editor_activity_title_edit_goal));

            // Initialize a loader to read the goal data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_GOAL_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mDescriptionEditText = (EditText) findViewById(R.id.edtGoalDescription);
    }

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
