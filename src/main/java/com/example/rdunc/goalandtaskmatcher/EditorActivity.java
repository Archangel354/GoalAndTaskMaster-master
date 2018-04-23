package com.example.rdunc.goalandtaskmatcher;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rdunc.goalandtaskmatcher.data.GoalandTaskMatcherContract;

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

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mDescriptionEditText.setOnTouchListener(mTouchListener);
    }

    /**
     * Get user input from editor and save goal into goal database.
     */
    private void saveGoal() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String descriptionString = mDescriptionEditText.getText().toString().trim();


        // Check if this is supposed to be a new product
        // and check if all the fields in the editor are blank
        if (mCurrentGoalUri == null && (TextUtils.isEmpty(descriptionString) )) {
            Toast.makeText(this, "Please add info to all the fields",
                    Toast.LENGTH_SHORT).show();
            // Since no fields were modified, we can return early without creating a new goal.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and goal attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(GoalandTaskMatcherContract.GoalEntry.COLUMN_GOAL_DESCRIPTION, descriptionString);

        // Determine if this is a new or existing goal by checking if mCurrentGoalUri is null or not
        if (mCurrentGoalUri == null) {
            // This is a NEW goal, so insert a new goal into the provider,
            // returning the content URI for the new goal.
            Uri newUri = getContentResolver().insert(GoalandTaskMatcherContract.GoalEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_goal_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_goal_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING goal, so update the goal with content URI: mCurrentGoalUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentGoalUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentGoalUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_goal_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_goal_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new product, hide the "Delete" menu item.
        if (mCurrentGoalUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
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
