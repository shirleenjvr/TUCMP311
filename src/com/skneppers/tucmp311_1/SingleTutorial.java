package com.skneppers.tucmp311_1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SingleTutorial extends Activity {

	String tutID;
	String tutTitle = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.single_tutorial);
		List<String> comments = new ArrayList<String>();
				
		String[] tutData;
		tutData = new String[4];
		DBHelper db;
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) 
			{
		    tutTitle = extras.getString("tutTitle");
			}
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		
		db = new DBHelper(getApplicationContext());
		//Get Our Tutorial Data
		tutData = db.getTutorialData(tutTitle);
		
		//Display tutorial Date
		TextView tv_dateCreated = new TextView(this);
		tv_dateCreated.setText(tutData[1]);
		ll.addView(tv_dateCreated);
		//Display Tutorial Title
		TextView tv_tutTitle = new TextView(this);
		tv_tutTitle.setText(tutData[0]);
		ll.addView(tv_tutTitle);
		//Display Tutorial Text
		TextView tv_tutText = new TextView(this);
		tv_tutText.setText(tutData[2]);
		ll.addView(tv_tutText);
		//Save Tutorial ID
		tutID = tutData[3];
		
		//Get out Comments Data
		comments = db.getCommentText(tutID);
		for (int i=0; i<comments.size(); i++) {
		    //System.out.println(comments.get(i));
			//Display Tutorial Text
			TextView tv = new TextView(this);
			tv.setText(comments.get(i));
			ll.addView(tv);

		}
		
		Button addComment = new Button(this);
		addComment.setText("Add Comment");
		ll.addView(addComment);

		this.setContentView(sv);
		
		addComment.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	//Toast.makeText(getApplicationContext(),
				//"Add Comment goes here", Toast.LENGTH_SHORT).show();
		    	Intent i = new Intent(getApplicationContext(), AddCommentActivity.class);
		        i.putExtra("tutTitle", tutTitle);
		        Log.d(tutTitle, "In the onCreate() event");
		        startActivity(i);
		    }
		});
	}
}
