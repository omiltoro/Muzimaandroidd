package com.muzima.view.sample.activities;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.muzima.view.sample.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Base64;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private final Log log = LogFactory.getLog(MainActivity.class);

 


 private static final String METHOD_POST = "POST";
 
 private static String formsubmissionjson;
 
 private WebView wv;  
 
 private ProgressBar progress;
 
 private Button btn;

public void TestMethod(String formData) {  
 System.out.println("TestMethod FormData " + formData);  
 try {  
   JSONObject jsonData = new JSONObject(formData);  
   formsubmissionjson = jsonData.toString();
   
 } catch (JSONException e) {  
   e.printStackTrace();  
 }  
 wv.loadUrl("javascript:onSubmit('" + formData + "');");  
 Toast.makeText(getApplicationContext(), formData, 50000).show();  
// System.out.println("TestMethod FormData " + formsubmissionjson);  
 /*Intent ip = new Intent(getApplicationContext(),MuzimaMainActivity.class);
 startActivity(ip);
 */
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
  wv.setWebChromeClient(new WebChromeClient());
  
  progress = (ProgressBar) findViewById(R.id.progressBar);
  progress.setMax(100);
  // requires javascript  
  wv.getSettings().setJavaScriptEnabled(true);  
  // make this activity accessible to javascript  
  wv.addJavascriptInterface(this, "android");
  // set the html view to load sample.html
  wv.loadUrl("file:///android_asset/patientform.html");  
  MainActivity.this.progress.setProgress(100);
  
  Button btn = (Button) findViewById(R.id.goButton);
  btn.setOnClickListener(new OnClickListener() {

     

	@Override

	public void onClick(View arg0) {
		int position = 0;
		// TODO Auto-generated method stub
		
		new MyAsyncTask().execute();	
		
	}
  });

  
}  

private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
	 
@Override
protected Double doInBackground(String... params) {
// TODO Auto-generated method stub
try {
	postingQueueData();
	
	
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return null;
}


public void postingQueueData() throws Exception   {
    URL url = new URL("http://192.168.1.3:8081/openmrs-standalone/ws/rest/v1/muzima/queueData");
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    //String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
    String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
    connection.setRequestProperty("Authorization", encodedAuthorization);
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setRequestMethod(METHOD_POST);
    connection.setDoOutput(true);
    
    

    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
    writer.write(formsubmissionjson);
    writer.flush();

    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
    int responseCode = reader.read();
    if (responseCode == HttpServletResponse.SC_OK
            || responseCode == HttpServletResponse.SC_CREATED) {
        log.info("Queue data created!");
    }

    reader.close();
    writer.close();
   

}
 
protected void onPostExecute(Double result){
	Intent ip = new Intent(getApplicationContext(), ListPatientActivity.class);
    startActivity(ip);
//pb.setVisibility(View.GONE);
//Toast.makeText(getApplicationContext(), "post sent", Toast.LENGTH_LONG).show();
}
protected void onProgressUpdate(Integer... progress){
//pb.setProgress(progress[0]);
}
 

 
}
private class MyWebViewClient extends WebChromeClient { 
    @Override
    public void onProgressChanged(WebView view, int newProgress) {          
        MainActivity.this.setValue(newProgress);
        super.onProgressChanged(view, newProgress);
    }
}

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
 // getMenuInflater().inflate(R.menu.activity_main, menu);
	 MenuInflater menuInflater = getMenuInflater();
     menuInflater.inflate(R.layout.menu, menu);

  return true;
 }
 
 public void setValue(int progress) {
     this.progress.setProgress(progress);        
 }
 
/* public void postingQueueData() throws Exception   {
	    URL url = new URL("http://192.168.1.3:8081/openmrs-standalone/ws/rest/v1/muzima/queueData");
		  //URL url = new URL(getString(R.string.default_server)+"/ws/rest/v1/muzima/queueData");
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    //String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
	    String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
	   // Base64.encode("admin:test".getBytes());
	  //  String encoding = Base64.encodeToString(AuthenticationException.getBytes(), Base64.NO_WRAP);
	    connection.setRequestProperty("Authorization", encodedAuthorization);
	    connection.setRequestMethod(METHOD_POST);
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");

	    connection.setDoOutput(true);
	    
	    connection.setChunkedStreamingMode(0);

	    connection.setInstanceFollowRedirects(false);

	    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	    writer.write(formsubmissionjson);
	    writer.flush();

	    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
	    
	    int responseCode = reader.read();
	    if (responseCode == HttpServletResponse.SC_OK
	            || responseCode == HttpServletResponse.SC_CREATED) {
	        log.info("Queue data created!");
	    }

	    reader.close();
	    writer.close();
	}*/
 
}
