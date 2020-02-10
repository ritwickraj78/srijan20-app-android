package in.srijanju.androidapp.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.SrijanActivity;


public class Gallery extends SrijanActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		// Gallery images list view
		final ListView lvGallery = findViewById(R.id.lv_gallery);
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
					v = new ImageView(Gallery.this);
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