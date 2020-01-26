package in.srijanju.androidapp.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.model.SrijanEvent;

public class EventDescription extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_description);

		SrijanEvent event = (SrijanEvent) getIntent().getExtras().getSerializable("event");

		// Refer to SrijanEvent class for the parameters

		Log.i("event", String.valueOf(event));
	}
}
