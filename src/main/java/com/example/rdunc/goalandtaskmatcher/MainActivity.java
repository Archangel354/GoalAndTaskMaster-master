package com.example.rdunc.goalandtaskmatcher;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rdunc.goalandtaskmatcher.data.GoalandTaskMatcherContract;
import com.example.rdunc.goalandtaskmatcher.data.GoalandTaskMatcherContract.GoalEntry;
import static com.example.rdunc.goalandtaskmatcher.data.GoalandTaskMatcherContract.GoalEntry.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Identifier for the GoalAndTaskMaster data loader
    private static final int GOAL_LOADER = 1;

    //* Adapter for the ListView
    GoalCursorAdapter mCursorAdapter;

    //Test field to display goal #1
    TextView txtGoal1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMidLevel = findViewById(R.id.btnMidLevel);
        Button btnDisplayGoals = findViewById(R.id.btnDisplayGoals);
        txtGoal1 = findViewById(R.id.txtGoal1);

        btnMidLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MidActivity.class);
                startActivity(intent);
            }
        });

        btnDisplayGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtGoal1.setText("Roger roger");
                //txtGoal1.setText(goal1);
                // Initialize a loader to read the product data from the database
                // and display the current values in the editor
            }
        });

        getLoaderManager().initLoader(GOAL_LOADER, null, this);

    }





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the editor shows all product attributes, define a projection that contains
        // all columns from the products table
        String[] projection = {
                GoalEntry.MY_GOAL_ID,
                GoalEntry.COLUMN_GOAL_DESCRIPTION};


        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                CONTENT_URI,         // Query the content URI for the current product
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of product attributes that we're interested in
            int idColumnIndex = cursor.getColumnIndex(GoalEntry.MY_GOAL_ID);
            int descriptionColumnIndex = cursor.getColumnIndex(GoalEntry.COLUMN_GOAL_DESCRIPTION);


            // Extract out the value from the Cursor for the given column index
            String ID = cursor.getString(idColumnIndex);
            String goal1 = cursor.getString(descriptionColumnIndex);
            Log.i("GOAL 1 is: ", goal1);

            // Update the views on the screen with the values from the database
            txtGoal1.setText(goal1.toString());
            Log.i("GOAL 1 text is: ", txtGoal1.toString());

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        txtGoal1.setText("");

    }
}