package in.srijanju.androidapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import in.srijanju.androidapp.R;

public class webview extends AppCompatActivity {

	WebView webView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_activity);

		final RelativeLayout r = findViewById(R.id.loadingPanel);
		Intent i = getIntent();
		String full = i.getStringExtra("url");
		webView = findViewById(R.id.web);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setHorizontalScrollBarEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(full);
		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				r.setVisibility(View.GONE);
			}
		});
	}
}