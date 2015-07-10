package com.sc.aizuanshi.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sc.aizuanshi.MainActivity;
import com.sc.aizuanshi.R;
import com.sc.aizuanshi.util.Config;
import com.sc.aizuanshi.util.Parameters;

public class DialogInput extends Dialog implements android.view.View.OnClickListener {
	private Context context;
	private LinearLayout layout;
	private ImageView cancel;
	private Button wall;
	private EditText inputqq;

	public DialogInput(Context context, int theme) {
		super(context, theme);
		this.context = context;
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.input, null);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout);
		perView();
	}

	public void perView() {
		cancel = (ImageView) layout.findViewById(R.id.dialog_cancel);
		wall = (Button) layout.findViewById(R.id.wall);
		inputqq = (EditText) layout.findViewById(R.id.inputqq);
		cancel.setOnClickListener(this);
		wall.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_cancel:
			DialogInput.this.dismiss();
			break;
		case R.id.wall:
			String str = inputqq.getText().toString();
			if (TextUtils.isEmpty(str)) {
				Toast.makeText(context, "QQ不能为空...", Toast.LENGTH_SHORT).show();
				return;
			}
			if (str.length() < 6) {
				Toast.makeText(context, "QQ不正确...", Toast.LENGTH_SHORT).show();
				return;
			}
			DialogInput.this.dismiss();
			final AlertDialog.Builder dialog = new Builder(context);
			dialog.setTitle(context.getResources().getString(R.string.hint));
			String rankTextTip = Parameters.rankId[Config.getSingle(context).getrRank()];
			dialog.setMessage("因腾讯限制，当前" + rankTextTip + "无法领取，请升级更高级别会员来领取钻石，VIP2和VIP3没有限制！");
			dialog.setNegativeButton(context.getResources().getString(R.string.wall_integral),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(MainActivity.OPEN_SCORE);
							context.sendBroadcast(intent);
						}
					});
			dialog.show();
			break;

		default:
			break;
		}
	}

}
