package in.srijanju.androidapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.srijanju.androidapp.R;

public class About extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

  private static final String srijanAbt = "SRIJAN has always dedicated itself to the idea of " +
		  "promoting, showcasing, and encouraging concepts and research beyond the known periphery. " +
		  "SRIJAN is not just a fest for us. It is a Religion.";

  private static final String API_KEY = "AIzaSyBYBVz-xSdiiIE2bueRgccFKcQ7odLosVg";
  private static final String VIDEO_ID = "7oAc0d_W8-k";

  YouTubePlayerView playerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_about);

	FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
	if (user == null) {
	  Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
	  FirebaseAuth.getInstance().signOut();
	  AuthUI.getInstance().signOut(getApplicationContext());
	  Intent intent = new Intent(About.this, MainActivity.class);
	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	  startActivity(intent);
	  finish();
	  return;
	}

	playerView = findViewById(R.id.srijanVid);
	playerView.initialize(API_KEY, About.this);

	TextView srijanText = findViewById(R.id.srijanText);
	srijanText.setText(srijanAbt);

	//OnClickListeners
	ImageView img1 = findViewById(R.id.JU_official);
	img1.setOnClickListener(new View.OnClickListener() {
	  public void onClick(View v) {

		Intent myIntent = new Intent(About.this, webview.class);
		myIntent.putExtra("url", "http://www.jaduniv.edu.in");
		startActivity(myIntent);
	  }
	});
	ImageView img2 = findViewById(R.id.GS_official);
	img2.setOnClickListener(new View.OnClickListener() {
	  public void onClick(View v) {
		Intent myIntent = new Intent(About.this, webview.class);
		myIntent.putExtra("url", "https://www.facebook.com/GamesSocietyJU/");
		startActivity(myIntent);
	  }
	});
	ImageView img3 = findViewById(R.id.CC_official);
	img3.setOnClickListener(new View.OnClickListener() {
	  public void onClick(View v) {
		Intent myIntent = new Intent(About.this, webview.class);
		myIntent.putExtra("url", "https://www.facebook.com/JUCodeClub/");
		startActivity(myIntent);
	  }
	});
	ImageView img4 = findViewById(R.id.SC_official);
	img4.setOnClickListener(new View.OnClickListener() {
	  public void onClick(View v) {
		Intent myIntent = new Intent(About.this, webview.class);
		myIntent.putExtra("url", "https://www.facebook.com/juscofficial/");
		startActivity(myIntent);
	  }
	});
  }

  @Override
  public void onInitializationSuccess(
		  YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
	youTubePlayer.cueVideo(VIDEO_ID);
	youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
  }

  @Override
  public void onInitializationFailure(
		  YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
  }

  @Override
  protected void onDestroy() {
	super.onDestroy();
  }
}
