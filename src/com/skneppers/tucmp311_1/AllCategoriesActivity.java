package com.skneppers.tucmp311_1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.skneppers.tucmp311_1.DBHelper;

public class AllCategoriesActivity extends ListActivity {
	
	//static final String[] Categories = new String[] {"Android", "iOS", 
	//	"Windows Phone", "PHP", "Java" };
	// Database Helper
    DBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_all_categories);
		
		List<String> Categorylabels = new ArrayList<String>();
		db = new DBHelper(getApplicationContext());
		Categorylabels = db.getCategoryLabels();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_all_categories, Categorylabels));
		db.close();
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    //Toast.makeText(getApplicationContext(),
				//((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			 // On selecting a listview item
		        String label = parent.getItemAtPosition(position).toString();
		        db = new DBHelper(getApplicationContext());
		        String catID = db.getCategoryID(label);
		        //Toast.makeText(getApplicationContext(),
				//catID, Toast.LENGTH_SHORT).show();
		        
		        Intent i = new Intent(getApplicationContext(), AllTutorialsActivity.class);
		        i.putExtra("label", catID);
		        Log.d(label, "In the onCreate() event");
		        startActivity(i);
			}
		});
	}
}
