package com.sc.aizuanshi.fragment;

import java.util.List;
import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sc.aizuanshi.MainActivity;
import com.sc.aizuanshi.MainActivity.OnDataListener;
import com.sc.aizuanshi.R;
import com.sc.aizuanshi.dialog.DialogGameList;
import com.sc.aizuanshi.util.Config;
import com.sc.aizuanshi.util.Game;
import com.sc.aizuanshi.util.Parameters;

public class FragmentMain extends Fragment {
	private TextView rank, tip;
	private Button acquire;
	private List<Game> listGame;
	private Config config;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = Config.getSingle(getActivity());
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main_content, container, false);
		initView(view);
		initData();
		gainGold();
		return view;
	}

	public void initView(View view) {
		rank = (TextView) view.findViewById(R.id.rank);
		tip = (TextView) view.findViewById(R.id.tip);
		acquire = (Button) view.findViewById(R.id.acquire);
		MainActivity mainActivity = (MainActivity) getActivity();
		mainActivity.setOnDataListener(new OnDataListener() {

			public void callback(List<Game> list) {
				listGame = list;
			}
		});
	}

	public void initData() {

		String rankStr = getResources().getString(R.string.rank);
		rankStr = String.format(rankStr, Parameters.rankId[config.getrRank()]);
		rank.setText(rankStr);

		String rollText = getResources().getString(R.string.roll_text);
		String str = "";
		int[] num = { 1000, 2000, 3000 };
		for (int i = 0; i < 10; i++) {
			int q1 = new Random().nextInt(999);
			int q2 = new Random().nextInt(9999);
			int n = new Random().nextInt(2);
			rankStr = String.format(rollText, new Object[] { q1, q2, num[n] });
			str = str + "    " + rankStr;
		}
		tip.setText(str);
	}

	public void gainGold() {
		acquire.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				tipDialog();
			}
		});
	}

	public void tipDialog() {

		DialogGameList dialogGameList = new DialogGameList(getActivity(), R.style.Game_dialog, listGame);
		dialogGameList.setCancelable(false);
		dialogGameList.show();
		
	}

}
