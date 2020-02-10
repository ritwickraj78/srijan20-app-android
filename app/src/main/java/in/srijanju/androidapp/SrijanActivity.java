package in.srijanju.androidapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import static in.srijanju.androidapp.SrijanApp.ANDROID_APP_VERSION_CODE_STRING;
import static in.srijanju.androidapp.SrijanApp.ANDROID_APP_VERSION_NAME_STRING;

public class SrijanActivity extends AppCompatActivity {

	FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

	@Override
	protected void onResume() {
		super.onResume();

		/*
		 * Check for app updates
		 */
		long vCode = mFirebaseRemoteConfig.getLong(ANDROID_APP_VERSION_CODE_STRING);
		String vName = mFirebaseRemoteConfig.getString(ANDROID_APP_VERSION_NAME_STRING);
		String[] arrNames = vName.split("\\.");
		long vName0 = Long.parseLong(arrNames[0]), vName1 = Long.parseLong(arrNames[1]);

		long curCode = BuildConfig.VERSION_CODE;
		String curName = BuildConfig.VERSION_NAME;
		String[] carrNames = curName.split("\\.");

		long curName0 = Long.parseLong(carrNames[0]), curName1 = Long.parseLong(carrNames[1]);
		if (vCode > curCode && (vName0 > curName0 || vName1 > curName1)) {
			new AlertDialog.Builder(SrijanActivity.this).setTitle("App update available")
				.setIcon(R.mipmap.ic_launcher)
				.setMessage("Update to continue using the app.").setPositiveButton(
				"Update", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final String appPackageName = getPackageName();
						try {
							startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse("market://details?id=" + appPackageName)));
						} catch (android.content.ActivityNotFoundException anfe) {
							startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
						}
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(SrijanActivity.this, "You need to update the app first!",
						Toast.LENGTH_LONG)
						.show();
					finish();
				}
			}).setCancelable(false).create().show();
		}
	}
}
