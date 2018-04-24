package com.example.rdunc.goalandtaskmatcher;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Identifier for the GoalAndTaskMaster data loader
    private static final int GOAL_LOADER = 1;

    //* Adapter for the ListView
    GoalCursorAdapter mCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMidLevel = findViewById(R.id.btnMidLevel);
        Button btnDisplayGoals = findViewById(R.id.btnDisplayGoals);
        final TextView txtGoal1 = findViewById(R.id.txtGoal1);

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
                txtGoal1.setText("Get a superman cape!");
            }
        });



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