package in.srijanju.androidapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ServerValue;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Map;

import in.srijanju.androidapp.R;

public class Sponsors extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        final MaterialSpinner spinner = findViewById(R.id.spinner_year);
        spinner.setItems("1", "2", "3", "4", "5");
        spinner.setBackgroundResource(R.drawable.background);
        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setBackgroundColor(Color.DKGRAY);


        final TextInputEditText etClg = findViewById(R.id.et_clg);
        final TextInputEditText etDegree = findViewById(R.id.et_degree);
        final TextInputEditText etCourse = findViewById(R.id.et_course);
        final TextInputEditText etEmail = findViewById(R.id.et_email);
        final TextInputEditText etName = findViewById(R.id.et_name);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(etName.getText());
                String email = String.valueOf(etEmail.getText());
                String college = String.valueOf(etClg.getText());
                String degree = String.valueOf(etDegree.getText());
                String course = String.valueOf(etCourse.getText());
                TextView regname=(TextView)findViewById(R.id.name);
                TextView collegename=(TextView)findViewById(R.id.college);
                TextView degreename=(TextView)findViewById(R.id.degree);
                TextView coursename=(TextView)findViewById(R.id.course);

                Map<String, String> updatetime = ServerValue.TIMESTAMP;
                Log.d("time", String.valueOf(updatetime));

                regname.setText("");
                degreename.setText("");
                collegename.setText("");
                coursename.setText("");

                int f=0;
                if(!(name.length()>=6 && name.length()<=30 && name.matches("[a-z A-Z]{6,50}")))
                {
                    //Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                    f=1;
                    regname.setText("Enter valid user name");
                }

                if(!(college.length()>=6 && college.length()<=50 && college.matches("[a-z A-Z]{6,50}")))
                {
                    f=1;
                    collegename.setText("Enter valid college name");
                }

                if(!(degree.length()>=2 && degree.length()<=50 && degree.matches("[a-z A-Z]{2,50}")))
                {
                    f=1;
                    degreename.setText("Enter valid degree name");
                }
                if(!(course.length()>=2 && course.length()<=50 && course.matches("[a-z A-Z]{2,50}")))
                {
                    f=1;
                    coursename.setText("Enter valid course name");
                }
                if(f==0)
                {
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
