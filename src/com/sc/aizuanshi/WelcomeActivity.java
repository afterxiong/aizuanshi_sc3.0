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
		/** ���� */
		AdManager.getInstance(this).init(Parameters.YOU_MI_ID, Parameters.YOU_MI_KEY);
		// ������滺��
		SpotManager.getInstance(this).loadSpotAds();
		// ��������
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
		/** Ȥ�� */
		QuMiConnect.ConnectQuMi(this, Parameters.QU_MI_APP_ID, Parameters.QU_MI_KEY);
		/** ���������ݹ�� */
		QOMan mManager = QOMan.getInstance(this, Parameters.JU_YOU, "baidu");
		mManager.create();
		/**�׵�**/
		 // �Զ���AWActivity ��ȫ�� ,��Manifest.xml��ƥ�� ,ͬʱ��ҪUserActivity�̳���AWActivity
        AdWall.setUserActivity(this,this.getPackageName() +".UserActivity");
        AdWall.init(this, Parameters.MI_DI_ID, Parameters.MI_DI_KEY);
	}

}
