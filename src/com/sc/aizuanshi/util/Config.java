package com.sc.aizuanshi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Config {

	private static Config config = null;
	private static SharedPreferences pref;
	private static Editor editor;
	private static String CONFIG_NAME = "config_name";
	private static String QQ = "qq";
	private static String QZONES = "qzones";
	private static String WECHATS = "wechats";
	private static String WECHATMOM = "wechatmom";
	private static String GOLD = "gold";
	private String RANK = "rank";
	public static int QQ_MAX = 4;
	public static int QZONES_MAX = 1;
	public static int WECHATS_MAX = 4;
	public static int WECHATMOM_MAX = 1;
	public static int SHARE_NUMBER_MAX = 10;

	public Config(Context context) {
		super();
		pref = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
		editor = pref.edit();
	}

	public static Config getSingle(Context context) {
		if (config == null) {
			synchronized (Config.class) {
				if (config == null) {
					config = new Config(context);
				}
			}
		}
		return config;
	}

	// /////////////////////////////////////////////////////////////////////////////////
	public int getrRank() {
		int rank = pref.getInt(RANK, 0);
		if (rank >= 5) {
			rank = 5;
		}
		return rank;
	}

	public void setRank(int rank) {
		editor.putInt(RANK, rank);
		editor.commit();
	}

	// ////////////////////////////////////////////////////////////////////////////////
	public void setQq() {
		int num = getQq() >= QQ_MAX ? QQ_MAX : getQq() + 1;
		editor.putInt(QQ, num);
		editor.commit();
	}

	public int getQq() {
		return pref.getInt(QQ, 0);
	}

	// ////////////////////////////////////////////////////////////////////////////////
	public void setQzones() {
		int num = getQzones() >= QZONES_MAX ? QZONES_MAX : getQzones() + 1;
		editor.putInt(QZONES, num);
		editor.commit();
	}

	public int getQzones() {
		return pref.getInt(QZONES, 0);
	}

	// ////////////////////////////////////////////////////////////////////////////////
	public void setWechats() {
		int num = getWechats() >= WECHATS_MAX ? WECHATS_MAX : getWechats() + 1;
		editor.putInt(WECHATS, num);
		editor.commit();
	}

	public int getWechats() {
		return pref.getInt(WECHATS, 0);
	}

	// ////////////////////////////////////////////////////////////////////////////////
	public void setWechatmom() {
		int num = getWechatmom() >= WECHATMOM_MAX ? WECHATMOM_MAX : getWechatmom() + 1;
		editor.putInt(WECHATMOM, num);
		editor.commit();
	}

	public int getWechatmom() {
		return pref.getInt(WECHATMOM, 0);
	}
	// ////////////////////////////////////////////////////////////////////////////////
	public void setGold(int num) {
		editor.putInt(GOLD, num);
		editor.commit();
	}
	
	public int getGold() {
		return pref.getInt(GOLD, 0);
	}
}
