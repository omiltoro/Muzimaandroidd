package com.muzima.view.sample.activities;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.muzima.view.sample.R;

public class FormActivity extends Activity {
	
	private static final String TAG = null;
	 
	private WebView wv; 
	
	private static final String METHOD_POST = "POST";
	 
	private static String formsubmissionJson;
	 
	@Override  
	@JavascriptInterface
	public void onCreate(Bundle savedInstanceState) {  
		  super.onCreate(savedInstanceState);  
		  setContentView(R.layout.activity_main);
		  
		  wv = (WebView) this.findViewById(R.id.webView1);  
		  
		  // requires javascript  
		  wv.getSettings().setJavaScriptEnabled(true); 
		  
		  // make this activity accessible to javascript  
		  wv.addJavascriptInterface(this, "android");
		  
		  wv.setWebChromeClient(new WebChromeClient());
		  
		  // set the html view to load sample.html
		  wv.loadUrl("file:///android_asset/regtbform.html"); 
		 
	}  	
	
	@JavascriptInterface
	public void TestMethod(String formData) {  
		
		/*Json string from form*/
		   System.out.println("TestMethod FormData " + formData);  
			 try {  
			 		  JSONObject jsonData = new JSONObject(formData);  
			 		   
			 	  } catch (JSONException e) {  
			 		   e.printStackTrace();  
			 		 }  
			 
		   wv.loadUrl("javascript:onSubmit('" + formData + "');");  
		   formsubmissionJson = formData.toString();
		   
		   System.out.println("Form json is ======= " + formData);  
		   Toast.makeText(getApplicationContext(), formData, 50000).show(); 
		   
		   Intent ip = new Intent(getApplicationContext(), SyncFormDataActivity.class);
		   ip.putExtra("formdata", formData);
		   startActivity(ip);
			 		 
	}  
		
	public void postingQueueData() throws Exception   {
		    URL url = new URL("http://192.168.1.3:8081/openmrs-standalone/ws/rest/v1/muzima/queueData");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
		    connection.setRequestProperty("Authorization", encodedAuthorization);
		    connection.setRequestMethod(METHOD_POST);
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);
	
		    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		    writer.write(formsubmissionJson);
		    writer.flush();
	
		    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		    int responseCode = reader.read();
		    if (responseCode == HttpServletResponse.SC_OK
		       || responseCode == HttpServletResponse.SC_CREATED) {
		      //  Log.info("Queue data created!");
		    }
	
		    reader.close();
		    writer.close();
			  
	}
	
	private class sendformTask extends AsyncTask<URL, Integer, Long> {
	        protected Long doInBackground(URL... urls) {
		    	 try {
					postingQueueData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
		     }
	
             protected void onProgressUpdate(Integer... progress) {
		       
		     }
	
		     protected void onPostExecute(Long result) {
		        
		     }
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
		   //Inflate the menu; this adds items to the action bar if it is present.
		  // getMenuInflater().inflate(R.menu.activity_main, menu);
			 MenuInflater menuInflater = getMenuInflater();
		     menuInflater.inflate(R.layout.menu, menu);
	
		  return true;
		 }
		 
	}



