package in.srijanju.androidapp.view;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
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
import in.srijanju.androidapp.controller.EventRegisteredAdapter;
import in.srijanju.androidapp.model.User;
public class Profile extends Fragment {
	final ArrayList<Pair<String, String>> events = new ArrayList<>();
	EventRegisteredAdapter adapter;
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
	public void onAttach(Context context) {
		super.onAttach(context);
	}
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_profile, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 adapter = new EventRegisteredAdapter(getActivity(), events);
		user = FirebaseAuth.getInstance().getCurrentUser();
		if (user == null) {
			Toast.makeText(getActivity(), "Not logged in", Toast.LENGTH_SHORT).show();
			FirebaseAuth.getInstance().signOut();
			AuthUI.getInstance().signOut(getContext());
			Intent intent = new Intent(getActivity(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			return;
		}
		final TextView tvName = getView().findViewById(R.id.tv_profile_name);
		final TextView tvEmail = getView().findViewById(R.id.tv_profile_email);
		final TextView tvClg = getView().findViewById(R.id.tv_clg);
		final TextView tvDegree = getView().findViewById(R.id.tv_degree);
		final TextView tvCourse = getView().findViewById(R.id.tv_course);
		final TextView tvYear = getView().findViewById(R.id.tv_year);
		FirebaseDatabase.getInstance().getReference("srijan/profile/" + user.getUid() +
				"/parentprofile").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				User user = dataSnapshot.getValue(User.class);
				if (user == null) {
					Toast.makeText(getActivity(), "Something weird happened! :(", Toast.LENGTH_SHORT).show();
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
		GridView gridView = getView().findViewById(R.id.gv_events);
		gridView.setAdapter(adapter);
		getView().findViewById(R.id.btn_profile_logout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FirebaseAuth.getInstance().signOut();
				AuthUI.getInstance().signOut(getActivity().getApplicationContext());
				Intent intent = new Intent(getActivity(), MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onResume() {
		super.onResume();
		ref.addChildEventListener(regEventListener);
	}
	@Override
	public void onPause() {
		super.onPause();
		ref.removeEventListener(regEventListener);
		events.clear();
	}
}
