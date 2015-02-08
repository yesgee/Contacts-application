package contactbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import contactbook.MainActivity;
import contactbook.library.JSONParser;

import android.widget.ListView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import Phonebook.contactbook.R;


public class DIsplayContactActivity extends Activity {


	 ListView lview;
	 ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	 String ifname,ilname,iphone,iemail,id;
	 TextView tfname,tlname,temail,tphone,tid;
	 View view;
	 private static String url = "http://192.168.1.109:8080/api/contacts";
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact);
		
 ifname = (String) getIntent().getStringExtra("fname");
 ilname = (String) getIntent().getStringExtra("lname");
 iemail = (String) getIntent().getStringExtra("email");
 iphone = (String) getIntent().getStringExtra("phone");
 id = (String) getIntent().getStringExtra("id");
 
 tfname = (TextView)findViewById(R.id.fname);
 tlname = (TextView)findViewById(R.id.lname);
 temail = (TextView)findViewById(R.id.email);
 tphone = (TextView)findViewById(R.id.phone);
 
 
tfname.setText("First name: " + ifname);
tlname.setText("Last name: " + ilname);
tphone.setText("Phone: "+iphone);
temail.setText("Email: "+iemail);

Button deluser = (Button)findViewById(R.id.deluser);
deluser.setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View view) {
	             new JSONParseDel().execute();
	           
	    
	      }	});

  
	}
		private class JSONParseDel extends AsyncTask<String, String, JSONObject> {
			View view;
	        private ProgressDialog pDialog;
	        List<NameValuePair> params;
	       @Override
	         protected void onPreExecute() {
	             super.onPreExecute();
	             
	             pDialog = new ProgressDialog(DIsplayContactActivity.this);
	                  pDialog.setMessage("Contact being added ...");
	                  pDialog.setIndeterminate(false);
	                  pDialog.setCancelable(true);
	                  pDialog.show();
	              }
	       @Override
	         protected JSONObject doInBackground(String... args) {
	         JSONParser jParser = new JSONParser();
	         params = new ArrayList<NameValuePair>();
	         
	         params.add(new BasicNameValuePair("id", id));
	         Log.d("params",params.toString());
	         
	         // Getting JSON from URL
	         JSONObject json = jParser.delJSONFromUrl(url,params);
	         return json;
	       }
	        @Override
	          protected void onPostExecute(JSONObject json) {
	          pDialog.dismiss();
	          // Adding value HashMap key => value

	        delMessage(view);
	          super.onPostExecute(json);
	        }
	     }

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.del, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		
		public void delMessage(View view) {
			
			Intent intent1 = new Intent(this, MainActivity.class);
		         
		         startActivity(intent1);
	         }

	
}
