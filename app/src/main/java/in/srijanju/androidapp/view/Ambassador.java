package in.srijanju.androidapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.SrijanActivity;

public class Ambassador extends SrijanActivity {
  String textVal = "For the past 11 years, you’ve stormed into theatres celebrating the " +
		  "phenomenon" +
		  " that was Avengers: looked upto them dreamy-eyed as they wielded their superpower, wished " +
		  "almost half-heartedly,\n" +
		  "”What if I was the one?”\n" +
		  "As Kolkata’s biggest techno-management carnival slowly picks up steam, Team Srijan presents" +
		  " " +
		  "before you, an idea called the Campus Avengers Initiative. The idea is to bring together a " +
		  "group of remarkable people: honest, responsible and steadfast; and see if they can become " +
		  "something more.\n" +
		  "This is your chance to be THE ONE. Why be the pedestrian when you can own the spotlight?\n" +
		  "We are accepting applications for Campus Avengers.";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_ca);

	FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
	if (user == null) {
	  Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
	  FirebaseAuth.getInstance().signOut();
	  AuthUI.getInstance().signOut(getApplicationContext());
	  Intent intent = new Intent(Ambassador.this, MainActivity.class);
	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	  startActivity(intent);
	  finish();
	  return;
	}

	TextView caText = findViewById(R.id.CA_text);
	caText.setText(textVal);
	Button apply = findViewById(R.id.CA_button);
	apply.setOnClickListener(
			new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				Intent myIntent = new Intent(Ambassador.this, webview.class);
				myIntent.putExtra("url",
						"https://docs.google" +
								".com/forms/d/e/1FAIpQLSewELaYYdVa33eY2F1z_487plvltoFAu3VF2KiiWHg41sT5Zg/viewform");
				startActivity(myIntent);
			  }
			}
	);
  }
}