package com.skneppers.tucmp311_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

		 
	    // Logcat tag
	    private static final String LOG = "DBHelper";
	 
	    // Database Version
	    private static final int DATABASE_VERSION = 1;
	 
	    // Database Name
	    private static final String DATABASE_NAME = "tutorials";
	 
	    // Table Names
	    private static final String TABLE_CATEGORIES = "Categories";
	    private static final String TABLE_COMMENTTEXT = "CommentText";
	    private static final String TABLE_IMAGES = "Images";
	    private static final String TABLE_TUTORIALIMAGES = "TutorialImages";
	    private static final String TABLE_TUTORIALS = "Tutorials";
	    private static final String TABLE_TUTORIALVIDEOS = "TutorialVideos";
	    private static final String TABLE_USERS = "Users";
	    private static final String TABLE_VIDEOS = "Videos";
	 
	    // Common column names
	    //private static final String KEY_ID = "id";
	    //private static final String KEY_CREATED_AT = "created_at";
	 
	    // Categories Table - columns names
	    private static final String KEY_CATID = "catID";
	    private static final String KEY_CATNAME = "catName";
	    private static final String KEY_CATDESCRIPTION = "catDescription";
	 
	    // CommentsText Table - column names
	    private static final String KEY_COMMENTID = "commentID";
	    private static final String KEY_COM_USERID = "userID";
	    private static final String KEY_COM_TUTID = "tutID";
	    private static final String KEY_COMMENTDATECREATED = "commentDateCreated";
	    private static final String KEY_COMMENTTEXT = "commentText";
	 
	    // Images Table - column names
	    private static final String KEY_IMAGEID = "imageID";
	    private static final String KEY_IMAGEURL = "imageURL";
	    private static final String KEY_IMAGENAME = "imageName";
	    private static final String KEY_IMAGEDECRIPTION = "imageDescription";
	    
	    //TutorialImages Table - column names
	    private static final String KEY_TI_TUTID = "tutID";
	    private static final String KEY_TI_IMAGEID = "imageID";
	    
	    //Tutorials Table - column names
	    private static final String KEY_TUTID = "tutID";
	    private static final String KEY_TUT_CATID = "catID";
	    private static final String KEY_TUT_USERID = "userID";
	    private static final String KEY_TUTTITLE = "tutTitle";
	    private static final String KEY_TUTDATECREATED = "tutDateCreated";
	    private static final String KEY_TUTTEXT = "tutText";
	    
	    //TutorialVideos Table - column names
	    private static final String KEY_TV_TUTID = "tutID";
	    private static final String KEY_TV_VIDEOID = "videoID";
	    
	    //Users Table - column names
	    private static final String KEY_USERID = "userID";
	    private static final String KEY_USEREMAIL = "userEmail";
	    private static final String KEY_USERFIRSTNAME = "userFirstName";
	    private static final String KEY_USERSURNAME = "userSurname";
	    private static final String KEY_USERPASSWORD = "userPassword";
	    private static final String KEY_USERPASSWORDSALT = "userPasswordSalt";
	    private static final String KEY_USERLEVEL = "userLevel";
	    
	    //Videos Table - column names
	    private static final String KEY_VIDEOID = "videoID";
	    private static final String KEY_VIDEOURL = "videoURL";
	    private static final String KEY_VIDEONAME = "videoName";
	    private static final String KEY_VIDEODECRIPTION = "videoDescription";
	 
	    // Table Create Statements
	    // Categories table create statement
	    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE "
	            + TABLE_CATEGORIES + "(" + KEY_CATID + " INTEGER PRIMARY KEY," + KEY_CATNAME
	            + " TEXT," + KEY_CATDESCRIPTION + " TEXT " + ")";
	 
	    // CommentText table create statement
	    private static final String CREATE_TABLE_COMMENTTEXT = "CREATE TABLE " + TABLE_COMMENTTEXT
	            + "(" + KEY_COMMENTID + " INTEGER PRIMARY KEY," + KEY_COM_USERID + " TEXT,"
	            + KEY_COM_TUTID + " TEXT, " + KEY_COMMENTDATECREATED + " TEXT, " 
	            + KEY_COMMENTTEXT + " TEXT "+ ")";
	 
	    // Images table create statement
	    private static final String CREATE_TABLE_IMAGES = "CREATE TABLE "
	            + TABLE_IMAGES + "(" + KEY_IMAGEID + " INTEGER PRIMARY KEY,"
	            + KEY_IMAGEURL + " TEXT, " + KEY_IMAGENAME + " TEXT, "
	            + KEY_IMAGEDECRIPTION + " TEXT" + ")";
	    // TutorialImages table create statement
	    private static final String CREATE_TABLE_TUTORIALIMAGES = "CREATE TABLE "
	            + TABLE_TUTORIALIMAGES + "(" + KEY_TI_TUTID + " INTEGER ,"
	            + KEY_TI_IMAGEID + " INTEGER" + ")";
	    
	    // Tutorials table create statement
	    private static final String CREATE_TABLE_TUTORIALS = "CREATE TABLE "
	            + TABLE_TUTORIALS + "(" + KEY_TUTID + " INTEGER PRIMARY KEY,"
	            + KEY_TUT_CATID + " INTEGER, " + KEY_TUT_USERID + " INTEGER, "
	            + KEY_TUTTITLE + " TEXT, " + KEY_TUTDATECREATED + " TEXT, " + KEY_TUTTEXT + " TEXT " + ")";
	    
		// TutorialVideos table create statement
	    private static final String CREATE_TABLE_TUTORIALVIDEOS = "CREATE TABLE "
	            + TABLE_TUTORIALVIDEOS + "(" + KEY_TV_TUTID + " INTEGER ,"
	            + KEY_TV_VIDEOID + " INTEGER" + ")";
	    
	    // Users table create statement
	    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
	            + TABLE_USERS + "(" + KEY_USERID + " INTEGER PRIMARY KEY,"
	            + KEY_USEREMAIL + " TEXT, " + KEY_USERFIRSTNAME + " TEXT, "
	            + KEY_USERSURNAME + " TEXT, " + KEY_USERPASSWORD + " TEXT, " 
	            + KEY_USERPASSWORDSALT + " TEXT, " + KEY_USERLEVEL + " TEXT " + ")";
	    
	 // Videos table create statement
	    private static final String CREATE_TABLE_VIDEOS = "CREATE TABLE "
	            + TABLE_VIDEOS + "(" + KEY_VIDEOID + " INTEGER PRIMARY KEY,"
	            + KEY_VIDEOURL + " TEXT, " + KEY_VIDEONAME + " TEXT, "
	            + KEY_VIDEODECRIPTION + " TEXT" + ")";
	    
	 
	    public DBHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION); 	
	    }
	    
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
						
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTTEXT);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORIALIMAGES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORIALS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORIALVIDEOS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);
			
			db.execSQL(CREATE_TABLE_CATEGORIES);
			db.execSQL(CREATE_TABLE_COMMENTTEXT);
			db.execSQL(CREATE_TABLE_IMAGES);
	        db.execSQL(CREATE_TABLE_TUTORIALIMAGES);
	        db.execSQL(CREATE_TABLE_TUTORIALS);
	        db.execSQL(CREATE_TABLE_TUTORIALVIDEOS);
	        db.execSQL(CREATE_TABLE_USERS);
	        db.execSQL(CREATE_TABLE_VIDEOS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTTEXT);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORIALIMAGES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORIALS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORIALVIDEOS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);
		        
		    // create new tables
		    onCreate(db);
			
		}

	    
	    public SQLiteDatabase returnDB(){
	    	SQLiteDatabase sqliteDB = this.getWritableDatabase();
	    	return sqliteDB;
	    }
	    
	    public List<String> getCategoryLabels(){
	    		    	
	        List<String> Categorylabels = new ArrayList<String>();
	         
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES;
	      
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	      
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	                Categorylabels.add(cursor.getString(1));
	            } while (cursor.moveToNext());
	        }
	         
	        // closing connection
	        cursor.close();
	        db.close();
	         
	        // returning lables
	        return Categorylabels;
	    }
	    
	    public String getCategoryID(String catName){
	    	String returnID = null;
	    	//String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + KEY_CATNAME + " = " + catName;
	    	SQLiteDatabase db = this.getReadableDatabase();
	        //Cursor cursor = db.rawQuery(selectQuery, null);
	    	Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + KEY_CATNAME + "= ?", new String[] { catName });
	        //String returnID = cursor.getString(1);
	        while (cursor.moveToNext()){
	        	int colid = cursor.getColumnIndex("catID");
	        	returnID = cursor.getString(colid);
	        }
	        return returnID;
	    }
	    
	    public List<String> getTutorialLabels(String catID){
	    	
	        List<String> TutorialLabels = new ArrayList<String>();
	         
	        // Select All Query
	        //String selectQuery = "SELECT * FROM " + TABLE_TUTORIALS + " WHERE " + KEY_CATID + "= ?", new String[] { catID });
	      
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TUTORIALS + " WHERE " + KEY_CATID + "= ?", new String[] { catID });
	      
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	                TutorialLabels.add(cursor.getString(3));
	            } while (cursor.moveToNext());
	        }
	        //TutorialLabels.add("node 1"); 
	        // closing connection
	        cursor.close();
	        db.close();
	         
	        // returning lables*/
	        return TutorialLabels;
	    }
	    
	    /*public String getTutorialID(String tutTitle)	
	    	{
	    	String returnID = null;
	    	//String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + KEY_CATNAME + " = " + catName;
	    	SQLiteDatabase db = this.getReadableDatabase();
	        //Cursor cursor = db.rawQuery(selectQuery, null);
	    	Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TUTORIALS + " WHERE " + KEY_TUTTITLE + "= ?", new String[] { tutTitle });
	        //String returnID = cursor.getString(1);
	        while (cursor.moveToNext())
	        	{
	        	int colid = cursor.getColumnIndex("tutID");
	        	returnID = cursor.getString(colid);
	        	}
	        return returnID;
	    	}*/

	    
		public String[] getTutorialData(String tutTitle)
	    	{
	    	String[] tutData;
	    	tutData = new String[4];
	    	
	    	SQLiteDatabase db = this.getReadableDatabase();
	        //Cursor cursor = db.rawQuery(selectQuery, null);
	    	Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TUTORIALS + " WHERE " + KEY_TUTTITLE + "= ?", new String[] { tutTitle });
	    	
	    	if (cursor.moveToFirst()) {
	            do {
	            	//Save Tutorial title
	                tutData[0] = cursor.getString(3);
	                // Save Date Created
	                tutData[1] = cursor.getString(4);
	                // Save Tutorial Text
	                tutData[2] = cursor.getString(5);
	                //Save Tutorial ID
	                tutData[3] = cursor.getString(0);
	            } while (cursor.moveToNext());
	        }
	    	
	    	return tutData;
	    	}
		
		public List<String> getCommentText(String tutID)
    	{
		List<String> comments = new ArrayList<String>();
    	
    	SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
    	Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COMMENTTEXT + " WHERE " + KEY_COM_TUTID + "= ?", new String[] { tutID });
    	
    	if (cursor.moveToFirst()) {
            do {
            	//Save User ID
            	comments.add(cursor.getString(1));
            	//Save Comment Date
            	comments.add(cursor.getString(3));
            	//Save Comment Text
            	comments.add(cursor.getString(4));
            } while (cursor.moveToNext());
        }
    	
    	return comments;
    	}
		
		public void insertComment(HashMap<String, String> queryValues) {
	        //SQLiteDatabase database = this.getWritableDatabase();
	        //ContentValues values = new ContentValues();
	        //values.put("userComment", queryValues.get("userComment"));
	        //values.put("udpateStatus", "no");
	        //database.insert("comments", null, values);
	        //database.close();
	    }


}
