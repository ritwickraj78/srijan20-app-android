package in.srijanju.androidapp;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

	registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
	  @Override
	  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
		if (activity.getClass().toString().contains(getPackageName())) {
		  activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	  }

	  @Override
	  public void onActivityStarted(@NonNull Activity activity) {
	  }

	  @Override
	  public void onActivityResumed(@NonNull Activity activity) {
	  }

	  @Override
	  public void onActivityPaused(@NonNull Activity activity) {
	  }

	  @Override
	  public void onActivityStopped(@NonNull Activity activity) {
	  }

	  @Override
	  public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
	  }

	  @Override
	  public void onActivityDestroyed(@NonNull Activity activity) {
	  }
	});

	// Firebase realtime database caching
	FirebaseDatabase db = FirebaseDatabase.getInstance();
	db.setPersistenceEnabled(true);
	db.getReference("srijan/events").keepSynced(true);

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
