package com.sc.aizuanshi.dialog;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.aizuanshi.R;
import com.sc.aizuanshi.fragment.FragmentMain;
import com.sc.aizuanshi.util.Config;
import com.sc.aizuanshi.util.Parameters;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class DialogShare extends Dialog implements OnClickListener {

	private String summary = "哈哈，这个APP太牛了,我每天都能够领取几千个钻石。。。";
	private String title = "天天领钻石";
	private String target_url = "http://zuanshi8.aliapp.com";
	private String image_url = "http://bbb.slipperabc.cn/diamond.png";
	private TextView qq;
	private TextView qzone;
	private TextView wechat;
	private TextView wechatmomen;
	private TextView integral;
	private Button wall;
	private Context context;
	private ImageView cancel;
	private LinearLayout layout;
	private Config config;
	private int number;
	private Tencent tencent;
	private IWXAPI api;
	private BroadcastReceiver refurbish;

	public DialogShare(Context context) {
		super(context);
		this.context = context;
	}

	public DialogShare(Context context, int theme) {
		super(context, theme);
		this.context = context;
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.share, null);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout);
		configPlatforms();
		config = Config.getSingle(context);
		preView();
		preData();
		broadcast();
	}

	private void configPlatforms() {
		tencent = Tencent.createInstance(Parameters.QQ_APP_ID, (Activity) context);
		api = WXAPIFactory.createWXAPI(context, Parameters.WEIXIN_APP_ID);
		api.registerApp(Parameters.WEIXIN_APP_ID);
	}

	protected void preView() {
		qq = (TextView) layout.findViewById(R.id.qq);
		qzone = (TextView) layout.findViewById(R.id.qzone);
		wechat = (TextView) layout.findViewById(R.id.wechat);
		wechatmomen = (TextView) layout.findViewById(R.id.wechatmomen);
		cancel = (ImageView) layout.findViewById(R.id.dialog_cancel);
		integral = (TextView) layout.findViewById(R.id.integral);
		wall = (Button) layout.findViewById(R.id.wall);

		qq.setOnClickListener(this);
		qzone.setOnClickListener(this);
		wechat.setOnClickListener(this);
		wechatmomen.setOnClickListener(this);
		cancel.setOnClickListener(this);
		integral.setOnClickListener(this);
		wall.setOnClickListener(this);
	}

	public void preData() {
		number = config.getQq() + config.getQzones() + config.getWechats() + config.getWechatmom();
		String numberStr = context.getResources().getString(R.string.share_number);
		qq.setText(String.format(numberStr, new Object[] { config.getQq(), Config.QQ_MAX }));
		qzone.setText(String.format(numberStr, new Object[] { config.getQzones(), Config.QZONES_MAX }));
		wechat.setText(String.format(numberStr, new Object[] { config.getWechats(), Config.WECHATS_MAX }));
		wechatmomen.setText(String.format(numberStr, new Object[] { config.getWechatmom(), Config.WECHATMOM_MAX }));

		String before = context.getResources().getString(R.string.before);
		before = String.format(before, number);
		integral.setText(before);

	}

	public void broadcast() {
		refurbish = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				preData();
			}
		};

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.sc.aizuanshi.REFURBISH_DATA");
		context.registerReceiver(refurbish, intentFilter);

	}

	public void raise() {
		number = config.getQq() + config.getQzones() + config.getWechats() + config.getWechatmom();
		if (number >= 10) {
			config.setRank(1);
			Toast.makeText(context, "恭喜你，升级成功", Toast.LENGTH_LONG).show();
			this.dismiss();
			Fragment fragment = new FragmentMain();
			FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		} else {
			Toast.makeText(context, "你今天的任务还没有完成...", Toast.LENGTH_LONG).show();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qq:
			shareQQ();
			break;
		case R.id.qzone:
			shareQzones();
			break;
		case R.id.wechat:
			shareWechats();
			break;
		case R.id.wechatmomen:
			shareWechatmom();
			break;
		case R.id.dialog_cancel:
			this.dismiss();
			break;
		case R.id.wall:
			raise();
			break;

		default:
			break;
		}
	}

	public void shareQQ() {
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, target_url);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, image_url);
		tencent.shareToQQ((Activity) context, params, new BaseUiListener());
		Parameters.SHARETYPE = 1;
	}

	public void shareQzones() {
		final Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, target_url);
		ArrayList<String> list = new ArrayList<String>();
		list.add(image_url);
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
		tencent.shareToQzone((Activity) context, params, new BaseUiListener());
		Parameters.SHARETYPE = 2;
	}

	public void shareWechats() {
		if (!api.isWXAppInstalled()) {
			Toast.makeText(context, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
			return;
		}
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = title;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = summary;
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		msg.thumbData = thumb.getNinePatchChunk();
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
		Parameters.SHARETYPE = 3;
	}

	public void shareWechatmom() {
		if (!api.isWXAppInstalled()) {
			Toast.makeText(context, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
			return;
		}
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = title;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = summary;
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		msg.thumbData = thumb.getNinePatchChunk();
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
		Parameters.SHARETYPE = 4;
	}

	public void dismiss() {
		super.dismiss();
		context.unregisterReceiver(refurbish);
	}

	class BaseUiListener implements IUiListener {

		public void onCancel() {
			Toast.makeText(context, "取消分享...", Toast.LENGTH_LONG).show();
		}

		public void onComplete(Object obj) {
			Toast.makeText(context, "分享成功...", Toast.LENGTH_LONG).show();
			if (Parameters.SHARETYPE == 1) {
				config.setQq();
			} else if (Parameters.SHARETYPE == 2) {
				config.setQzones();
			}
			preData();
		}

		public void onError(UiError error) {
			Toast.makeText(context, "分享拒绝...", Toast.LENGTH_LONG).show();
		}
	}

}
