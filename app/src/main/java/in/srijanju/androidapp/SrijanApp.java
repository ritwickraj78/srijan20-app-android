package in.srijanju.androidapp;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class SrijanApp extends Application {

	public static final String ANDROID_APP_VERSION_CODE_STRING = "android_app_version_code";
	public static final String ANDROID_APP_VERSION_NAME_STRING = "android_app_version_name";
	FirebaseRemoteConfig mFirebaseRemoteConfig;

	@Override
	public void onCreate() {
		super.onCreate();

		// Firebase realtime database caching
		FirebaseDatabase db = FirebaseDatabase.getInstance();
		db.setPersistenceEnabled(true);
		db.getReference("srijan/events").keepSynced(true);
		db.getReference("srijan/gallery").keepSynced(true);
		db.getReference("srijan/profile").keepSynced(false);

		// Firebase remote config
		mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
		Map<String, Object> defMap = new HashMap<>();
		defMap.put(ANDROID_APP_VERSION_CODE_STRING, BuildConfig.VERSION_CODE);
		defMap.put(ANDROID_APP_VERSION_NAME_STRING, BuildConfig.VERSION_NAME);
		mFirebaseRemoteConfig.setDefaultsAsync(defMap);
		mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(
			new OnCompleteListener<Boolean>() {
				@Override
				public void onComplete(@NonNull Task<Boolean> task) {
				}
			});
	}
}
