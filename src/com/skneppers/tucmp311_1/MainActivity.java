package com.skneppers.tucmp311_1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.skneppers.tucmp311_1.JSONParser;
import com.skneppers.tucmp311_1.DBHelper;
import com.skneppers.tucmp311_1.ConnectionDetector;
import com.skneppers.tucmp311_1.AlertDialogManager;

public class MainActivity extends Activity {
	
	//Connection detector
	ConnectionDetector cd;
	
	// Alert Dialog
	AlertDialogManager alert = new AlertDialogManager(); 
	
	// Database Helper
    DBHelper db;
    
 // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    
 // url to get all products list
    private static String url_all_categories = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAllCategories.php";
    private static String TABLE_CATEGORIES = "Categories";
    
    private static String url_all_commenttext = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAllComments.php";
    private static String TABLE_COMMENTTEXT = "CommentText";
    
    private static String url_all_images = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAllImages.php";
    private static String TABLE_IMAGES = "Images";
    
    private static String url_all_tutorialimages = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAllTutorialImages.php";
    private static String TABLE_TUTORIALIMAGES = "TutorialImages";
    
    private static String url_all_tutorials = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAlltutorials.php";
    private static String TABLE_TUTORIALS = "Tutorials";
    
    private static String url_all_tutorialvideos = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAllTutorialVideos.php";
    private static String TABLE_TUTORIALVIDEOS = "TutorialVideos";
    
    //private static String url_all_users = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAllCategories.php";
    //private static String TABLE_USERS = "Users";
    
    private static String url_all_videos = "http://ast.ncl-coll.ac.uk/~skneppers/TUCMP311/json/getAllVideos.php";
    private static String TABLE_VIDEOS = "Videos";
    
    // JSON Category Node names
    private static final String KEY_CATSUCCESS = "Category success";
    private static final String KEY_CATEGORIES = "categories";
    private static final String KEY_CATID = "catID";
    private static final String KEY_CATNAME = "catName";
    private static final String KEY_CATDESCRIPTION = "catDescription";
    
    // JSON CommentText Node names
    private static final String KEY_COMSUCCESS = "Comments success";
    private static final String KEY_COMMENTS = "comments";
    private static final String KEY_COMMENTID = "commentID";
    private static final String KEY_COM_USERID = "userID";
    private static final String KEY_COM_TUTID = "tutID";
    private static final String KEY_COMMENTDATECREATED = "commentDateCreated";
    private static final String KEY_COM_TEXT = "commentText";
    
    // JSON Images Node names
    private static final String KEY_IMGSUCCESS = "Images success";
    private static final String KEY_IMAGES = "images";
    private static final String KEY_IMAGEID = "imageID";
    private static final String KEY_IMAGEURL = "imageURL";
    private static final String KEY_IMAGENAME = "imageName";
    private static final String KEY_IMAGEDESCRIPTION = "imageDescription";
    
    // JSON TutorialImages Node names
    private static final String KEY_TUTIMGSUCCESS = "TutorialImages success";
    private static final String KEY_TUTORIALIMAGES = "tutorialimages";
    private static final String KEY_IMG_TUTID = "tutID";
    private static final String KEY_IMG_IMAGEID = "imageID";
    
   // JSON Tutorials Node names
    private static final String KEY_TUTSUCCESS = "Tutorials success";
    private static final String KEY_TUTORIALS = "tutorials";
    private static final String KEY_TUTID = "tutID";
    private static final String KEY_TUT_CATID = "catID";
    private static final String KEY_TUT_USERID = "userID";
    private static final String KEY_TUTTITLE = "tutTitle";
    private static final String KEY_TUTDATECREATED = "tutDateCreated";
    private static final String KEY_TUTTEXT = "tutText";
    
