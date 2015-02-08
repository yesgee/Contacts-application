package contactbook;

import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import contactbook.library.JSONParser;

import java.util.ArrayList;
import java.util.List;

import Phonebook.contactbook.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AddUserActivity extends Activity {
	public EditText edit_message, edit_message0, edit_message1, edit_message2;
	   private static String url = "http://192.168.1.109:8080/api/contacts";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);
		Button adduser = (Button)findViewById(R.id.adduser);
		edit_message = (EditText)findViewById(R.id.edit_message);
        edit_message0 = (EditText)findViewById(R.id.edit_message0);
        edit_message1 = (EditText)findViewById(R.id.edit_message1);
        edit_message2 = (EditText)findViewById(R.id.edit_message2);
        adduser.setOnClickListener(new View.OnClickListener() {
  	      @Override
  	      public void onClick(View view) {
  	             new JSONParsePost().execute();
  	           
  	    
  	      }	});
        
	
	   
	      
	          
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_user, menu);
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
	
	public void showMessage(View view) {
		
		Intent intent1 = new Intent(this, MainActivity.class);
	         
	         startActivity(intent1);
         }



	private class JSONParsePost extends AsyncTask<String, String, JSONObject> {
		View view;
        private ProgressDialog pDialog;
        List<NameValuePair> params;
       @Override
         protected void onPreExecute() {
             super.onPreExecute();
             
             pDialog = new ProgressDialog(AddUserActivity.this);
                  pDialog.setMessage("Contact being added ...");
                  pDialog.setIndeterminate(false);
                  pDialog.setCancelable(true);
                  pDialog.show();
              }
       @Override
         protected JSONObject doInBackground(String... args) {
         JSONParser jParser = new JSONParser();
         params = new ArrayList<NameValuePair>();
         
         params.add(new BasicNameValuePair("firstname", edit_message.getText().toString()));
         params.add(new BasicNameValuePair("lastname", edit_message0.getText().toString()));
         params.add(new BasicNameValuePair("email", edit_message1.getText().toString()));
         params.add(new BasicNameValuePair("phone", edit_message2.getText().toString()));
         
         // Getting JSON from URL
         JSONObject json = jParser.postJSONFromUrl(url,params);
         return json;
       }
        @Override
          protected void onPostExecute(JSONObject json) {
          pDialog.dismiss();
          // Adding value HashMap key => value

          showMessage(view);
          super.onPostExecute(json);
        }
     }

}