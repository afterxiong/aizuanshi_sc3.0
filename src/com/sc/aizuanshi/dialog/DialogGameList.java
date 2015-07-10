package com.sc.aizuanshi.dialog;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.aizuanshi.R;
import com.sc.aizuanshi.util.Config;
import com.sc.aizuanshi.util.Game;
import com.sc.aizuanshi.util.GameAdapter;

public class DialogGameList extends Dialog {

	private Context context;
	private LinearLayout layout;
	private ListView listView;
	private List<Game> list;
	private ImageView cancel;
	private GameAdapter adapter;

	public DialogGameList(Context context, int theme, List<Game> list) {
		super(context, theme);
		this.context = context;
		this.list = list;
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.game_list, null);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout);
		preView();
	}

	public void preView() {
		cancel = (ImageView) layout.findViewById(R.id.dialog_cancel);
		listView = (ListView) layout.findViewById(R.id.game_list);
		adapter = new GameAdapter(context, list);
		listView.setAdapter(adapter);

		cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				DialogGameList.this.dismiss();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Game game = list.get(position);
				if (game.getExist() == 1) {
					DialogGameList.this.dismiss();
					
					if (Config.getSingle(context).getrRank() < 1) {
						
						final AlertDialog.Builder dialog = new Builder(context);
						dialog.setTitle(context.getResources().getString(R.string.hint));
						dialog.setMessage(context.getString(R.string.tip_text1));
						dialog.setNegativeButton(context.getResources().getString(R.string.share_integral),
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										Dialog sharedialog = new DialogShare(context, R.style.Theme_dialog);
										sharedialog.setCancelable(false);
										sharedialog.show();
									}
								});
						dialog.show();
					} else {
						DialogInput dialogInput = new DialogInput(context, R.style.processDialog);
						dialogInput.setCancelable(false);
						dialogInput.show();
					}
					
					
				} else {
					View layout = LayoutInflater.from(context).inflate(R.layout.toast, null);
					TextView textarning = (TextView) layout.findViewById(R.id.warning);
					textarning.getBackground().setAlpha(150);
					Toast toast = new Toast(context);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setView(layout);
					toast.show();
				}

			}
		});
	}

}
