package in.srijanju.androidapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.SrijanActivity;

public class MainPage extends SrijanActivity implements
		NavigationView.OnNavigationItemSelectedListener {
  public static final String SRIJAN_MERCH_URL = "https://www.srijanju.in/app/merchandise";
  public static final String SRIJAN_WORKSHOP_URL = "https://www.srijanju.in/app/workshops";

  private TextView a;

	public Toolbar toolbar;

	public DrawerLayout drawerLayout;

	public NavController navController;

	public NavigationView navigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_page);

	FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
	if (user == null) {
	  Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
	  FirebaseAuth.getInstance().signOut();
	  AuthUI.getInstance().signOut(getApplicationContext());
	  Intent intent = new Intent(MainPage.this, MainActivity.class);
	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	  startActivity(intent);
	  finish();
	  return;
	}
	setupNavigation();
//	initialise();
//
//	/*
//	 * Srijan timer logic
//	 * */
//	final Calendar thatDay = Calendar.getInstance();
//	thatDay.setTime(new Date(0)); /* reset */
//	thatDay.set(Calendar.DAY_OF_MONTH, 5);
//	thatDay.set(Calendar.MONTH, 2); // 0-11 so 1 less
//	thatDay.set(Calendar.YEAR, 2020);
//	thatDay.set(Calendar.HOUR_OF_DAY, 0);
//
//	final Calendar lastDay = Calendar.getInstance();
//	lastDay.setTime(new Date(0)); /* reset */
//	lastDay.set(Calendar.DAY_OF_MONTH, 9);
//	lastDay.set(Calendar.MONTH, 2); // 0-11 so 1 less
//	lastDay.set(Calendar.YEAR, 2020);
//	thatDay.set(Calendar.HOUR_OF_DAY, 0);
//
//	final int SECONDS_IN_A_DAY = 24 * 60 * 60;
//	Timer t = new Timer();
//	t.schedule(new TimerTask() {
//	  @Override
//	  public void run() {
//		Calendar today = Calendar.getInstance();
//		long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
//		if (diff >= 0) {
//		  long diffSec = diff / 1000;
//
//		  long days = diffSec / SECONDS_IN_A_DAY;
//		  long secondsDay = diffSec % SECONDS_IN_A_DAY;
//		  // long seconds = secondsDay % 60;
//		  // long minutes = (secondsDay / 60) % 60;
//		  long hours = (secondsDay / 3600); // % 24 not needed
//
//		  String d = days > 1 ? " DAYS \n" : " DAY \n";
//		  String h = hours > 1 ? " HOURS \n to go! " : " HOUR \n to go! ";
//		  String s = days + d + hours + h;
//		  setText(a, s);
//		} else {
//		  long difference = lastDay.getTimeInMillis() - today.getTimeInMillis();
//		  // long diffSec = difference / 1000;
//		  if (difference >= 0) {
//			// long days = diffSec / SECONDS_IN_A_DAY;
//			// long secondsDay = diffSec % SECONDS_IN_A_DAY;
//			// long seconds = secondsDay % 60;
//			// long minutes = (secondsDay / 60) % 60;
//			// long hours = (secondsDay / 3600); // % 24 not needed
//			String s = " Srijan \n is live \n now";
//			setText(a, s);
//		  } else {
//			setText(a, " Srijan\n over");
//		  }
//		}
//
//	  }
//	}, 500, 1000 * 60 * 5);
  }
	private void setupNavigation() {

		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		drawerLayout = findViewById(R.id.drawer_layout);

		navigationView = findViewById(R.id.navigationView);

		navController = Navigation.findNavController(this, R.id.nav_host_fragment);

		NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);

		NavigationUI.setupWithNavController(navigationView, navController);

		navigationView.setNavigationItemSelectedListener(this);

	}

//  private void initialise() {
//	a = findViewById(R.id.timer);
//
//	ImageView ivProfile = findViewById(R.id.iv_profile);
//	ivProfile.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		startActivity(new Intent(MainPage.this, Profile.class));
//	  }
//	});
//
//	ImageView ivAbout = findViewById(R.id.iv_about);
//	ivAbout.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		startActivity(new Intent(MainPage.this, About.class));
//	  }
//	});
//
//	ImageView ivEvents = findViewById(R.id.iv_events);
//	ivEvents.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		startActivity(new Intent(MainPage.this, Events.class));
//	  }
//	});
//
//	ImageView ambassador = findViewById(R.id.iv_ca);
//	ambassador.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		startActivity(new Intent(MainPage.this, Ambassador.class));
//	  }
//	});
//
//	ImageView gallery = findViewById(R.id.iv_gallery);
//	gallery.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		startActivity(new Intent(MainPage.this, Gallery.class));
//	  }
//	});
//
//	ImageView ivSponsor = findViewById(R.id.iv_sponsor);
//	ivSponsor.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		startActivity(new Intent(MainPage.this, Sponsors.class));
//	  }
//	});
//
//	ImageView ivScanQr = findViewById(R.id.iv_scan_qr);
//	ivScanQr.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		startActivity(new Intent(MainPage.this, CameraScan.class));
//	  }
//	});
//
//	findViewById(R.id.iv_merch).setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View v) {
//		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SRIJAN_MERCH_URL));
//		startActivity(browserIntent);
//	  }
//	});
//  }
//
//  private void setText(final TextView text, final String value) {
//	runOnUiThread(new Runnable() {
//	  @Override
//	  public void run() {
//		text.setText(value);
//	  }
//	});
//  }



	@Override
	public boolean onSupportNavigateUp() {
		return NavigationUI.navigateUp(drawerLayout, Navigation.findNavController(this, R.id.nav_host_fragment));
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

		menuItem.setChecked(true);

		drawerLayout.closeDrawers();

		int id = menuItem.getItemId();

		switch (id) {

			case R.id.first:
				navController.navigate(R.id.firstFragment);
				break;

			case R.id.second:
				navController.navigate(R.id.secondFragment);
				break;

			case R.id.third:
				navController.navigate(R.id.thirdFragment);
				break;

				case R.id.fourth:
				navController.navigate(R.id.fourthFragment);
				break;
			case R.id.fifth:
				navController.navigate(R.id.fifthFragment);
				break;

		}
		return true;

	}

}
