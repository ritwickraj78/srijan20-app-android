package in.srijanju.androidapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.controller.EventAdapter;
import in.srijanju.androidapp.model.SrijanEvent;

import static in.srijanju.androidapp.view.MainPage.SRIJAN_WORKSHOP_URL;

public class Events extends Fragment {

  final ArrayList<SrijanEvent> events = new ArrayList<>();

  EventAdapter adapter;

  ChildEventListener eventListener = new ChildEventListener() {
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
  };
  DatabaseReference ref;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	return inflater.inflate(R.layout.activity_events, container, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	adapter = new EventAdapter(getActivity(), events);

	FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
	if (user == null) {
	  Toast.makeText(getActivity(), "Not logged in", Toast.LENGTH_SHORT).show();
	  FirebaseAuth.getInstance().signOut();
	  AuthUI.getInstance().signOut(getContext());
	  Intent intent = new Intent(getActivity(), MainActivity.class);
	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	  startActivity(intent);
	  return;
	}


	GridView gridView = getView().findViewById(R.id.gv_events);

	// Open the event description page to show the details of the event
	gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	  @Override
	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(getActivity(), EventDescription.class);
		// Put the event object of the event that was clicked
		Bundle bundle = new Bundle();
		bundle.putSerializable("event", events.get(position));
		intent.putExtras(bundle);
		startActivity(intent);
	  }
	});
	gridView.setAdapter(adapter);

	getView().findViewById(R.id.tv_works).setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SRIJAN_WORKSHOP_URL));
		startActivity(browserIntent);
	  }
	});

	FirebaseDatabase db = FirebaseDatabase.getInstance();
	ref = db.getReference("srijan/events");
  }

  @Override
  public void onResume() {
	super.onResume();

	// Get the list of events
	ref.addChildEventListener(eventListener);
  }

  @Override
  public void onPause() {
	super.onPause();
	ref.removeEventListener(eventListener);
	events.clear();
  }
}