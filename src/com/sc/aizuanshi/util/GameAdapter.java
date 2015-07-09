package com.sc.aizuanshi.util;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sc.aizuanshi.R;

public class GameAdapter extends BaseAdapter {
	private ViewHolder vh;
	private Context context;
	private List<Game> list;

	public GameAdapter(Context context, List<Game> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final Game game = list.get(position);
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.game_item, null);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.install = (ImageView) convertView.findViewById(R.id.install);
		vh.tip = (TextView) convertView.findViewById(R.id.game_tip);
		vh.tip.setText(game.getName());

		Drawable drawable = context.getResources().getDrawable(Parameters.iconId[game.getId()]);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		vh.tip.setCompoundDrawables(drawable, null, null, null);

		if (game.getExist() == 1) {
			vh.install.setVisibility(View.VISIBLE);
		} else {
			vh.install.setVisibility(View.GONE);
		}
		this.notifyDataSetChanged();
		return convertView;
	}
}

class ViewHolder {
	TextView tip;
	ImageView install;
}
