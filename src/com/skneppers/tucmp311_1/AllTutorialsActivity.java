package com.skneppers.tucmp311_1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.View;

import com.skneppers.tucmp311_1.DBHelper;

public class AllTutorialsActivity extends ListActivity {
	
	DBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_all_tutorials);
		String catID = null;
		List<String> TutorialLabels = new ArrayList<String>();
		db = new DBHelper(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    catID = extras.getString("label");
		}
		TutorialLabels = db.getTutorialLabels(catID);
		//ArrayAdapter adapter = new ArrayAdapter(this, R.layout.messages, values);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_all_tutorials, TutorialLabels));
		db.close();

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // On selecting a listview item
		        String title = parent.getItemAtPosition(position).toString();
		        		        
		        Intent i = new Intent(getApplicationContext(), SingleTutorial.class);
		        i.putExtra("tutTitle", title);
		        Log.d(title, "In the onCreate() event");
		        startActivity(i);
			}
		});
		
		}
	
}
