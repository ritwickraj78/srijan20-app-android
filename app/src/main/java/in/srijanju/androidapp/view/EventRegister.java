package in.srijanju.androidapp.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import in.srijanju.androidapp.R;
import in.srijanju.androidapp.SrijanActivity;
import in.srijanju.androidapp.model.SrijanEvent;


public class EventRegister extends SrijanActivity {

  final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

  EditText etLeadContact;
  EditText etMem2;
  EditText etMem3;
  Button btnRegister;

  DatabaseReference refReg;
  SrijanEvent event;

  String uid2 = null, uid3 = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_event_register);

	// Get event details. If error, exit
	Bundle extras = getIntent().getExtras();
	if (extras == null) {
	  Toast.makeText(this, "Something went wrong! :(", Toast.LENGTH_SHORT).show();
	  finish();
	  return;
	}
	event = (SrijanEvent) extras.getSerializable("event");
	if (event == null || user == null) {
	  Toast.makeText(this, "Something went wrong! :(", Toast.LENGTH_SHORT).show();
	  finish();
	  return;
	}

	if (event.maxts == 0) {
	  Toast.makeText(EventRegister.this, "Registration not yet started",
			  Toast.LENGTH_SHORT).show();
	  finish();
	  return;
	}

	EditText etEventName = findViewById(R.id.et_event_name);
	etEventName.setText(event.name);
	etEventName.setEnabled(false);

	final TextInputLayout lTeamName = findViewById(R.id.in_team_name);
	final EditText etTeamName = findViewById(R.id.et_team_name);
	// If team name changes, remove the error
	etTeamName.addTextChangedListener(new TextWatcher() {
	  @Override
	  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	  }

	  @Override
	  public void onTextChanged(CharSequence s, int start, int before, int count) {
		lTeamName.setError("");
	  }

	  @Override
	  public void afterTextChanged(Editable s) {
	  }
	});

	TextView tvTeamSize = findViewById(R.id.tv_size);
	tvTeamSize.setText(String.format(Locale.ENGLISH, "Team size allowed: %d - %d", event.mints, event.maxts));

	final EditText etLeadEmail = findViewById(R.id.et_team_lead);
	etLeadEmail.setText(user.getEmail());
	etLeadEmail.setEnabled(false);

	final EditText etMem1 = findViewById(R.id.et_team_mem1);
	etMem1.setText(user.getEmail());
	etMem1.setEnabled(false);

	etLeadContact = findViewById(R.id.et_team_lead_contact);
	etMem2 = findViewById(R.id.et_team_mem2);
	etMem3 = findViewById(R.id.et_team_mem3);

	// When "register" is clicked, validate the data and push to the database
	btnRegister = findViewById(R.id.btn_register);
	btnRegister.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View v) {
		btnRegister.setEnabled(false);

		/*
		 * Verify team name
		 */
		final String teamName = etTeamName.getText().toString();
		if (!teamName.matches("[a-zA-Z0-9]{3,16}")) {
		  lTeamName.setError("Team name should be alphanumeric. Length should be at least 3 and no more than 16.");
		  btnRegister.setEnabled(true);
		  return;
		}

		refReg = FirebaseDatabase.getInstance().getReference("srijan/events/" + event.code +
				"/teams");

		// Check if team name is taken
		refReg.child(teamName).addListenerForSingleValueEvent(new ValueEventListener() {
		  @Override
		  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
			if (dataSnapshot.exists()) {
			  lTeamName.setError("Team name taken.");
			  btnRegister.setEnabled(true);
			  return;
			}

			// Verify user email
			String email2 = etMem2.getText().toString().trim();
			String email3 = etMem3.getText().toString().trim();
			if (!email2.equals("") && !email2.matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\" +
					".[A-Za-z]{2,64}")) {
			  Toast.makeText(EventRegister.this, "Enter valid email2", Toast.LENGTH_SHORT).show();
			  btnRegister.setEnabled(true);
			  return;
			}
			if (!email3.equals("") && !email3.matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\" +
					".[A-Za-z]{2,64}")) {
			  Toast.makeText(EventRegister.this, "Enter valid email3", Toast.LENGTH_SHORT).show();
			  btnRegister.setEnabled(true);
			  return;
			}

			// Check if users exists
			if (!email2.equals("")) {
			  checkEmail2Exists(email2, email3, teamName);
			} else if (!email3.equals("")) {
			  checkEmail3Exists(email3, teamName);
			} else {
			  createTeam(teamName);
			}
		  }

		  @Override
		  public void onCancelled(@NonNull DatabaseError databaseError) {
			btnRegister.setEnabled(true);
		  }
		});
	  }
	});
  }

  // Check if second user is valid
  private void checkEmail2Exists(final String email, final String nextMail, final String teamname) {
	FirebaseDatabase.getInstance().getReference("srijan/profile").orderByChild(
			"parentprofile/email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
	  @Override
	  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
		if (!dataSnapshot.exists()) {
		  Toast.makeText(EventRegister.this, "User 2 doesn't exist", Toast.LENGTH_SHORT).show();
		  btnRegister.setEnabled(true);
		  return;
		}

		// If second user exists, get its uid and move to u3
		uid2 = dataSnapshot.getChildren().iterator().next().getKey();
		if (!nextMail.equals("")) {
		  // U3 exists, check that
		  checkEmail3Exists(nextMail, teamname);
		} else {
		  // If u3 doesn't exist, create team
		  createTeam(teamname);
		}
	  }

	  @Override
	  public void onCancelled(@NonNull DatabaseError databaseError) {
		Toast.makeText(EventRegister.this, "Some error occurred! Try again.",
				Toast.LENGTH_SHORT).show();
		btnRegister.setEnabled(true);
	  }
	});
  }

  // Check if third user is valid
  private void checkEmail3Exists(String email, final String teamname) {
	FirebaseDatabase.getInstance().getReference("srijan/profile").orderByChild(
			"parentprofile/email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
	  @Override
	  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
		if (!dataSnapshot.exists()) {
		  Toast.makeText(EventRegister.this, "User 3 doesn't exist", Toast.LENGTH_SHORT).show();
		  btnRegister.setEnabled(true);
		  return;
		}

		// U3 exists, create team
		uid3 = dataSnapshot.getChildren().iterator().next().getKey();
		createTeam(teamname);
	  }

	  @Override
	  public void onCancelled(@NonNull DatabaseError databaseError) {
		Toast.makeText(EventRegister.this, "Some error occurred! Try again.",
				Toast.LENGTH_SHORT).show();
		btnRegister.setEnabled(true);
	  }
	});
  }

  // If everything is ok, create the team for the user
  private void createTeam(final String teamName) {
	Map<String, Object> reg = new HashMap<>();
	reg.put("name", teamName);
	reg.put("lead", Objects.requireNonNull(user).getEmail());
	reg.put("lead-contact", etLeadContact.getText().toString());
	Map<String, String> mems = new HashMap<>();
	mems.put("0", user.getEmail());
	if (!etMem2.getText().toString().trim().equals(""))
	  mems.put("1", etMem2.getText().toString().trim());
	if (!etMem3.getText().toString().trim().equals(""))
	  mems.put("2", etMem3.getText().toString().trim());
	reg.put("mems", mems);

	// Create team and push to the events
	refReg.child(teamName).setValue(reg).addOnSuccessListener(new OnSuccessListener<Void>() {
	  @Override
	  public void onSuccess(Void aVoid) {
		String refbase = "srijan/profile/%s/events/%s";
		String leadRef = String.format(refbase, user.getUid(), event.code);
		final String mem2Ref = String.format(refbase, uid2, event.code);
		final String mem3Ref = String.format(refbase, uid3, event.code);

		final Map<String, String> userEventReg = new HashMap<>();
		userEventReg.put("team", teamName);
		userEventReg.put("event", event.name);
		// Add event to team lead
		FirebaseDatabase.getInstance().getReference(leadRef).setValue(userEventReg)
				.addOnSuccessListener(new OnSuccessListener<Void>() {
				  @Override
				  public void onSuccess(Void aVoid) {
					if (uid2 != null) {
					  // Add event to u2
					  FirebaseDatabase.getInstance()
							  .getReference(mem2Ref)
							  .setValue(userEventReg)
							  .addOnSuccessListener(new OnSuccessListener<Void>() {
								@Override
								public void onSuccess(Void aVoid) {
								  // Add event to u3
								  u3UpdateEvent(mem3Ref, userEventReg);
								}
							  })
							  .addOnFailureListener(new OnFailureListener() {
								@Override
								public void onFailure(@NonNull Exception e) {
								  regError();
								}
							  });
					} else {
					  // Add event to u3
					  u3UpdateEvent(mem3Ref, userEventReg);
					}
				  }
				})
				.addOnFailureListener(new OnFailureListener() {
				  @Override
				  public void onFailure(@NonNull Exception e) {
					regError();
				  }
				});
	  }
	});
  }

  // Add event to u3
  private void u3UpdateEvent(String ref, Map<String, String> userEventReg) {
	if (uid3 != null) {
	  FirebaseDatabase.getInstance().getReference(ref).setValue(userEventReg).addOnSuccessListener(new OnSuccessListener<Void>() {
		@Override
		public void onSuccess(Void aVoid) {
		  successfulRegDone();
		}
	  }).addOnFailureListener(new OnFailureListener() {
		@Override
		public void onFailure(@NonNull Exception e) {
		  regError();
		}
	  });
	} else {
	  successfulRegDone();
	}
  }

  // Show success message
  private void successfulRegDone() {
	Toast.makeText(EventRegister.this, "Registered! :)", Toast.LENGTH_SHORT).show();
	finish();
  }

  private void regError() {
	Toast.makeText(EventRegister.this, "Something went wrong! Report to srijanjdvu.ac@gmail" +
			".com for any queries", Toast.LENGTH_LONG).show();
	btnRegister.setEnabled(true);
  }
}
