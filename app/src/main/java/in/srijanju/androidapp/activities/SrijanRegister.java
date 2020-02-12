package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Map;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.model.UserSend;

public class SrijanRegister extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_srijan_register);

	final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
	if (user == null) {
	  Toast.makeText(this, "UserSend not found", Toast.LENGTH_SHORT).show();
	  finish();
	  return;
	}

	final TextInputEditText etName = findViewById(R.id.et_name);
	final TextInputLayout lName = findViewById(R.id.in_reg_name);
	// Set user's name
	etName.setText(user.getDisplayName());

	TextInputEditText etEmail = findViewById(R.id.et_email);
	// Set user's email and disallow edit
	etEmail.setText(user.getEmail());
	etEmail.setEnabled(false);
	etEmail.setInputType(InputType.TYPE_NULL);

	final TextInputEditText etClg = findViewById(R.id.et_clg);
	final TextInputLayout lClg = findViewById(R.id.in_reg_clg);

	final TextInputEditText etDegree = findViewById(R.id.et_degree);
	final TextInputLayout lDeg = findViewById(R.id.in_reg_degree);

	final TextInputEditText etCourse = findViewById(R.id.et_course);
	final TextInputLayout lCourse = findViewById(R.id.in_reg_course);

	// Set the possible values of year
	final MaterialSpinner spinner = findViewById(R.id.spinner_year);
	spinner.setItems("1st year", "2nd year", "3rd year", "4th year", "5th year");
	spinner.setBackgroundResource(R.drawable.background);
	spinner.getListView()
			.setDivider(new ColorDrawable(Color.parseColor("#33ffffff")));
	spinner.getListView().setDividerHeight(4);
	spinner.getPopupWindow()
			.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

	// Submit the details
	Button btnRegister = findViewById(R.id.btn_register);
	btnRegister.setBackgroundColor(Color.DKGRAY);
	btnRegister.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View v) {
		String name = String.valueOf(etName.getText());
		String email = user.getEmail();
		String college = String.valueOf(etClg.getText());
		String degree = String.valueOf(etDegree.getText());
		String course = String.valueOf(etCourse.getText());

		String[] suffix = {"st", "nd", "rd", "th", "th"};
		int pos = spinner.getSelectedIndex();
		String year = (pos + 1) + suffix[pos];
		String uid = user.getUid();
		Map<String, String> updatetime = ServerValue.TIMESTAMP;

		/*
		 * Validate all data before sending
		 */
		boolean f = false;
		//Validate name
		if (!(name.length() >= 6 && name.length() <= 30 && name.matches("[a-z A-Z]{6,50}"))) {
		  f = true;
		  lName
				  .setError("At least 6 alphabets but lesser than 30 required.");
		}
		//Validate college
		if (!(college.length() >= 6 && college.length() <= 50 && college
				.matches("[a-z A-Z]{6,50}"))) {
		  f = true;
		  lClg
				  .setError("At least 6 alphabets but lesser than 50 required.");
		}
		//Validate degree
		if (!(degree.length() >= 2 && degree.length() <= 50 && degree.matches("[a-z A-Z]{2,50}"))) {
		  f = true;
		  lDeg
				  .setError("At least 2 alphabets but lesser than 50 required.");
		}
		//Validate course
		if (!(course.length() >= 2 && course.length() <= 50 && course.matches("[a-z A-Z]{2,50}"))) {
		  f = true;
		  lCourse
				  .setError("At least 2 alphabets but lesser than 50 required.");
		}

		if (f) {
		  // Invalid data
		  Toast toast = Toast.makeText(getApplicationContext(), "Invalid data!",
				  Toast.LENGTH_SHORT);
		  toast.setGravity(Gravity.CENTER, 0, 0);
		  toast.show();
		} else {
		  // Data is valid, register the user

		  UserSend newUserSend = new UserSend(name, email, college, degree, course, year, 1,
				  updatetime);
		  // Add user to the database
		  FirebaseDatabase.getInstance()
				  .getReference("srijan/profile/" + uid + "/parentprofile")
				  .setValue(newUserSend)
				  .addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
					  Toast toast = Toast.makeText(getApplicationContext(), "Registered!",
							  Toast.LENGTH_SHORT);
					  toast.setGravity(Gravity.CENTER, 0, 0);
					  toast.show();

					  // If added successfully, open app content
					  startActivity(new Intent(SrijanRegister.this, MainPage.class));
					  finish();
					}
				  })
				  .addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
					  Toast toast = Toast.makeText(getApplicationContext(), "Please enter correct details",
							  Toast.LENGTH_SHORT);
					  toast.setGravity(Gravity.CENTER, 0, 0);
					  toast.show();
					}
				  });

		}
	  }
	});

	// Logout the user and display the sign in page
	Button btnLogout = findViewById(R.id.btn_logout);
	btnLogout.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View v) {
		FirebaseAuth.getInstance().signOut();
		AuthUI.getInstance().signOut(getApplicationContext());
		startActivity(new Intent(SrijanRegister.this, MainActivity.class));
		finish();
	  }
	});
  }
}