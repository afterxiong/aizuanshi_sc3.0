package com.sc.aizuanshi.fragment;

import net.slidingmenu.tools.os.OffersManager;
import net.slidingmenu.tools.os.PointsManager;
import net.slidingmenu.tools.video.VideoAdManager;
import net.slidingmenu.tools.video.listener.VideoAdListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dlnetwork.DevInit;
import com.dlnetwork.GetTotalMoneyListener;
import com.dlnetwork.GiveMoneyListener;
import com.dlnetwork.SpendMoneyListener;
import com.newqm.sdkoffer.QuMiConnect;
import com.newqm.sdkoffer.QuMiNotifier;
import com.sc.aizuanshi.R;
import com.sc.aizuanshi.util.Config;
import com.sc.aizuanshi.util.Parameters;

public class FragmentScore extends Fragment implements OnClickListener {

	private TextView gold;
	public int qumiNumber = 0;
	private BroadcastReceiver broadcastReceiver;
	private String REFURBISH_GOLD = "com.sc.aizuanshi.REFURBISH_GOLD";
	private Config config;
	private int number = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		OffersManager.getInstance(getActivity()).onAppLaunch();
		VideoAdManager.getInstance(getActivity()).requestVideoAd();
		config = Config.getSingle(getActivity());
		refurbish();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.score, container, false);
		initView(view);
		initDate();
		return view;
	}

	public void initView(View view) {
		gold = (TextView) view.findViewById(R.id.gold);
		TextView pass2 = (TextView) view.findViewById(R.id.pass2);
		TextView pass3 = (TextView) view.findViewById(R.id.pass3);
		TextView pass5 = (TextView) view.findViewById(R.id.pass5);
		TextView dianle = (TextView) view.findViewById(R.id.dianle);
		pass2.setOnClickListener(this);
		pass3.setOnClickListener(this);
		pass5.setOnClickListener(this);
		dianle.setOnClickListener(this);
	}

	public void initDate() {
		String goldNumber = getResources().getString(R.string.gold_tip);
		goldNumber = String.format(goldNumber, amount());
		gold.setText(goldNumber);
	}

	public void refurbish() {
		broadcastReceiver = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent) {
				initDate();
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(REFURBISH_GOLD);
		getActivity().registerReceiver(broadcastReceiver, intentFilter);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dianle:
			DevInit.showOffers(getActivity());
			break;
		case R.id.pass2:
			OffersManager.getInstance(getActivity()).showOffersWall();
			break;
		case R.id.pass3:
			QuMiConnect.getQumiConnectInstance(getActivity()).initOfferAd(getActivity());
			QuMiConnect.getQumiConnectInstance().showOffers(new QuMiNotifier() {

				public void earnedPoints(int pointTotal, int arg1) {

				}

				public void getPoints(int arg0) {
					qumiNumber = arg0;
				}

				public void getPointsFailed(String arg0) {

				}
			});
			break;
		case R.id.pass5:
			promotes();
			break;
		default:
			break;
		}
	}

	public void sendBroadRefurbish() {
		Intent intent = new Intent(REFURBISH_GOLD);
		getActivity().sendBroadcast(intent);
	}

	public void promotes() {
		final AlertDialog.Builder dialog = new Builder(getActivity());
		if (amount() > 50) {
			dialog.setTitle(getActivity().getResources().getString(R.string.hint));
			dialog.setMessage(getActivity().getResources().getString(R.string.upgrade));

			dialog.setNegativeButton(getActivity().getResources().getString(R.string.ensure),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							PointsManager.getInstance(getActivity()).spendPoints(100);
							int len = config.getrRank() >= Parameters.rankId.length - 1 ? Parameters.rankId.length - 1
									: config.getrRank() + 1;
							config.setRank(len);
							Toast.makeText(getActivity(), "�����ɹ�...", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							sendBroadRefurbish();
							number = number - 100;
						}
					});
			dialog.setPositiveButton(getActivity().getResources().getString(R.string.cancel),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getActivity(), "ȡ������...", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}
					});

		} else {
			dialog.setTitle(getActivity().getResources().getString(R.string.hint));
			dialog.setMessage(getActivity().getResources().getString(R.string.lack));
			dialog.setNegativeButton("��ȡ���", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}
		dialog.show();
	}

	int amount_nums = 0;

	public int amount() {

		int pointsBalance = PointsManager.getInstance(getActivity()).queryPoints();
		DevInit.getTotalMoney(getActivity(), new GetTotalMoneyListener() {

			public void getTotalMoneySuccessed(String arg0, long amounts) {
				amount_nums = (int) amounts;
			}

			public void getTotalMoneyFailed(String arg0) {

			}
		});
		return pointsBalance + qumiNumber + amount_nums - number;
	}

	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(broadcastReceiver);
	}
}