package com.zms.wechatrecorder;

import java.util.ArrayList;
import java.util.List;

import com.zms.wechatrecorder.view.AudioRecordButton;
import com.zms.wechatrecorder.view.AudioRecordButton.AudioRecordFinishListener;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * TODO: 1.录音时，如果有正在播放的音频，需要暂停 2.音频对话框长度需要根据录音时间长短变化
 */

public class MainActivity extends Activity {
	private AudioRecordButton btnRecord;
	private ListView voiceList;
	private ArrayAdapter<Recorder> mAdapter;
	private List<Recorder> mDatas = new ArrayList<Recorder>();

	private AnimationDrawable animation;
	private View voiceAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		voiceList = (ListView) findViewById(R.id.voiceList);
		btnRecord = (AudioRecordButton) findViewById(R.id.btnRecord);
		btnRecord
				.setAudioRecordFinishListener(new MyAudioRecordFinishListener());

		mAdapter = new VoiceListAdapter(this, mDatas);
		voiceList.setAdapter(mAdapter);
		voiceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 播放动画
				if (animation != null) {
					voiceAnim
							.setBackgroundResource(R.drawable.icon_voice_ripple);
					voiceAnim = null;
				}
				voiceAnim = view.findViewById(R.id.voiceAnim);
				voiceAnim.setBackgroundResource(R.drawable.anim_play_audio);
				animation = (AnimationDrawable) voiceAnim.getBackground();
				animation.start();
				// 播放音频
				MediaManager.playSound(mDatas.get(position).filePath,
						new MediaPlayer.OnCompletionListener() {
							@Override
							public void onCompletion(MediaPlayer mp) {
								voiceAnim
										.setBackgroundResource(R.drawable.icon_voice_ripple);
							}
						});

			}
		});
	}

	class MyAudioRecordFinishListener implements AudioRecordFinishListener {

		@Override
		public void onFinish(float second, String filePath) {
			// TODO Auto-generated method stub
			Recorder recorder = new Recorder(second, filePath);
			mDatas.add(recorder);
			mAdapter.notifyDataSetChanged();
			voiceList.setSelection(mDatas.size() - 1);
		}

	}

	class Recorder {
		float audioLength;
		String filePath;

		public Recorder(float audioLength, String filePath) {
			super();
			this.audioLength = audioLength;
			this.filePath = filePath;
		}

		public float getAudioLength() {
			return audioLength;
		}

		public void setAudioLength(float audioLength) {
			this.audioLength = audioLength;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MediaManager.release();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MediaManager.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MediaManager.resume();
	}

}
