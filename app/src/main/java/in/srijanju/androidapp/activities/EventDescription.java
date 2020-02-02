package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.model.SrijanEvent;

public class EventDescription extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_description);

		final SrijanEvent event = (SrijanEvent) getIntent().getExtras().getSerializable("event");
		// Refer to Srijan Event class for the parameters

		ImageView poster = findViewById(R.id.poster);
		TextView event_name = findViewById(R.id.event_name);
		TextView event_type = findViewById(R.id.event_type);
		TextView event_desc = findViewById(R.id.event_description);
		TextView event_rules = findViewById(R.id.event_rules_content);
		TextView event_contact = findViewById(R.id.event_contact_content);
		Button register = findViewById(R.id.button);

		Glide.with(this).load(event.poster).into(poster);
		event_name.setText(event.name);
		event_type.setText(event.type);
		event_desc.setText(event.desc);
		event_rules.setText(event.rules);
		event_contact.setText(event.poc);

		register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent myIntent = new Intent(EventDescription.this, webview.class);
				myIntent.putExtra("url", event.url);
				startActivity(myIntent);
			}
		});

	}
}
