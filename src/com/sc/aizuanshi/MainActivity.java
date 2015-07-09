package com.sc.aizuanshi;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sc.aizuanshi.fragment.FragmentMain;
import com.sc.aizuanshi.fragment.FragmentScore;
import com.sc.aizuanshi.util.Game;

public class MainActivity extends BaseActivity {
	private List<Game> listGame = new ArrayList<Game>();
	private BroadcastReceiver broadcastReceiver;
	public static final String OPEN_SCORE = "com.metek.OPEN_SCORE";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sort();
		initFragmentMain();
		openScore();
	}

	public void initFragmentMain() {
		Fragment fragment = new FragmentMain();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	}

	public void initFragmentScore() {
		Fragment fragment = new FragmentScore();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	}

	public void openScore() {
		broadcastReceiver = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent) {
				initFragmentScore();
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(OPEN_SCORE);
		registerReceiver(broadcastReceiver, filter);

	}

	public interface OnDataListener {
		void callback(List<Game> list);
	}

	public void setOnDataListener(OnDataListener listener) {
		listener.callback(listGame);
	}

	public List<Game> sort() {
		for (int i = 0; i < temp.size(); i++) {
			Game game = temp.get(i);
			if (game.getExist() == 1) {
				listGame.add(game);
			}

		}

		for (int i = 0; i < temp.size(); i++) {
			Game game = temp.get(i);
			if (game.getExist() != 1) {
				listGame.add(game);
			}

		}
		return listGame;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_webSearch:
			dialogFollow();
			break;
		case R.id.back:
			initFragmentMain();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void dialogFollow() {
		final AlertDialog.Builder dialog = new Builder(this);
		dialog.setTitle("联系客服");
		dialog.setMessage("请关注我们唯一的微信公众号heiying8,及时获取更多的资讯");
		dialog.setNegativeButton("复制公众号", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				cm.setText("heiying8");
				Toast.makeText(MainActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}
}
