package in.srijanju.androidapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.SrijanActivity;
import in.srijanju.androidapp.controller.SponsorAdapter;
import in.srijanju.androidapp.model.Sponsor;

public class Sponsors extends Fragment {

  final ArrayList<Sponsor> sponsors = new ArrayList<>();
  final SponsorAdapter adapter = new SponsorAdapter(getActivity(), sponsors);
  DatabaseReference ref = null, checkRef = null;

  ChildEventListener eventListener = new ChildEventListener() {
	@Override
	public void onChildAdded(
			@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
	  try {
		Sponsor sponsor = dataSnapshot.getValue(Sponsor.class);
		sponsors.add(sponsor);
		adapter.notifyDataSetChanged();
	  } catch (Exception e) {
		e.printStackTrace();
	  }
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

  private boolean isSponsored = false;
  ValueEventListener sponsoredValueListener = new ValueEventListener() {
	@Override
	public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
	  Boolean isSpon = null;
	  if (dataSnapshot.exists()) isSpon = dataSnapshot.getValue(Boolean.class);
	  if (isSpon == null || !isSpon) {
		isSponsored = false;
		if (ref != null && eventListener != null)
		  ref.removeEventListener(eventListener);
		sponsors.clear();
		adapter.notifyDataSetChanged();
		return;
	  }

	  isSponsored = true;
	  ref.addChildEventListener(eventListener);
	}

	@Override
	public void onCancelled(@NonNull DatabaseError databaseError) {
	}
  };
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_sponsors, container, false);
	}
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);


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

	ListView lvSponsors = getView().findViewById(R.id.lv_sponsors);
	lvSponsors.setAdapter(adapter);
	lvSponsors.setEmptyView(getView().findViewById(R.id.tv_no_sponsor));

	FirebaseDatabase db = FirebaseDatabase.getInstance();
	ref = db.getReference("srijan/sponsors");
	checkRef = db.getReference("srijan/sponsors/isSponsored");
  }


  @Override
  public void onResume() {
	super.onResume();

	checkRef.addValueEventListener(sponsoredValueListener);
	// Get the list of sponsors
	if (isSponsored)
	  ref.addChildEventListener(eventListener);
  }

  @Override
  public void onPause() {
	super.onPause();

	checkRef.removeEventListener(sponsoredValueListener);
	if (ref != null && eventListener != null)
	  ref.removeEventListener(eventListener);
	sponsors.clear();
	adapter.notifyDataSetChanged();
  }
}
