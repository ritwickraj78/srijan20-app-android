package in.srijanju.androidapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.SrijanActivity;


public class Gallery extends Fragment {
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_gallery, container, false);
	}
	@Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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

	// Gallery images list view
	final ListView lvGallery = getView().findViewById(R.id.lv_gallery);
	// Store the gallery images' link
	final ArrayList<String> galleryList = new ArrayList<>();

	final BaseAdapter galleryAdapter = new BaseAdapter() {
	  @Override
	  public int getCount() {
		return galleryList.size();
	  }

	  @Override
	  public Object getItem(int position) {
		return galleryList.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
		return position;
	  }

	  @Override
	  public View getView(
			  int position, View convertView, ViewGroup parent) {
		// Gallery item is just an image
		View v;
		if (convertView == null) {
		  v = new ImageView(getActivity());
		  Glide.with(Gallery.this).load(galleryList.get(position)).into((ImageView) v);
		} else {
		  v = convertView;
		}
		return v;
	  }
	};
	lvGallery.setAdapter(galleryAdapter);

	// Get the list of images
	FirebaseDatabase.getInstance().getReference("srijan/gallery").addChildEventListener(
			new ChildEventListener() {
			  @Override
			  public void onChildAdded(
					  @NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				galleryList.add(dataSnapshot.getValue(String.class));
				galleryAdapter.notifyDataSetChanged();
			  }

			  @Override
			  public void onChildChanged(
					  @NonNull DataSnapshot dataSnapshot,
					  @Nullable String s) {
			  }

			  @Override
			  public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
			  }

			  @Override
			  public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
			  }

			  @Override
			  public void onCancelled(@NonNull DatabaseError databaseError) {
			  }
			}
	);
  }
}