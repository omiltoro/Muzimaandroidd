package com.muzima.view.sample.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import android.webkit.WebView;
import android.widget.Toast;

import com.muzima.view.sample.R;

public class PatientFormActivity extends Activity {
	
	private static final String TAG = null;
	 
	 private WebView wv;  
	 

	 
	public void TestMethod(String formData) {  
	 System.out.println("TestMethod FormData " + formData);  
	 try {  
	   JSONObject jsonData = new JSONObject(formData);  
	 } catch (JSONException e) {  
	   e.printStackTrace();  
	 }  
	 wv.loadUrl("javascript:onSubmit('" + formData + "');");  
	 Toast.makeText(getApplicationContext(), formData, 50000).show();  
	}  

	/*@Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	  setContentView(R.layout.activity_main);
	  
	  //load webview from activity_main.xml to activity
	  WebView webView = (WebView) findViewById(R.id.webView1);
	  webView.getSettings().setJavaScriptEnabled(true);
	  webView.getSettings().setBuiltInZoomControls(false);
	  WebView.addJavascriptInterface(this, "android");
	  
	  //save the html files in assets folder in project. load html file.
	  webView.loadUrl("file:///android_asset/registrationform.html");
	  if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
	      Log.d(TAG, "No SDCARD");
	} else {
	webView.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/simpleform/SimpleForm.html");
	}


	 }*/
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	  super.onCreate(savedInstanceState);  
	  setContentView(R.layout.activity_main);
	  wv = (WebView) this.findViewById(R.id.webView1);  
	  // requires javascript  
	  wv.getSettings().setJavaScriptEnabled(true);  
	  // make this activity accessible to javascript  
	  wv.addJavascriptInterface(this, "android");
	  // set the html view to load sample.html
	  wv.loadUrl("file:///android_asset/patientform.html");  
	  
	}  

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu; this adds items to the action bar if it is present.
	 // getMenuInflater().inflate(R.menu.activity_main, menu);
		 MenuInflater menuInflater = getMenuInflater();
	     menuInflater.inflate(R.layout.menu, menu);

	  return true;
	 }

	}