    // JSON VideoImages Node names
    private static final String KEY_TUTVIDSUCCESS = "TutorialVideos success";
    private static final String KEY_TUTORIALVIDEOS = "tutorialvideos";
    private static final String KEY_VID_TUTID = "tutID";
    private static final String KEY_VID_VIDEOID = "videoID";
    
    // JSON Videos Node names
    private static final String KEY_VIDSUCCESS = "Videos success";
    private static final String KEY_VIDEOS = "videos";
    private static final String KEY_VIDEOID = "videoID";
    private static final String KEY_VIDEOURL = "videoURL";
    private static final String KEY_VIDEONAME = "videoName";
    private static final String KEY_VIDEODESCRIPTION = "videoDescription";

    // Tutorials JSONArray
    JSONArray categories = null;
    JSONArray comments = null;
    JSONArray images = null;
    JSONArray tutorialimages = null;
    JSONArray tutorials = null;
    JSONArray tutorialvideos = null;
    //JSONArray users = null;
    JSONArray videos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		cd = new ConnectionDetector(getApplicationContext());
		}
	
	public void onClick(View v) 
	{
		// Check for Internet connection
		if (!cd.isConnectingToInternet()) 
			{
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this, "Internet connection ERROR!", 
				"Please connect to working internet connection, or click next to go to stored resources.", false);
			// stop executing code by return
			return;
			}
		else
			{
			db = new DBHelper(getApplicationContext());
			db.getWritableDatabase();
			new LoadAllData().execute();
			}
	}
	
	public void onClickNext(View v) 
	{
    	Intent i = new Intent(getApplicationContext(), AllCategoriesActivity.class);
    	startActivity(i);
    }
	
	
	/**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllData extends AsyncTask<String, String, String> {
    	
    	
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading all data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
	
	protected String doInBackground(String... args) 
		{
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject jsonCat = jParser.makeHttpRequest(url_all_categories, "GET", params);
        JSONObject jsonCom = jParser.makeHttpRequest(url_all_commenttext, "GET", params);
        JSONObject jsonImg = jParser.makeHttpRequest(url_all_images, "GET", params);
        JSONObject jsonTutImg = jParser.makeHttpRequest(url_all_tutorialimages, "GET", params);
        JSONObject jsonTut = jParser.makeHttpRequest(url_all_tutorials, "GET", params);
        JSONObject jsonTutVid = jParser.makeHttpRequest(url_all_tutorialvideos, "GET", params);
        //JSONObject jsonUsers = jParser.makeHttpRequest(url_all_users, "GET", params);
        JSONObject jsonVid = jParser.makeHttpRequest(url_all_videos, "GET", params);
        
        SQLiteDatabase sqliteDB = db.returnDB();
        
        		
        // Check your log cat for JSON reponse
        Log.d("All Categories: ", jsonCat.toString());
        Log.d("All CommentText: ", jsonCom.toString());
        Log.d("All Images: ", jsonImg.toString());
        Log.d("All TutorialImages: ", jsonTutImg.toString());
        Log.d("All Tutorials: ", jsonTut.toString());
        Log.d("All TutorialVideos: ", jsonTutVid.toString());
        //Log.d("All Users: ", jsonUsers.toString());
        Log.d("All Videos: ", jsonVid.toString());
        

        try {
            // Checking for SUCCESS TAG
            int success = jsonCat.getInt(KEY_CATSUCCESS);
            
            if (success == 1) 
            	{
                // categories found
                categories = jsonCat.getJSONArray(KEY_CATEGORIES);

                // looping through All Categories
                for (int icat = 0; icat < categories.length(); icat++) 
                	{
                    JSONObject cat = categories.getJSONObject(icat);

                    // Storing each json item in variable
                    String id = cat.getString(KEY_CATID);
                    String name = cat.getString(KEY_CATNAME);
                    String description = cat.getString(KEY_CATDESCRIPTION);

                    ContentValues values = new ContentValues();
                    values.put(KEY_CATID, id);
                    values.put(KEY_CATNAME, name);
                    values.put(KEY_CATDESCRIPTION, description);

                    // insert row
                    //db = this.getWritableDatabase();
                    long cat_id = sqliteDB.insert(TABLE_CATEGORIES, null, values);
                	}
            	}
               
            int success1 = jsonCom.getInt(KEY_COMSUCCESS);
                
            if (success1 == 1) 
            	{
            	// comments found
            	comments = jsonCom.getJSONArray(KEY_COMMENTS);
                
            	// looping through all Comments    
            	for (int icom = 0; icom < comments.length(); icom++) 
            		{
            		JSONObject com = comments.getJSONObject(icom);
                        
                    // Storing each json item in variable
                    String comid = com.getString(KEY_COMMENTID);
                    String comuserid = com.getString(KEY_COM_USERID);
                    String comtutid = com.getString(KEY_COM_TUTID);
                    String comdate = com.getString(KEY_COMMENTDATECREATED);
                    String comtext = com.getString(KEY_COM_TEXT);

                    ContentValues values = new ContentValues();
                    values.put(KEY_COMMENTID, comid);
                    values.put(KEY_COM_USERID, comuserid);
                    values.put(KEY_COM_TUTID, comtutid);
                    values.put(KEY_COMMENTDATECREATED, comdate);
                    values.put(KEY_COM_TEXT, comtext);

                    // insert row
                    //db = this.getWritableDatabase();
                    long com_id = sqliteDB.insert(TABLE_COMMENTTEXT, null, values);
            		}
            	}
            
            int success2 = jsonImg.getInt(KEY_IMGSUCCESS);
               
            if (success2 == 1) 
            	{
            	images = jsonImg.getJSONArray(KEY_IMAGES);
                
            	// looping through all Comments    
            	for (int iimg = 0; iimg < images.length(); iimg++) 
            		{
                    JSONObject img = images.getJSONObject(iimg);
                        
                    // Storing each json item in variable
                    String imgid = img.getString(KEY_IMAGEID);
                    String imgurl = img.getString(KEY_IMAGEURL);
                    String imgname = img.getString(KEY_IMAGENAME);
                    String imgdecription = img.getString(KEY_IMAGEDESCRIPTION);
                        
                    ContentValues values = new ContentValues();
                    values.put(KEY_IMAGEID, imgid);
                    values.put(KEY_IMAGEURL, imgurl);
                    values.put(KEY_IMAGENAME, imgname);
                    values.put(KEY_IMAGEDESCRIPTION, imgdecription);
                            

                    // insert row
                    //db = this.getWritableDatabase();
                    long img_id = sqliteDB.insert(TABLE_IMAGES, null, values);
            		}
            	}
            
            int success3 = jsonTutImg.getInt(KEY_TUTIMGSUCCESS);
               
            if (success3 == 1) 
            	{
            	tutorialimages = jsonTutImg.getJSONArray(KEY_TUTORIALIMAGES);
                
            	// looping through all Comments    
            	for (int itutimg = 0; itutimg < tutorialimages.length(); itutimg++) 
            		{
                    JSONObject tutimg = tutorialimages.getJSONObject(itutimg);
                        
                    // Storing each json item in variable
                    String imgtutid = tutimg.getString(KEY_IMG_TUTID);
                    String imgimageid = tutimg.getString(KEY_IMG_IMAGEID);

                    ContentValues values = new ContentValues();
                    values.put(KEY_IMG_TUTID, imgtutid);
                    values.put(KEY_IMG_IMAGEID, imgimageid);

                    // insert row
                    //db = this.getWritableDatabase();
                    long tutimg_id = sqliteDB.insert(TABLE_TUTORIALIMAGES, null, values);
            		}
            	}
            
            int success4 = jsonTut.getInt(KEY_TUTSUCCESS);
               
            if (success4 == 1) 
            	{
            	tutorials = jsonTut.getJSONArray(KEY_TUTORIALS);
                
            	// looping through all Comments    
            	for (int itut = 0; itut < tutorials.length(); itut++) 
            		{
                    JSONObject tut = tutorials.getJSONObject(itut);
                        
                    // Storing each json item in variable
                    String tutid = tut.getString(KEY_TUTID);
                    String tutcatid = tut.getString(KEY_TUT_CATID);
                    String tutuserid = tut.getString(KEY_TUT_USERID);
                    String tuttitle = tut.getString(KEY_TUTTITLE);
                    String tutdatecreated = tut.getString(KEY_TUTDATECREATED);
                    String tuttext = tut.getString(KEY_TUTTEXT);

                    ContentValues values = new ContentValues();
                    values.put(KEY_TUTID, tutid);
                    values.put(KEY_TUT_CATID, tutcatid);
                    values.put(KEY_TUT_USERID, tutuserid);
                    values.put(KEY_TUTTITLE, tuttitle);
                    values.put(KEY_TUTDATECREATED, tutdatecreated );
                    values.put(KEY_TUTTEXT, tuttext);
                            

                    // insert row
                    //db = this.getWritableDatabase();
                    long tut_id = sqliteDB.insert(TABLE_TUTORIALS, null, values);
            		}
            	}
            
            	int success5 = jsonTutVid.getInt(KEY_TUTVIDSUCCESS);
            
            	if (success5 == 1) 
            		{
            		tutorials = jsonTutVid.getJSONArray(KEY_TUTORIALVIDEOS);
                
            		// looping through all Comments    
            		for (int itutvid = 0; itutvid < tutorials.length(); itutvid++) 
            			{
            			JSONObject tutvid = tutorials.getJSONObject(itutvid);
                        
            			// Storing each json item in variable
            			String vidtutid = tutvid.getString(KEY_VID_TUTID);
            			String vidvideoid = tutvid.getString(KEY_VID_VIDEOID);

            			ContentValues values = new ContentValues();
            			values.put(KEY_VID_TUTID, vidtutid);
            			values.put(KEY_VID_VIDEOID, vidvideoid);

                            

            			// insert row
            			//db = this.getWritableDatabase();
            			long tut_id = sqliteDB.insert(TABLE_TUTORIALVIDEOS, null, values);
            			}
            		}
            	
            		int success6 = jsonVid.getInt(KEY_VIDSUCCESS);
                
            		if (success6 == 1) 
                		{
            			videos = jsonVid.getJSONArray(KEY_VIDEOS);
                    
            			// looping through all Comments    
            			for (int iimg = 0; iimg < videos.length(); iimg++) 
                			{
            				JSONObject img = videos.getJSONObject(iimg);
                            
            				// Storing each json item in variable
            				String vidid = img.getString(KEY_VIDEOID);
            				String vidurl = img.getString(KEY_VIDEOURL);
            				String vidname = img.getString(KEY_VIDEONAME);
            				String viddecription = img.getString(KEY_VIDEODESCRIPTION);
                            
            				ContentValues values = new ContentValues();
            				values.put(KEY_VIDEOID, vidid);
            				values.put(KEY_VIDEOURL, vidurl);
            				values.put(KEY_VIDEONAME, vidname);
            				values.put(KEY_VIDEODESCRIPTION, viddecription);
                                
            				// insert row
            				//db = this.getWritableDatabase();
            				long vid_id = sqliteDB.insert(TABLE_VIDEOS, null, values);
                			}
                		}
                
            //} else {
                // no products found
                // Launch Add New product Activity
               // Intent i = new Intent(getApplicationContext(),
                       // NewCategoryActivity.class);
                 //Closing all previous activities
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(i);
            //}
        	} 
        	catch (JSONException e) 
        		{
        		e.printStackTrace();
        		}
        	sqliteDB.close();
        	pDialog.dismiss();
        	return null;
		}

	}
    }
   
    




	
	

