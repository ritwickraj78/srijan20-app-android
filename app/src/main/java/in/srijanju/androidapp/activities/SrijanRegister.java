package in.srijanju.androidapp.activities;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Date;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.model.User;

public class SrijanRegister extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_srijan_register);

		final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user == null) {
			Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
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

		final MaterialSpinner spinner = findViewById(R.id.spinner_year);
		spinner.setItems("1st year", "2nd year", "3rd year", "4th year", "5th year");

		Button btnRegister = findViewById(R.id.btn_register);
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
				long updatetime = new Date().getTime();
				User newUser = new User(name, email, college, degree, course, year, 0, updatetime);
				Log.d("user", newUser.toString());

				FirebaseDatabase.getInstance().getReference("srijan/profile/" + uid + "/parentprofile")
					.setValue(newUser).addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
	}
}
