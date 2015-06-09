package com.example.wechat;


import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
	private EditText account;
	private EditText pwd;
	private Button login;
	
	private String accountStr=null;
	private String password=null;
	private HttpClient httpClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		account=(EditText) findViewById(R.id.account);
		pwd=(EditText) findViewById(R.id.password);
		login=(Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accountStr=account.getText().toString();
				password=pwd.getText().toString();
				Multithread m = new Multithread(accountStr,password);
				m.start();		
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	class Multithread extends Thread {
		String account=null;
		String password=null;
		Multithread(String account, String password){
			this.account=account;
			this.password=password;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			httpClient= new DefaultHttpClient();
			httpClient= new DefaultHttpClient();  
	        try{    
	            @SuppressWarnings("deprecation")
				HttpPost post = new HttpPost("http://192.168.8.3:5000/");  
	            @SuppressWarnings("deprecation")
				List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("account" , this.account));
	            params.add(new BasicNameValuePair("password" , this.password)); 
	            post.setEntity(new UrlEncodedFormEntity(  
	                params,HTTP.UTF_8));  
	            HttpResponse httpResponse = httpClient.execute(post);   
	            if (httpResponse.getStatusLine()  
	                .getStatusCode() == 200)  
	            {  
	                String result = EntityUtils  
	                    .toString(httpResponse.getEntity());  
	                Log.i("return result", result);
	                JSONObject  dataJson=new JSONObject(result);
	                String accountFromJson=dataJson.getString("account");
	                String passwordFromJson=dataJson.getString("password");
	                
	                Intent intent=new Intent();
	                intent.putExtra("account", accountFromJson);
	                intent.putExtra("password", passwordFromJson);
	                intent.setClass(MainActivity.this, Welcome.class);
					startActivity(intent);
	            }
	            else {
	            	Toast.makeText(MainActivity.this, "Wrong account or password! Please try again!", Toast.LENGTH_SHORT).show();
	            }  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }finally{  
	            httpClient.getConnectionManager().shutdown();  
	        }  
	        //return null; 
			
		}
		
	}
}
