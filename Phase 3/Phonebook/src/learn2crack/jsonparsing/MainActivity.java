package contactbook;
import java.util.ArrayList;
import java.util.HashMap;
import contactbook.DIsplayContactActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import contactbook.library.JSONParser;
@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	ListView list;
	TextView uid;
	TextView fname;
	TextView lname;
	TextView email;
	TextView phone;
	Button Btngetdata;
    Button Btnsee;
	public HashMap<String, String> sendlist;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

  private static String url = "http://130.233.193.179:3000/userlist";
  //JSON Node Names
  
 
  
  private static final String TAG_USER = "users";
  private static final String TAG_ID = "_id";
  private static final String TAG_FNAME = "first_name";
  private static final String TAG_LNAME = "last_name";
  private static final String TAG_EMAIL = "email";
  private static final String TAG_PHONE = "phone_num";
  String dispfname,displname,dispphone,dispemail,dispid;
  JSONArray user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    // Creating new JSON Parser
  
        oslist = new ArrayList<HashMap<String, String>>();
        Btngetdata = (Button)findViewById(R.id.getdata);
//        Btnsee = (Button)findViewById(R.id.see);
        
        
//        Btngetdata.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//             new JSONParse().execute();
//      }
//      
//          
//    });
//        
        new JSONParse().execute();
    } 
    
    public void sendMessage(View view) {
        Intent intent = new Intent(this, AddUserActivity.class);
        
        startActivity(intent);
    }
    
     
   
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
       @Override
         protected void onPreExecute() {
             super.onPreExecute();
              uid = (TextView)findViewById(R.id.uid);
        fname = (TextView)findViewById(R.id.fname);
        lname = (TextView)findViewById(R.id.lname);
        email = (TextView)findViewById(R.id.email);
        phone = (TextView)findViewById(R.id.phone);
         
        pDialog = new ProgressDialog(MainActivity.this);
             pDialog.setMessage("Getting Data ...");
             pDialog.setIndeterminate(false);
             pDialog.setCancelable(true);
             pDialog.show();
       }
       @Override
         protected JSONObject doInBackground(String... args) {
         JSONParser jParser = new JSONParser();
         // Getting JSON from URL
         JSONObject json = jParser.getJSONFromUrl(url);
         return json;
       }
        @Override
          protected void onPostExecute(JSONObject json) {
          pDialog.dismiss();
          try {
             // Getting JSON Array from URL
           //  android = json.getJSONArray(TAG_OS);
             
             user = json.getJSONArray(TAG_USER);
             for(int i = 0; i < user.length(); i++){
             JSONObject c = user.getJSONObject(i);
             // Storing  JSON item in a Variable
             String uid = c.getString(TAG_ID);
             String fname = c.getString(TAG_FNAME);
             String lname = c.getString(TAG_LNAME);
             String email = c.getString(TAG_EMAIL);
             String phone = c.getString(TAG_PHONE);
             
             // Adding value HashMap key => value
             final HashMap<String, String> map = new HashMap<String, String>();
             
                 
             map.put(TAG_ID, uid);
             map.put(TAG_FNAME, fname);
             map.put(TAG_LNAME, lname);
             map.put(TAG_EMAIL, email);
             map.put(TAG_PHONE, phone);
             
             
             oslist.add(map);
             list=(ListView)findViewById(R.id.list);
      
   
             
             ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
                     R.layout.list_v,
                     new String[] { TAG_FNAME}, new int[] {
                        R.id.fname});
             
             
             list.setAdapter(adapter);
 
             
             list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view,
                                             int position, long id) {
                    		Intent intent = new Intent(MainActivity.this, DIsplayContactActivity.class);
            
                    	 dispfname = oslist.get(position).get("first_name");
                    	 displname = oslist.get(position).get("last_name");
                    	 dispphone = oslist.get(position).get("phone_num");
                    	 dispemail = oslist.get(position).get("email");
                    	 	
                    	 dispid = oslist.get(position).get("_id");
            
                    	  intent.putExtra("fname",dispfname);
                    	  intent.putExtra("lname",displname);
                    	  intent.putExtra("phone",dispphone);
                    	  intent.putExtra("email",dispemail);
                    	  intent.putExtra("id",dispid);
                    	  
              Toast.makeText(MainActivity.this, "You Selected to display "+dispfname, Toast.LENGTH_SHORT).show();
              startActivity(intent); 
                     }
                 });
             
    
             
           
             }
             
            
             
         } catch (JSONException e) {
           e.printStackTrace();
         }
        }
     }
 }
    
    
   