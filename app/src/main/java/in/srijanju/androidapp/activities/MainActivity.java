package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.utils.Screen;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Make the activity fullscreen
		Screen.makeFullscreen(this);

		// Go to the main page after 2 seconds
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					startActivity(new Intent(MainActivity.this, MainPage.class));
					finish();
				}
			}
		}).start();
	}
}
