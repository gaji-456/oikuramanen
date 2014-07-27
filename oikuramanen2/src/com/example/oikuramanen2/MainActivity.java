package com.example.oikuramanen2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.R.string;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		Thread httpget = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getKawase();
			}
		});
		
		httpget.start();
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	// 
	public String doGet(String url) {
		try {
			HttpGet method = new HttpGet(url);
			
			DefaultHttpClient client = new DefaultHttpClient();

			// ヘッダを設定する
			method.setHeader("Connection", "Keep-Alive");

			HttpResponse response = client.execute(method);
			int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK)
				throw new Exception("");

			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 
	public float getKawase() {
		String html = doGet("http://shiro-kuro.tv/money/kawase/");
		// 
		Pattern pattern = Pattern.compile("SIZE=\"2\">([\\d.]+)</FONT>");
		Matcher matcher = pattern.matcher(html);
		ArrayList list = new ArrayList<>();
		while (matcher.find()) { // Find each match in turn; String can't do this.
			
			String name = matcher.group(1); // Access a submatch group; String can't
			list.add(name);
				
		}
		// Log.d("oikuramanen=======", (String)list.get(list.size()-3));
		
		
		
		float kawase = Float.valueOf((String) list.get(list.size()-3));
		
		Log.d("oikuramanen*******", String.valueOf(kawase));
		
		return kawase;
		
	}

}
