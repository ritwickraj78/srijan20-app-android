package in.srijanju.androidapp.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import in.srijanju.androidapp.R;


public class Gallery extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		ImageView im1 = findViewById(R.id.i1);
		ImageView im2 = findViewById(R.id.i2);
		ImageView im3 = findViewById(R.id.i3);
		ImageView im4 = findViewById(R.id.i4);
		ImageView im5 = findViewById(R.id.i5);
		ImageView im6 = findViewById(R.id.i6);
		ImageView im7 = findViewById(R.id.i7);
		ImageView im8 = findViewById(R.id.i8);
		ImageView im9 = findViewById(R.id.i9);
		ImageView im10 = findViewById(R.id.i10);
		ImageView im11 = findViewById(R.id.i11);
		ImageView im12 = findViewById(R.id.i12);
		ImageView im13 = findViewById(R.id.i13);
		ImageView im14 = findViewById(R.id.i14);
		ImageView im15 = findViewById(R.id.i15);
		ImageView im16 = findViewById(R.id.i16);
		ImageView im17 = findViewById(R.id.i17);
		ImageView im18 = findViewById(R.id.i18);
		ImageView im19 = findViewById(R.id.i19);
		ImageView im20 = findViewById(R.id.i20);

		Glide.with(this).load(
			"https://raw.githubusercontent" +
        ".com/dagahimanshu/srijan-pictures/master/IMG_20190302_203922_HDR.jpg")
			.into(im1);
		Glide.with(this).load(
			"https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190303_183655" +
        ".jpg")
			.into(im2);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0280.jpg")
			.into(im3);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/DSC_0416.jpg")
			.into(im4);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0389.jpg")
			.into(im5);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0399.jpg")
			.into(im6);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_9934.jpg")
			.into(im7);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0298.jpg")
			.into(im8);
		Glide.with(this).load(
			"https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190301_184722" +
        ".jpg")
			.into(im9);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0069.jpg")
			.into(im10);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_9983.jpg")
			.into(im11);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0021.jpg")
			.into(im12);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0523.jpg")
			.into(im13);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0024.jpg")
			.into(im14);
		Glide.with(this).load(
			"https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190302_193217" +
        ".jpg")
			.into(im15);
		Glide.with(this).load(
			"https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190303_194436" +
        ".jpg")
			.into(im16);
		Glide.with(this).load(
			"https://raw.githubusercontent" +
        ".com/dagahimanshu/srijan-pictures/master/LRM_EXPORT_293551229290629_20190304_004728008" +
        ".jpg")
			.into(im17);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0027.jpg")
			.into(im18);
		Glide.with(this)
			.load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0023.jpg")
			.into(im19);
		Glide.with(this).load(
			"https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190303_210019" +
        ".jpg")
			.into(im20);
	}


}
