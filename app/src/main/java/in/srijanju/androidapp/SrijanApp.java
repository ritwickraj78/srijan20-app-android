package in.srijanju.androidapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SrijanApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		FirebaseDatabase db = FirebaseDatabase.getInstance();
		db.setPersistenceEnabled(true);
		db.getReference("srijan/events").keepSynced(true);
		db.getReference("srijan/gallery").keepSynced(true);
		if (FirebaseAuth.getInstance().getCurrentUser() != null)
			db.getReference("srijan/profile/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
				.keepSynced(true);
	}
}
