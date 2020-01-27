package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.srijanju.androidapp.R;

public class Ambassador extends AppCompatActivity {
    String textVal = "For the past 11 years, you’ve stormed into theatres celebrating the phenomenon that was Avengers: looked upto them dreamy-eyed as they wielded their superpower, wished almost half-heartedly,\n" +
            "”What if I was the one?”\n" +
            "As Kolkata’s biggest techno-management carnival slowly picks up steam, Team Srijan presents before you, an idea called the Campus Avengers Initiative. The idea is to bring together a group of remarkable people: honest, responsible and steadfast; and see if they can become something more.\n" +
            "This is your chance to be THE ONE. Why be the pedestrian when you can own the spotlight?\n" +
            "We are accepting applications for Campus Avengers.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_circle_descriptors);
        TextView caText = (TextView) findViewById(R.id.CA_text);
        caText.setText(textVal);
        Button apply =( Button)findViewById(R.id.CA_button);
        apply.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent();
                                         intent.setAction(Intent.ACTION_VIEW);
                                         intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                         intent.setData(Uri.parse("http://www.jaduniv.edu.in"));
                                         startActivity(intent);
                                     }
                                 }
        );
    }
}