package in.srijanju.androidapp.activities;

<<<<<<< HEAD
import android.os.Build;
||||||| merged common ancestors
=======
import android.content.Intent;
>>>>>>> 6c48a05daf57ae7dd7e3cd58311864dea56c0918
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Typeface;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import in.srijanju.androidapp.R;

public class MainPage extends AppCompatActivity {
	private TextView a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		a=(TextView)findViewById(R.id.timer);
		Typeface myCustomFont=getResources().getFont(R.font.timerfont);
		a.setTypeface(myCustomFont);
		initialise();

		/*
		 * Srijan timer logic
		 * */
		final Calendar thatDay = Calendar.getInstance();
		thatDay.setTime(new Date(0)); /* reset */
		thatDay.set(Calendar.DAY_OF_MONTH, 5);
		thatDay.set(Calendar.MONTH, 2); // 0-11 so 1 less
		thatDay.set(Calendar.YEAR, 2020);

		final int SECONDS_IN_A_DAY = 24 * 60 * 60;
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				Calendar today = Calendar.getInstance();
				long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
				long diffSec = diff / 1000;

				long days = diffSec / SECONDS_IN_A_DAY;
				long secondsDay = diffSec % SECONDS_IN_A_DAY;
				long seconds = secondsDay % 60;
				long minutes = (secondsDay / 60) % 60;
				long hours = (secondsDay / 3600); // % 24 not needed
				String s=days+" DAYS \n\n" + hours +" HOURS \n\n to go! ";
				setText(a, s);
			}
		}, 0, 1000);

	}

	private void initialise() {
		a = findViewById(R.id.timer);

		ImageView ivAbout = findViewById(R.id.iv_about);
		ivAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, About.class));
			}
		});
	}

	private void setText(final TextView text, final String value) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				text.setText(value);
			}
		});
	}
}
