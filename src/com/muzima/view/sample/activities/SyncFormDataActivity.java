package com.muzima.view.sample.activities;
 
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL; 

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.lucene.queryParser.ParseException;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.util.Constants;
import com.muzima.view.sample.R;
import com.muzima.view.sample.utilities.StringConstants;
 
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
 
public class SyncFormDataActivity extends Activity implements OnClickListener{
 
	private EditText value;
	
	private Button btn;
	
	private static final String METHOD_POST = "POST";
	
	private static final String URL = "/ws/rest/v1/muzima/queueData";



    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync_form_data);
		
		String formsubmissionJson = getIntent().getStringExtra("formdata");
	    String URF = (getString(R.string.default_server) + URL);
		
	    System.out.println("url is" +URF);
		
		/* Testing to see if  we can get the json string from form in webview*/
			
		System.out.println("submit 56565 ======= " + formsubmissionJson);  
		
		btn=(Button)findViewById(R.id.button1);
		
		btn.setOnClickListener(this);
		
	}
		 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	
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
	
	public void onClick(View v) {
	// TODO Auto-generated method stub
		String formsubmissionJson = getIntent().getStringExtra("formdata");
		new MyAsyncTask().execute(formsubmissionJson.toString());	
	 
	}
		 
	private class MyAsyncTask extends AsyncTask<String, String, String>{
			
	@Override
	protected void onPreExecute() {
	   super.onPreExecute();
				           
	}
	
	@Override
	protected String doInBackground(String... args) {
		try {
				postingQueueData();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		// TODO Auto-generated method stub
			return null;
	}
		 
	protected void onPostExecute(Double result){
		
	Toast.makeText(getApplicationContext(), "Form Data has been sent to server", Toast.LENGTH_LONG).show();
		
		// Start ListPatient activity
    Intent ip = new Intent(getApplicationContext(), ListPatientActivity.class);
       startActivity(ip);
       
	}
		
		
	public void postingQueueData() throws Exception   {
		    String formsubmissionJson = getIntent().getStringExtra("formdata");
		    URL url = new URL("http://192.168.1.3:8081/openmrs-standalone/ws/rest/v1/muzima/queueData");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
		   // String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
		    connection.setRequestProperty("Authorization", encodedAuthorization);
		    connection.setRequestMethod(METHOD_POST);
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);
		
		    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		    writer.write(formsubmissionJson);
		    writer.flush();
		
		    InputStreamReader reader = new InputStreamReader(connection.getInputStream(),"UTF-8");
		    int responseCode = reader.read();
		    if (responseCode == HttpServletResponse.SC_OK
		            || responseCode == HttpServletResponse.SC_CREATED) {
		        //log.info("Queue data created!");
		    }
		
		    reader.close();
		    writer.close();
		   }
		 
		 
		  }
	
	}