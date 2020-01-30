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

        ImageView im1 = (ImageView) findViewById(R.id.i1);
        ImageView im2 = (ImageView) findViewById(R.id.i2);
        ImageView im3 = (ImageView) findViewById(R.id.i3);
        ImageView im4 = (ImageView) findViewById(R.id.i4);
        ImageView im5 = (ImageView) findViewById(R.id.i5);
        ImageView im6 = (ImageView) findViewById(R.id.i6);
        ImageView im7 = (ImageView) findViewById(R.id.i7);
        ImageView im8 = (ImageView) findViewById(R.id.i8);
        ImageView im9 = (ImageView) findViewById(R.id.i9);
        ImageView im10 = (ImageView) findViewById(R.id.i10);
        ImageView im11 = (ImageView) findViewById(R.id.i11);
        ImageView im12 = (ImageView) findViewById(R.id.i12);
        ImageView im13 = (ImageView) findViewById(R.id.i13);
        ImageView im14 = (ImageView) findViewById(R.id.i14);
        ImageView im15 = (ImageView) findViewById(R.id.i15);
        ImageView im16 = (ImageView) findViewById(R.id.i16);
        ImageView im17 = (ImageView) findViewById(R.id.i17);
        ImageView im18 = (ImageView) findViewById(R.id.i18);
        ImageView im19 = (ImageView) findViewById(R.id.i19);
        ImageView im20 = (ImageView) findViewById(R.id.i20);

        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190302_203922_HDR.jpg").into(im1);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190303_183655.jpg").into(im2);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0280.jpg").into(im3);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/DSC_0416.jpg").into(im4);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0389.jpg").into(im5);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0399.jpg").into(im6);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_9934.jpg").into(im7);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0298.jpg").into(im8);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190301_184722.jpg").into(im9);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0069.jpg").into(im10);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_9983.jpg").into(im11);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0021.jpg").into(im12);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0523.jpg").into(im13);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0024.jpg").into(im14);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190302_193217.jpg").into(im15);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190303_194436.jpg").into(im16);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/LRM_EXPORT_293551229290629_20190304_004728008.jpg").into(im17);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0027.jpg").into(im18);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_0023.jpg").into(im19);
        Glide.with(this).load("https://raw.githubusercontent.com/dagahimanshu/srijan-pictures/master/IMG_20190303_210019.jpg").into(im20);
    }


}
