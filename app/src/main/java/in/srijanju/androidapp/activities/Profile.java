package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.controller.EventRegisteredAdapter;
import in.srijanju.androidapp.model.User;

public class Profile extends AppCompatActivity {

  final ArrayList<Pair<String, String>> events = new ArrayList<>();
  final EventRegisteredAdapter adapter = new EventRegisteredAdapter(this, events);
  FirebaseUser user;
  ChildEventListener regEventListener = new ChildEventListener() {
	@Override
	public void onChildAdded(
			@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
	  events.add(Pair.create(String.valueOf(dataSnapshot.child("event").getValue()),
			  String.valueOf(dataSnapshot.child("team").getValue())));
	  adapter.notifyDataSetChanged();
	}

	@Override
	public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
	}

	@Override
	public void onChildRemoved(
			@NonNull DataSnapshot dataSnapshot) {
	  events.remove(Pair.create(dataSnapshot.child("event").toString(),
			  dataSnapshot.child("team").toString()));
	  adapter.notifyDataSetChanged();
	}

	@Override
	public void onChildMoved(
			@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
	}

	@Override
	public void onCancelled(
			@NonNull DatabaseError databaseError) {
	}
  };

  DatabaseReference ref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile);

	user = FirebaseAuth.getInstance().getCurrentUser();
	if (user == null) {
	  Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
	  FirebaseAuth.getInstance().signOut();
	  AuthUI.getInstance().signOut(getApplicationContext());
	  startActivity(new Intent(Profile.this, MainActivity.class));
	  finish();
	  return;
	}

	final TextView tvName = findViewById(R.id.tv_profile_name);
	final TextView tvEmail = findViewById(R.id.tv_profile_email);
	final TextView tvClg = findViewById(R.id.tv_clg);
	final TextView tvDegree = findViewById(R.id.tv_degree);
	final TextView tvCourse = findViewById(R.id.tv_course);
	final TextView tvYear = findViewById(R.id.tv_year);

	FirebaseDatabase.getInstance().getReference("srijan/profile/" + user.getUid() +
			"/parentprofile").addListenerForSingleValueEvent(new ValueEventListener() {
	  @Override
	  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
		User user = dataSnapshot.getValue(User.class);
		if (user == null) {
		  Toast.makeText(Profile.this, "Something weird happened! :(", Toast.LENGTH_SHORT).show();
		  finish();
		  return;
		}
		tvName.setText(user.name);
		tvEmail.setText(user.email);
		tvClg.setText(user.college);
		tvDegree.setText(user.degree);
		tvCourse.setText(String.format("Department of %s", user.course));
		tvYear.setText(String.format("%s year", user.year));
	  }

	  @Override
	  public void onCancelled(@NonNull DatabaseError databaseError) {
	  }
	});

	FirebaseDatabase db = FirebaseDatabase.getInstance();
	ref = db.getReference("srijan/profile/" + user.getUid() + "/events");

	GridView gridView = findViewById(R.id.gv_events);
	gridView.setAdapter(adapter);
  }

  @Override
  protected void onResume() {
	super.onResume();
	ref.addChildEventListener(regEventListener);
  }

  @Override
  protected void onPause() {
	super.onPause();
	ref.removeEventListener(regEventListener);
	events.clear();
  }
}
