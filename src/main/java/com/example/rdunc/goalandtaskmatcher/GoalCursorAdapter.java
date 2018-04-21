package com.example.rdunc.goalandtaskmatcher;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rdunc.goalandtaskmatcher.data.GoalandTaskMatcherContract;

public class GoalCursorAdapter extends CursorAdapter {

    // The default constructor
    public GoalCursorAdapter(Context context, Cursor c) {super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    //    This method binds the product data (in the current row pointed to by cursor) to the given
    //    ist item layout. For example, the name for the current product can be set on the name TextView
//    in the list item layout.
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final int newID = cursor.getInt(cursor.getColumnIndex(GoalandTaskMatcherContract.GoalEntry.MY_GOAL_ID));

        // Find individual views that we want to modify or use in the list item layout
        TextView txtGoalDescription = (TextView) view.findViewById(R.id.txtGoalDescription);

        // Find the columns of product attributes that we're interested in
        int nameIDIndex = cursor.getColumnIndex(GoalandTaskMatcherContract.GoalEntry.MY_GOAL_ID);
        int goalColumnIndex = cursor.getColumnIndex(GoalandTaskMatcherContract.GoalEntry.COLUMN_GOAL_DESCRIPTION);

        // Read the goals attributes from the Cursor for the current goal
        String goalDescription = cursor.getString(goalColumnIndex);


        // Update the TextViews and ImageViews with the attributes for the current goal
        txtGoalDescription.setText(goalDescription);

    }
}
