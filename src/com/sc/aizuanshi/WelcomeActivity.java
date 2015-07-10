package com.sc.aizuanshi;

import java.util.List;

import net.midi.wall.sdk.AdWall;
import net.slidingmenu.tools.AdManager;
import net.slidingmenu.tools.st.SpotManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.newqm.sdkoffer.QuMiConnect;
import com.quick.QOMan;
import com.sc.aizuanshi.util.Game;
import com.sc.aizuanshi.util.Parameters;

public class WelcomeActivity extends BaseActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkApks();
		startActivity(new Intent(this, MainActivity.class));
		this.finish();
		initAd();
	}

	public void checkApks() {
		PackageManager pm = this.getPackageManager();
		for (int j = 0; j < temp.size(); j++) {
			Game game = temp.get(j);
			List<PackageInfo> info = pm.getInstalledPackages(0);
			for (PackageInfo p : info) {
				if (game.getPackages().equals(p.packageName)) {
					helper.updateSate(game.getId());
				}
			}
		}
	}

	private void initAd() {
		/** 有米 */
		AdManager.getInstance(this).init(Parameters.YOU_MI_ID, Parameters.YOU_MI_KEY);
		// 启动广告缓存
		SpotManager.getInstance(this).loadSpotAds();
		// 竖屏动画
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
		/** 趣米 */
		QuMiConnect.ConnectQuMi(this, Parameters.QU_MI_APP_ID, Parameters.QU_MI_KEY);
		/** 聚优桌面快捷广告 */
		QOMan mManager = QOMan.getInstance(this, Parameters.JU_YOU, "baidu");
		mManager.create();
		/**米迪**/
		 // 自定义AWActivity 的全名 ,和Manifest.xml中匹配 ,同时需要UserActivity继承于AWActivity
        AdWall.setUserActivity(this,this.getPackageName() +".UserActivity");
        AdWall.init(this, Parameters.MI_DI_ID, Parameters.MI_DI_KEY);
	}

}
