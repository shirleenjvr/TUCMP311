package com.skneppers.tucmp311_1;

//import com.example.extrafiles.R;

import java.util.HashMap;

//import com.example.extrafiles.CommentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class AddCommentActivity extends Activity {

	EditText userComment;
	DBHelper db;
	String tutTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comment);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) 
			{
		    tutTitle = extras.getString("tutTitle");
			}
		db = new DBHelper(getApplicationContext());
		userComment = (EditText) findViewById(R.id.userComment);
	}
	
	public void addNewComment(View view) {
        HashMap<String, String> queryValues = new HashMap<String, String>();
        queryValues.put("userComment", userComment.getText().toString());
        if (userComment.getText().toString() != null
                && userComment.getText().toString().trim().length() != 0) {
            db.insertComment(queryValues);
            this.callHomeActivity(view);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter comment",
                    Toast.LENGTH_LONG).show();
        }
    }
	
	public void callHomeActivity(View view) {
        Intent objIntent = new Intent(getApplicationContext(),
                SingleTutorial.class);
        objIntent.putExtra("tutTitle", tutTitle);
        Log.d(tutTitle, "In the onCreate() event");
        startActivity(objIntent);
    }
	
	public void cancelAddComment(View view) {
		this.callHomeActivity(view);
    }
}
