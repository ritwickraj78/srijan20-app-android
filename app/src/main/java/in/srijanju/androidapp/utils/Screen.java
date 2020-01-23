package in.srijanju.androidapp.utils;

import android.app.Activity;
import android.view.View;

public class Screen {

	/**
	 * Get the activity reference and make it fullscreen
	 */
	public static void makeFullscreen(Activity activity) {
		activity.getWindow().getDecorView().setSystemUiVisibility(
			View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				// Set the content to appear under the system bars so that the
				// content doesn't resize when the system bars hide and show.
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				// Hide the nav bar and status bar
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN);
	}
}
