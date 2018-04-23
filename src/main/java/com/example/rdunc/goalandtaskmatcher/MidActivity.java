package com.example.rdunc.goalandtaskmatcher;


import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.rdunc.goalandtaskmatcher.data.GoalandTaskMatcherContract.GoalEntry;
import static com.example.rdunc.goalandtaskmatcher.EditorActivity.getUriToDrawable;

public class MidActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Identifier for the GoalAndTaskMaster data loader
    private static final int GOAL_LOADER = 0;

    //* Adapter for the ListView
    GoalCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid);

        // Setup big green "plus" button to open EditorActivity
        ImageButton btnAddGoal = (ImageButton) findViewById(R.id.btnAddGoal);
        btnAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MidActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the goal data
        ListView goalListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        goalListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of goal data in the Cursor.
        // There is no goal data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new GoalCursorAdapter(this, null);
        goalListView.setAdapter(mCursorAdapter);
        // Setup the item click listener
        goalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(MidActivity.this, EditorActivity.class);
                // Form the content URI that represents the specific goal that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link GoalEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.rdunc.goalandtaskmatcher/goals/2"
                // if the goal with ID 2 was clicked on.
                Uri currentGoalUri = ContentUris.withAppendedId(GoalEntry.CONTENT_URI, id);
                // Set the URI on the data field of the intent
                intent.setData(currentGoalUri);
                // Launch the {@link EditorActivity} to display the data for the current goal.
                startActivity(intent);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(GOAL_LOADER, null, this);
    }

    /**
     * Helper method to insert hardcoded goal data into the database. For debugging purposes only.
     */
    private void insertGoal() {

        String defaultUriString = "drawable/grocerycart.png";
        int resID = getResources().getIdentifier("grocerycart" , "drawable", getPackageName());
        Uri imageUri = Uri.parse(defaultUriString);

        imageUri =  getUriToDrawable(this, resID);
        //Uri imageUri = Uri.parse(mImageEditText.getText().toString());
        //mImageView.setImageBitmap(EditorActivity.getBitmapFromUri(imageUri));


        // Create a ContentValues object where column names are the keys,
        // and hammer's attributes are the values.
        ContentValues values = new ContentValues();
        values.put(GoalEntry.COLUMN_GOAL_DESCRIPTION, "Climb Mt. Everest");

        // Insert a new row for Climb Mt. Everest into the provider using the ContentResolver.
        // Use the {@link ProductEntry#CONTENT_URI} to indicate that we want to insert
        // into the goals database table.
        // Receive the new content URI that will allow us to access the Climb Mt. Everest data in the future.
        Uri newUri = getContentResolver().insert(GoalEntry.CONTENT_URI, values);
    }

    /**
     * Helper method to delete all goals in the database.
     */
    private void deleteAllGoals() {
        int rowsDeleted = getContentResolver().delete(GoalEntry.CONTENT_URI, null, null);
        Log.v("MainActivity", rowsDeleted + " rows deleted from products database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertGoal();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllGoals();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                GoalEntry.MY_GOAL_ID,
                GoalEntry.COLUMN_GOAL_DESCRIPTION

        };
        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                GoalEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated product data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
