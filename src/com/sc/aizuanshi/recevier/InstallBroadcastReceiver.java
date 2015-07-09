package com.sc.aizuanshi.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class InstallBroadcastReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
			String packageName = intent.getData().getSchemeSpecificPart();
			Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();
		}
		if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
			String packageName = intent.getData().getSchemeSpecificPart();
			Toast.makeText(context, "卸载成功" + packageName, Toast.LENGTH_LONG).show();
		}
		if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
			String packageName = intent.getData().getSchemeSpecificPart();
			Toast.makeText(context, "替换成功" + packageName, Toast.LENGTH_LONG).show();
		}
	}
	
	public void updateState(String packageName){
		
	}

}
