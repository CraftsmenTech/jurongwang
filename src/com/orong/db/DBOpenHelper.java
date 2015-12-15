package com.orong.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "orong.db";
	public static final int DATABASE_VERSION = 1;

	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createMessageTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private void createMessageTable(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS " + DBConstants.Message.TABLENAME + " (" + DBConstants.Message._ID + " integer primary key autoincrement,"
				+ DBConstants.Message.MESSAGEID + "  varchar(20) unique," + DBConstants.Message.TITLE + " varchar(30)," + DBConstants.Message.SENDTIME + " integer,"
				+ DBConstants.Message.FLAGE + " integer," + DBConstants.Message.CONTENT + " TEXT )";
		db.execSQL(sql);

	}
}
