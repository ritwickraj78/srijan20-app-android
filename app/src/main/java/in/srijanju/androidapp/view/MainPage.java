package in.srijanju.androidapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.SrijanActivity;

public class MainPage extends SrijanActivity implements
		NavigationView.OnNavigationItemSelectedListener {
  public static final String SRIJAN_MERCH_URL = "https://www.srijanju.in/app/merchandise";
  public static final String SRIJAN_WORKSHOP_URL = "https://www.srijanju.in/app/workshops";
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
  }

  private void setupNavigation() {

	toolbar = findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);

	drawerLayout = findViewById(R.id.drawer_layout);

	navigationView = findViewById(R.id.navigationView);

	navController = Navigation.findNavController(this, R.id.nav_host_fragment);

	NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);

	NavigationUI.setupWithNavController(navigationView, navController);

	navigationView.setNavigationItemSelectedListener(this);

  }

  @Override
  public boolean onSupportNavigateUp() {
	return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
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

	  case R.id.profile:
		navController.navigate(R.id.profileFrag);
		break;

	  case R.id.about:
		navController.navigate(R.id.aboutFrag);
		break;

	  case R.id.events:
		navController.navigate(R.id.eventsFrag);
		break;

	  case R.id.ca:
		navController.navigate(R.id.caFrag);
		break;

	  case R.id.gallery:
		navController.navigate(R.id.galleryFrag);
		break;
	  case R.id.sponsors:
		navController.navigate(R.id.sponsorsFrag);
		break;

	}
	return true;

  }

}
