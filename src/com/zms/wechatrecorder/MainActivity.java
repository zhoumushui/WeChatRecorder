package com.zms.wechatrecorder;

import com.zms.wechatrecorder.view.AudioRecorderButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private AudioRecorderButton btnRecorder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnRecorder = (AudioRecorderButton) findViewById(R.id.btnRecorder);
	}

}
