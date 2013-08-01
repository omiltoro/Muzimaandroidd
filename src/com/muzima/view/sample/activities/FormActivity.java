package com.muzima.view.sample.activities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.AuthenticationException;
import org.apache.lucene.queryParser.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;

import com.muzima.api.context.Context;
import com.muzima.api.model.Patient;
import com.muzima.api.service.PatientService;
import com.muzima.search.api.util.StringUtil;
import com.muzima.view.sample.R;
import com.muzima.view.sample.adapters.PatientAdapter;

import com.muzima.view.sample.adapters.PatientAdapter;
import com.muzima.view.sample.tasks.DownloadPatientTask;
import com.muzima.view.sample.tasks.DownloadTask;
import com.muzima.view.sample.utilities.FileUtils;
import com.muzima.view.sample.utilities.StringConstants;
import org.apache.lucene.queryParser.ParseException;
import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Patient;
import com.muzima.api.service.PatientService;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;

import com.muzima.view.sample.utilities.StringConstants;


import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




public class FormActivity extends Activity {
	 private static final String TAG = FormActivity.class.getSimpleName();
	private final Log log = LogFactory.getLog(FormActivity.class);

    private static final String METHOD_POST = "POST";

    private static String formsubmissionJson;

    private static String encounterJson;
    
    private static String provider_uuid;
    private static String location_uuid;
    private static String obs_uuid;
    private static String type;
    private static String value;
    private static String person;
    private static String encounterdatetime;
    private TextWatcher textWatcher;
    
    private ArrayAdapter<Patient> patientAdapter;
    
    private Context context;

    
   private static String consultationJson;
   
    private Button btn;
    private ProgressBar pb;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		
		Button btnposts = (Button) findViewById(R.id.bn_post);
		
		     
	
	
		

		btnposts.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
	
		int position = 0;
	
		
		final Spinner feedbackSpinner = (Spinner) findViewById(R.id.spinner2);  
		String provider = feedbackSpinner.getSelectedItem().toString(); 

			
			if (provider.equals("Win")) 
				provider_uuid= ("3c023ab8-82ff-11e2-96ef-f0def12f7061");
			
				else
					if (provider.equals("Admin"))
						
						provider_uuid= ("3c023ab8-82ff-11e2-96ef-f0def12f7061");
		
			final Spinner locationSpinner = (Spinner) findViewById(R.id.spinner3);  
			String location = locationSpinner.getSelectedItem().toString(); 
			
			if(location.equals("Reach"))
				location_uuid=("e1b1d9eb-69a2-4843-bccc-e73e33c70e4c");
			else
				if(location.equals("Ampath"))
			location_uuid=("3d0c01b7-0306-45cd-a86f-8c96fee0b012");
			
			final Spinner SputumAcidSpinner = (Spinner) findViewById(R.id.spinner1);  
			String Sputtest = SputumAcidSpinner.getSelectedItem().toString(); 
			
