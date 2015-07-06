package vocab.sat.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBhelper extends SQLiteOpenHelper{
	private static final String CREATE_TABLE= "create table " + Constants.TABLE_NAME+" ("+
	Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.TITLE_NAME+" text not null, "+ Constants.CONTENT_NAME+" text not null, "+
	Constants.DATE_NAME+" long);";
	private static final String CREATE_TABLE2= "create table " + Constants.TABLE_NAME2+" ("+
	Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.TITLE_NAME+" text not null, "+ Constants.CONTENT_NAME+" text not null, "+
	Constants.DATE_NAME+" long);";
	public MyDBhelper(Context context, String name, CursorFactory factory, int version){
		super(context, name, factory, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db){
		Log.v("MyDBhelper onCreate","Creating all the tables");
		try{
			db.execSQL(CREATE_TABLE);
			db.execSQL(CREATE_TABLE2);
		}catch(SQLiteException ex){
			Log.e("Create table exception", ex.getMessage());
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		Log.w("TaskDBAdapter", "Upgarding from version " + oldVersion +" to "+newVersion +", which will destory all old data");
		db.execSQL("drop table if exists " + Constants.TABLE_NAME);
		db.execSQL("drop table if exists " + Constants.TABLE_NAME2);
		onCreate(db);
	}

}
