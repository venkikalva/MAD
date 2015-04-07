package com.example.group1a_hw05;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class WebActivity extends Activity {

	private WebView mWebview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWebview = new WebView(this);
		mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
		final Activity activity = this;
		if(getIntent().getExtras().getString("url")!=null||"".equalsIgnoreCase(getIntent().getExtras().getString("url"))){
			String url = getIntent().getExtras().getString("url");
			mWebview.setWebViewClient(new WebViewClient() {
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
					Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
				}
			});
			mWebview.loadUrl(url);
			setContentView(mWebview);
		}else{
			Toast.makeText(WebActivity.this, "No Url Found", Toast.LENGTH_LONG).show();
		}
		}
	}
