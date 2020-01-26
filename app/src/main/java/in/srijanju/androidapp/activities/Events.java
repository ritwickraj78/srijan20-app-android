package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.controller.EventAdapter;
import in.srijanju.androidapp.model.SrijanEvent;

public class Events extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);

		// The list of events
		final ArrayList<SrijanEvent> events = new ArrayList<>();

		GridView gridView = findViewById(R.id.gv_events);

		// Open the event description page to show the details of the event
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(Events.this, EventDescription.class);
				// Put the event object of the event that was clicked
				Bundle bundle = new Bundle();
				bundle.putSerializable("event", events.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		final EventAdapter adapter = new EventAdapter(this, events);
		gridView.setAdapter(adapter);

		FirebaseDatabase db = FirebaseDatabase.getInstance();
		DatabaseReference ref = db.getReference("srijan/events");

		// Get the list of events
		ref.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(
				@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				SrijanEvent e = dataSnapshot.getValue(SrijanEvent.class);
				events.add(e);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onChildChanged(
				@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onChildRemoved(
				@NonNull DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(
				@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onCancelled(
				@NonNull DatabaseError databaseError) {

			}
		});
	}
}