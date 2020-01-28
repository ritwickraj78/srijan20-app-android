package in.srijanju.androidapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import in.srijanju.androidapp.R;

public class MainPage extends AppCompatActivity {
	public static final int REQUEST_IMAGE_CAPTURE = 101;
	private TextView a;

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		a = findViewById(R.id.timer);
		Typeface myCustomFont = getResources().getFont(R.font.payback);
		a.setTypeface(myCustomFont);
		initialise();

		/*
		 * Srijan timer logic
		 * */
		final Calendar thatDay = Calendar.getInstance();
		thatDay.setTime(new Date(0)); /* reset */
		thatDay.set(Calendar.DAY_OF_MONTH, 5);
		thatDay.set(Calendar.MONTH, 2); // 0-11 so 1 less
		thatDay.set(Calendar.YEAR, 2020);
		thatDay.set(Calendar.HOUR_OF_DAY, 0);

		final Calendar lastDay = Calendar.getInstance();
		lastDay.setTime(new Date(0)); /* reset */
		lastDay.set(Calendar.DAY_OF_MONTH, 9);
		lastDay.set(Calendar.MONTH, 2); // 0-11 so 1 less
		lastDay.set(Calendar.YEAR, 2020);
		thatDay.set(Calendar.HOUR_OF_DAY, 0);

		final int SECONDS_IN_A_DAY = 24 * 60 * 60;
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				Calendar today = Calendar.getInstance();
				long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
				if (diff >= 0) {
					long diffSec = diff / 1000;

					long days = diffSec / SECONDS_IN_A_DAY;
					long secondsDay = diffSec % SECONDS_IN_A_DAY;
					long seconds = secondsDay % 60;
					long minutes = (secondsDay / 60) % 60;
					long hours = (secondsDay / 3600); // % 24 not needed
					String s = days + " DAYS \n\n" + hours + " HOURS \n\n to go! ";
					setText(a, s);
				} else {
					long difference = lastDay.getTimeInMillis() - today.getTimeInMillis();
					long diffSec = difference / 1000;
					if (difference >= 0) {
						long days = diffSec / SECONDS_IN_A_DAY;
						long secondsDay = diffSec % SECONDS_IN_A_DAY;
						long seconds = secondsDay % 60;
						long minutes = (secondsDay / 60) % 60;
						long hours = (secondsDay / 3600); // % 24 not needed
						String s = " Srijan \n\n is live \n\n now";
						setText(a, s);
					} else {
						setText(a, " Srijan\n\n over");
					}
				}

			}
		}, 0, 1000);

	}

	private void initialise() {
		a = findViewById(R.id.timer);

		ImageView ivAbout = findViewById(R.id.iv_about);
		ivAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, About.class));
			}
		});

		ImageView ivEvents = findViewById(R.id.iv_events);
		ivEvents.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, Events.class));
			}
		});

		ImageView ambassador = findViewById(R.id.iv_ca);
		ambassador.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, Ambassador.class));
			}
		});

		ImageView gallery = findViewById(R.id.iv_gallery);
		gallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, Gallery.class));
			}
		});

		ImageView ivSponsor = findViewById(R.id.iv_sponsor);
		ivSponsor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: Complete Sponsor page
				Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_SHORT).show();
			}
		});

		ImageView ivScanQr = findViewById(R.id.iv_scan_qr);
		ivScanQr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
				}
			}
		});
	}

	private void setText(final TextView text, final String value) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				text.setText(value);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
			FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
				.getVisionBarcodeDetector();
			detector.detectInImage(image)
				.addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
					@Override
					public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
						boolean flag = false;
						for (FirebaseVisionBarcode barcode : barcodes) {
							int valueType = barcode.getValueType();
							// See API reference for complete list of supported types
							if (valueType == FirebaseVisionBarcode.TYPE_URL) {
								flag = true;
								String title = barcode.getUrl().getTitle();
								String url = barcode.getUrl().getUrl();
								Toast.makeText(getApplicationContext(), "Redirecting to " + url,
									Toast.LENGTH_SHORT).show();
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
								startActivity(browserIntent);
								break;
							}
						}
						if (!flag)
							Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
					}
				});
		} else Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
	}
}
