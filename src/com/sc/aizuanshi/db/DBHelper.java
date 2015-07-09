package com.sc.aizuanshi.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sc.aizuanshi.util.Parameters;

public class DBHelper extends SQLiteOpenHelper {
	private static String DB_NAME = "game_info.db";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	public void onCreate(SQLiteDatabase db) {

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public Cursor queryAll() {
		String sql = "select a._id,a.name,a.package,a.exist,b.qq,b.qzones,b.wechats,b.wechatmom from game as a,share as b where a._id==b.g_id";
		SQLiteDatabase db = getReadableDatabase();
		return db.rawQuery(sql, null);
	}

	public Cursor queryPackage() {
		String sql = "select package,_id from game";
		SQLiteDatabase db = getReadableDatabase();
		return db.rawQuery(sql, null);
	}

	public void updateSate(int id) {
		String sql = "update game set exist=? where _id=?";
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql, new Object[] { 1, id });
	}

	public void updateShareQQ(int id) {
		int number = 0;
		SQLiteDatabase db = getWritableDatabase();
		String one = "select qq from share where g_id=?";
		Cursor c = db.rawQuery(one, new String[] { String.valueOf(id) });
		if (c.moveToNext() && c != null) {
			number = c.getInt(0);
			if (number >= Parameters.QQ_RESTRICT) {
				return;
			}
		}
		String sql = "update share set qq=? ";
		db.execSQL(sql, new Object[] { number + 1 });
	}

	public void updateShareQzones(int id) {
		SQLiteDatabase db = getWritableDatabase();
		int number = 0;
		String one = "select qzones from share where g_id=?";
		Cursor c = db.rawQuery(one, new String[] { String.valueOf(id) });
		if (c.moveToNext() && c != null) {
			number = c.getInt(0);
			if (number >= Parameters.QZONES_RESTRICT) {
				return;
			}
		}
		String sql = "update share set qzones=? ";
		db.execSQL(sql, new Object[] { number + 1});
	}

	public void updateShareWeahts(int id) {
		SQLiteDatabase db = getWritableDatabase();
		int number = 0;
		String one = "select wechats from share where g_id=?";
		Cursor c = db.rawQuery(one, new String[] { String.valueOf(id) });
		if (c.moveToNext() && c != null) {
			number = c.getInt(0);
			if (number >= Parameters.WECHATS_RESTRICT) {
				return;
			}
		}
		String sql = "update share set wechats=?";
		db.execSQL(sql, new Object[] { number + 1});
	}

	public void updateShareWechatmom(int id) {
		SQLiteDatabase db = getWritableDatabase();
		int number = 0;
		String one = "select wechatmom from share where g_id=?";
		Cursor c = db.rawQuery(one, new String[] { String.valueOf(id) });
		if (c.moveToNext() && c != null) {
			number = c.getInt(0);
			if (number >= Parameters.WECHATMOM_RESTRICT) {
				return;
			}
		}
		String sql = "update share set wechatmom=?";
		db.execSQL(sql, new Object[] { number + 1, });
	}

	public void clean() {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "update share set qq=?,qzones=?,wechats=?,wechatmom=?";
		db.execSQL(sql, new Object[] { 0, 0, 0, 0 });
	}
}
