package in.srijanju.androidapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Collections;

import in.srijanju.androidapp.R;

public class CameraScan extends AppCompatActivity {

  private static final int RC_PERMISSION = 1002;
  private CodeScanner mCodeScanner;
  private boolean mPermissionGranted;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_camera_scan);

	CodeScannerView scannerView = findViewById(R.id.scanner_view);
	mCodeScanner = new CodeScanner(this, scannerView);
	mCodeScanner.setFormats(Collections.singletonList(BarcodeFormat.QR_CODE));
	mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
	mCodeScanner.setDecodeCallback(new DecodeCallback() {
	  @Override
	  public void onDecoded(@NonNull final Result result) {
		runOnUiThread(new Runnable() {
		  @Override
		  public void run() {
			Toast.makeText(CameraScan.this, result.getText(), Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(CameraScan.this, webview.class);
			myIntent.putExtra("url", result.getText());
			startActivity(myIntent);
		  }
		});
	  }
	});
	mCodeScanner.setErrorCallback(new ErrorCallback() {
	  @Override
	  public void onError(@NonNull Exception error) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		  if (checkSelfPermission(
				  Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			mPermissionGranted = false;
			requestPermissions(new String[]{Manifest.permission.CAMERA}, RC_PERMISSION);
		  } else {
			mPermissionGranted = true;
		  }
		} else {
		  mPermissionGranted = true;
		}
	  }
	});
	scannerView.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View view) {
		mCodeScanner.startPreview();
	  }
	});
  }

  @Override
  protected void onResume() {
	super.onResume();
	if (mPermissionGranted) {
	  mCodeScanner.startPreview();
	} else {
	  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		if (checkSelfPermission(
				Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
		  mPermissionGranted = false;
		  requestPermissions(new String[]{Manifest.permission.CAMERA}, RC_PERMISSION);
		} else {
		  mPermissionGranted = true;
		  mCodeScanner.startPreview();
		}
	  } else {
		mPermissionGranted = true;
		mCodeScanner.startPreview();
	  }
	}
  }

  @Override
  protected void onPause() {
	mCodeScanner.releaseResources();
	super.onPause();
  }

  @Override
  public void onRequestPermissionsResult(
		  int requestCode, @NonNull String[] permissions,
		  @NonNull int[] grantResults) {
	if (requestCode == RC_PERMISSION) {
	  if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
		mPermissionGranted = true;
		mCodeScanner.startPreview();
	  } else {
		mPermissionGranted = false;
		Toast.makeText(getApplicationContext(), "You need to allow camera perission",
				Toast.LENGTH_SHORT).show();
		finish();
	  }
	}
  }
}
