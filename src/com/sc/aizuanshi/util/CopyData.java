package com.sc.aizuanshi.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;

import com.sc.aizuanshi.R;

public class CopyData {
	private static final String TAG = "CopyData";
	public static final int DB_VERSION = 1;

	public static boolean extractDatabase(Context context, String name) {
		boolean retVal = false;
		// 获得数据存储的位置 ----/data/data/com.metek.copy/databases/city.db
		File db = context.getDatabasePath(name);
		if (!db.exists()) {
			File directory = db.getParentFile();
			directory.mkdirs();
			if (directory.exists()) {
				try {
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(db));
					InputStream in = context.getResources().openRawResource(R.raw.game_info);
					byte[] buf = new byte[1024 * 8];
					int length = 0;
					while (-1 != (length = in.read(buf))) {
						bos.write(buf, 0, length);
					}
					in.close();
					bos.close();
					retVal = true;
				} catch (FileNotFoundException ex) {
					Log.e(TAG, "Can not access db file.", ex);
				} catch (IOException ex) {
					Log.e(TAG, "Access db error.", ex);
				}
			} else {
				Log.e(TAG, "Can not make db directory.");
			}
		} else {
			retVal = true;
		}
		if (!retVal) {
			Log.e(TAG, "extractDatabase error");
			if (db.exists()) {
				db.delete();
			}
		}
		return retVal;
	}

}
