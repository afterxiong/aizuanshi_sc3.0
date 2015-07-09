package com.sc.aizuanshi;

import java.util.ArrayList;
import java.util.List;

import com.sc.aizuanshi.db.DBHelper;
import com.sc.aizuanshi.util.CopyData;
import com.sc.aizuanshi.util.Game;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

public class BaseActivity extends Activity {
	public DBHelper helper;
	public List<Game> temp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeData();
		initialize();
		System.out.println("∏∏¿‡ µ––");
	}

	private void initializeData() {
		CopyData.extractDatabase(this, "game_info.db");
	}

	private void initialize() {
		helper = new DBHelper(this);
		Cursor cursor = helper.queryAll();
		temp = new ArrayList<Game>();
		while (cursor.moveToNext()) {
			Game game = new Game();
			game.setId(cursor.getInt(0));
			game.setName(cursor.getString(1));
			game.setPackages(cursor.getString(2));
			game.setExist(cursor.getInt(3));
			game.setQq(cursor.getInt(4));
			game.setQzones(cursor.getInt(5));
			game.setWechats(cursor.getInt(6));
			game.setWechatmom(cursor.getInt(7));
			temp.add(game);
		}
	}
}
