package com.muzima.view.sample.activities;

import org.apache.lucene.queryParser.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Patient;
import com.muzima.api.service.PatientService;
import com.muzima.view.sample.R;
import com.muzima.view.sample.utilities.StringConstants;

public class PatientFormActivity extends Activity {
	
	private static final String TAG = null;
	 
	 private WebView wv;  
	 
	 private static Patient patient;

	 
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
	@JavascriptInterface
	public void onCreate(Bundle savedInstanceState) {  
	  super.onCreate(savedInstanceState);  
	  setContentView(R.layout.activity_main);
	  
	 final  String patientUuid = getIntent().getStringExtra(StringConstants.KEY_PATIENT_ID);
      patient = getPatient(patientUuid);
	  
	  wv = (WebView) this.findViewById(R.id.webView1);  
	  // requires javascript  
	  wv.getSettings().setJavaScriptEnabled(true);  
	  // make this activity accessible to javascript  
	  wv.addJavascriptInterface(this, "android");
	  wv.setWebChromeClient(new WebChromeClient());
	  // set the html view to load sample.html
	  wv.loadUrl("file:///android_asset/patientform.html"); 
	  wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				wv.loadUrl("javascript:setPersonUUID(\"" + patientUuid + "\")");
			}
		});
	  wv.loadUrl("javascript:setPersonUUID('"+patientUuid+"')");
	  System.out.println("The patient uuid is-----------" + patientUuid);  
	  
	 
	}  
	
	/*@JavascriptInterface
	  public void getUuid() {
		String patientUuid = getIntent().getStringExtra(StringConstants.KEY_PATIENT_ID);
	      patient = getPatient(patientUuid);
	    wv.loadUrl("javascript:setUuid('" + patientUuid + "');");
	    System.out.println("The patient uuid is-----------" + patientUuid); 
	  }*/
	

	
	
	private Context getContext() throws Exception {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String server = settings.getString(
                PreferencesActivity.KEY_SERVER, getString(R.string.default_server));
        String username = settings.getString(
                PreferencesActivity.KEY_USERNAME, getString(R.string.default_username));
        String password = settings.getString(
                PreferencesActivity.KEY_PASSWORD, getString(R.string.default_password));
        Context context = ContextFactory.createContext();
        try {
            if (!context.isAuthenticated())
                context.authenticate(username, password, server);
        } catch (ParseException e) {
            Log.e(TAG, "Unable to authenticate the current context.", e);
        }

        return context;
    }
	
	 private Patient getPatient(final String uuid) {
	        Patient patient = null;
	        try {
	            PatientService patientService = getContext().getPatientService();
	            patient = patientService.getPatientByUuid(uuid);
	        } catch (Exception e) {
	            Log.e(TAG, "Exception when trying to load patient", e);
	        }
	        return patient;
	    }
	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	       
	    }

	    @Override
	    protected void onPause() {
	        super.onPause();
	    }

	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
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



