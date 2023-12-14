package com.sesena.gestionecole;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "gestion_ecole.db";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, 2);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(
				"CREATE TABLE etudiant (" +
						"id integer primary key autoincrement," +
						"nom text," +
						"prenom text," +
						"age integer," +
						"nie text," +
						"id_classe integer" +
				")"
		);
		db.execSQL(
				"CREATE TABLE enseignant (" +
						"id integer primary key autoincrement," +
						"nom text," +
						"prenom text," +
						"id_matiere" +
				")"
		);
		db.execSQL(
				"CREATE TABLE classe (" +
						"id integer primary key autoincrement," +
						"nom text" +

				")"
		);
		db.execSQL(
				"CREATE TABLE matiere (" +
						"id integer primary key autoincrement," +
						"nom text," +
						"id_enseigniant integer" +
				")"
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("drop table if exists etudiant");
		db.execSQL("drop table if exists enseignant");
		db.execSQL("drop table if exists classe");
		db.execSQL("drop table if exists matiere");
		onCreate(db);
	}
	
	
	public boolean insertData(String table_name, ContentValues contentValues){
		SQLiteDatabase db = this.getWritableDatabase();
		
		long result = db.insert(table_name, null, contentValues);
		
		if(result == -1){
			return false;
		}else{
			return true;
		}
	}

	public Cursor getData(String table_name, String col, String condition){
		
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT "+col+" FROM "+ table_name+" WHERE "+condition, null);
	}
	
	
	public boolean updateData(String table_name, ContentValues contentvalue, String condition){
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.update(table_name, contentvalue, condition, null);
		
		return true;
	}

	
	public Integer deleteData(String table_name, String condition){
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(table_name, condition, null);
	}
	
	
}
