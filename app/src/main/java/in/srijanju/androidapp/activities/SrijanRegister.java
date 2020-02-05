package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
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
		// Set user's name
		etName.setText(user.getDisplayName());

		TextInputEditText etEmail = findViewById(R.id.et_email);
		// Set user's email and disallow edit
		etEmail.setText(user.getEmail());
		etEmail.setEnabled(false);
		etEmail.setInputType(InputType.TYPE_NULL);

		final TextInputEditText etClg = findViewById(R.id.et_clg);
		final TextInputEditText etDegree = findViewById(R.id.et_degree);
		final TextInputEditText etCourse = findViewById(R.id.et_course);

		// Set the possible values of year
		final MaterialSpinner spinner = findViewById(R.id.spinner_year);
		spinner.setItems("1", "2", "3", "4", "5");
		spinner.setBackgroundResource(R.drawable.background);

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

				// TODO: Check validity of the data before pushing to the database

				String[] suffix = {"st", "nd", "rd", "th", "th"};
				int pos = spinner.getSelectedIndex();
				String year = (pos + 1) + suffix[pos];
				String uid = user.getUid();
				Map<String, String> updatetime = ServerValue.TIMESTAMP;


				TextView regname=(TextView)findViewById(R.id.name);
				TextView collegename=(TextView)findViewById(R.id.college);
				TextView degreename=(TextView)findViewById(R.id.degree);
				TextView coursename=(TextView)findViewById(R.id.course);

				regname.setText("");
				degreename.setText("");
				collegename.setText("");
				coursename.setText("");

				int f=0;
				if(!(name.length()>=6 && name.length()<=30 && name.matches("[a-z A-Z]{6,50}")))
				{
					//Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
					f=1;
					regname.setText("enter atleast 6 characters. No digits please");
				}

				if(!(college.length()>=6 && college.length()<=50 && college.matches("[a-z A-Z]{6,50}")))
				{
					f=1;
					collegename.setText("enter atleast 6 characters. No digits please");
				}

				if(!(degree.length()>=2 && degree.length()<=50 && degree.matches("[a-z A-Z]{2,50}")))
				{
					f=1;
					degreename.setText("enter atleast 2 characters. No digits please");
				}
				if(!(course.length()>=2 && course.length()<=50 && course.matches("[a-z A-Z]{2,50}")))
				{
					f=1;
					coursename.setText("enter atleast 2 characters. No digits please");
				}
				if(f==1)
				{
					Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_SHORT).show();
				}
				if(f==0) {
					//Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
					UserSend newUserSend = new UserSend(name, email, college, degree, course, year, 1,
							updatetime);
					// Add user to the database
					FirebaseDatabase.getInstance()
							.getReference("srijan/profile/" + uid + "/parentprofile")
							.setValue(newUserSend)
							.addOnSuccessListener(new OnSuccessListener<Void>() {
								@Override
								public void onSuccess(Void aVoid) {
									// If added successfully, open app content
									startActivity(new Intent(SrijanRegister.this, MainPage.class));
									finish();
								}
							})
							.addOnFailureListener(new OnFailureListener() {
								@Override
								public void onFailure(@NonNull Exception e) {
									// TODO: Check validity of the data before pushing to the database
									Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_SHORT).show();
								}
							});

				}
			}
		});
	}
}
