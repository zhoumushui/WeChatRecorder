package com.zms.wechatrecorder;

import java.util.List;

import com.zms.wechatrecorder.MainActivity.Recorder;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VoiceListAdapter extends ArrayAdapter<Recorder> {

	private List<Recorder> mDatas;
	private Context context;

	private int minItemWidth;
	private int maxItemWidth;

	private LayoutInflater inflater;

	public VoiceListAdapter(Context context, List<Recorder> datas) {
		super(context, -1, datas);
		this.context = context;
		mDatas = datas;

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		maxItemWidth = (int) (outMetrics.widthPixels * 0.8);
		maxItemWidth = (int) (outMetrics.widthPixels * 0.2);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.list_item_voice, parent,
					false);
			holder = new ViewHolder();
			holder.seconds = (TextView) convertView
					.findViewById(R.id.textLength);
			holder.length = convertView.findViewById(R.id.voiceAnim);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.seconds.setText(Math.round(getItem(position).audioLength) + "\"");
//		ViewGroup.LayoutParams params = holder.length.getLayoutParams();
//		params.width = (int) (minItemWidth + maxItemWidth / 60f
//				* getItem(position).audioLength);
//		holder.length.setLayoutParams(params);

		return convertView;
	}

	private class ViewHolder {
		TextView seconds;
		View length;
	}

}