			if (Sputtest.equals("THREEPLUS")){
				obs_uuid=("307AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				type=("coded");
				value=("1364AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			} else 
							
					if(Sputtest.equals("ONEPLUS")){
						obs_uuid=("307AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
						type=("coded");
						value=("1362AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
					}
					else
						
						if(Sputtest.equals("TwOPLUS")){
							obs_uuid=("307AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
							type=("coded");
							value=("1363AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
						} else
							if(Sputtest.equals("NEGATIVE")){
								obs_uuid=("307AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
								type=("coded");
								value=("664AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			
							}else
								if(Sputtest.equals("POSITIVE")){
									obs_uuid=("307AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
									type=("coded");
									value=("703AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				
								}
			final Spinner SputumAppearanceSpinner = (Spinner) findViewById(R.id.spinner4);  
			String Appearance = SputumAppearanceSpinner.getSelectedItem().toString(); 
			if(Appearance.equals("Purulent")){
				obs_uuid=("159969AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				type=("coded");
				value=("1076AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

			} else
				if(Appearance.equals("Red")){
					obs_uuid=("159969AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
					type=("coded");
					value=("127778AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				}
				else
					if(Appearance.equals("Saliva")){
						obs_uuid=("159969AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
						type=("coded");
						value=("160013AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
						
					}else
						if(Appearance.equals("Mucopulurent")){
							obs_uuid=("159969AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
							type=("coded");
							value=("160014AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");}
			
			Calendar c = Calendar.getInstance();
			System.out.println("Current time => "+c.getTime());

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedDate = df.format(c.getTime());
			encounterdatetime =formattedDate.toString();
			// Now formattedDate have current date/time
			//Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
				
			JSONObject formObject = new JSONObject();
			
			
	        try {
				formObject.put("dataSource", "954b9e59-eba0-4679-afbb-2878580c054d");
				 formObject.put("discriminator", "encounter");
			        
			        JSONObject payload = new JSONObject();
			        
			        
					JSONObject encounter= new JSONObject();
					encounter.put("form.uuid","fe9fc58f-d196-46cb-956d-f46445f558d9");
					encounter.put("encounterType.uuid","bdb58960-4d91-4ca7-a27a-8dabde40ec12");
					encounter.put("provider.uuid",provider_uuid);
					encounter.put("location.uuid",location_uuid);
					encounter.put("datetime",encounterdatetime);
					
					System.out.println("Encounter Object: **************"+ encounter.toString());
					
					
					JSONObject person= new JSONObject();
					person.put("person.uuid","" );//replace value with this.getString(R.Id)

					JSONArray obs=new JSONArray();
					JSONObject o = new JSONObject();
					o.put("uuid", obs_uuid);//obs uuid for SPUTUM FOR ACID FAST BACILLI
					o.put("type",type);//coded
					o.put("value",value);
					obs.put(o);
					
					payload.put("person",person); // Device type
					payload.put("encounter",encounter);
					payload.put("obs",obs);
					
					
					
			       
			        formObject.put("payload", payload);
			      
			        formsubmissionJson = formObject.toString();
			        
			        
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FormActivity.this);
	        
            // Setting Dialog Title
            alertDialog.setTitle("Post Form data...");

            // Setting Dialog Message
            alertDialog.setMessage("Do you want to post data ?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.save);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on YES",
                                    Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), encounterdatetime, Toast.LENGTH_SHORT).show();
                new MyAsyncTask().execute();	
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // User pressed No button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                }
            });

            // Setting Netural "Cancel" Button
            alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // User pressed Cancel button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on Cancel",
                                    Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
	        
		//b.setVisibility(View.VISIBLE);
			
			
		
	} });
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
			MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.layout.menu, menu);
		return true;
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
       // Log.e(TAG, "Unable to authenticate the current context.", e);
    }

    return context;
}

public void postingQueueData() throws Exception   {
    //URL url = new URL("http://192.168.1.5:8081/openmrs-standalone/ws/rest/v1/muzima/queueData");
	  URL url = new URL(getString(R.string.default_server)+"/ws/rest/v1/muzima/queueData");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    //String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
    String encodedAuthorization = "Basic " + Base64.encodeToString("admin:test".getBytes(), Base64.NO_WRAP);
   // Base64.encode("admin:test".getBytes());
  //  String encoding = Base64.encodeToString(AuthenticationException.getBytes(), Base64.NO_WRAP);
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
        log.info("Queue data created!");
    }

    reader.close();
    writer.close();
}
 
protected void onPostExecute(Double result){
	Intent ip = new Intent(getApplicationContext(), MuzimaMainActivity.class);
    startActivity(ip);
//pb.setVisibility(View.GONE);
//Toast.makeText(getApplicationContext(), "post sent", Toast.LENGTH_LONG).show();
}
protected void onProgressUpdate(Integer... progress){
//pb.setProgress(progress[0]);
}
 

 
}

}
