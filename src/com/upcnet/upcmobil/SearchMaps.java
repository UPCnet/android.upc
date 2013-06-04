package com.upcnet.upcmobil;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchMaps extends MyListActivity {


	//static final String URL = "http://cercador.upc.edu/search?site=maps_units&client=default_frontend&output=xml_no_dtd&getfields=id_edifici&num=100&filter=0&q=";
	static final String WS_SEARCH ="http://nucli.upc.edu/ws/CercadorMapsv1.php?text=";
	public static final String LAT_POINT ="LATITUDE_SEARCH_";
	public static final String LON_POINT ="LONGITUDE_SEARCH_";
	public static final String NUM_POINTS ="NUMBER_OF_POINTS";
	public static final String ID_POINT ="ID_POINT_";
	public static final String NAME_POINT ="NAME_POINT_";
	public static final String TIPUS_POINT ="TYPE_POINT_";

	static final String WS_NOM ="nom_ca";
	static final String WS_ID = "id";
	static final String WS_COORDS = "coords";
	static final String WS_LAT = "lat";
	static final String WS_LON = "lon";
	static final String WS_TIPUS = "tipus";
	
	
	private final int XML_TIMEOUT = 30000;
	//final XMLParser parser = new XMLParser();
//	static final String KEY_ITEM = "R"; // parent node

	MyJSONArrayAdapter jsonArrayAdapter;
	JSONArray resultats;
	TextView textLoading;
	ProgressBar iconLoading;
	RelativeLayout searchRelLayout;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_list);
		//Toast.makeText(this, "test",Toast.LENGTH_SHORT).show();

		
		textLoading = (TextView) findViewById(R.id.searchLodading);
		iconLoading = (ProgressBar) findViewById(R.id.searchProgressBar);
		searchRelLayout = (RelativeLayout) findViewById(R.id.searchRelLayout);
		
		Bundle extras = getIntent().getExtras();


		if (extras != null) {
			String searchText = extras.getString(MapsActivity.SEARCH_PREF);


			if (searchText != null) {

				if (Utils.isNetworkAvailable(getApplicationContext())) {

					// Inici paste JSON

					textLoading.setText(this.getResources().getString(R.string.loading));
					iconLoading.setVisibility(View.VISIBLE);
					

					new DownloadJSON().execute(searchText);


					

				}

					else {
						Toast.makeText(this, getResources().getString(R.string.error_webservice_network), Toast.LENGTH_LONG).show();


						finish();

					}


			}
		}
	}

		public void submitResults(View v){



			Bundle extras = new Bundle();

			extras.clear();
			
			
			
			int num_punts = 0;
			for (int i=0; i < jsonArrayAdapter.getCount(); i++) {
				try {
					Log.v("TEST", "Position "+ i + " to " +jsonArrayAdapter.isSelected(i) );

					if (jsonArrayAdapter.isSelected(i)){

						Double latitude = Double.parseDouble(resultats.getJSONObject(i).getJSONObject(WS_COORDS).getString(WS_LAT));
						Double longitude = Double.parseDouble(resultats.getJSONObject(i).getJSONObject(WS_COORDS).getString(WS_LON));
						String name = resultats.getJSONObject(i).getString(WS_NOM);
						extras.putDouble(LAT_POINT + num_punts, latitude );
						extras.putDouble(LON_POINT + num_punts, longitude);
						extras.putString(NAME_POINT + num_punts, name );
						extras.putString(ID_POINT + num_punts, resultats.getJSONObject(i).getString(WS_ID));
						extras.putString(TIPUS_POINT + num_punts, resultats.getJSONObject(i).getString(WS_TIPUS));

						Log.v("test","Latitude :" +latitude);
						Log.v("test","Longitude :" + longitude);
						Log.v("test","Name: "+name );


						num_punts ++;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			extras.putInt(NUM_POINTS, num_punts);


			Intent i = new Intent(this,MapsResultActivity.class);



			i.putExtras(extras);

			startActivity(i);
			//i.putExtras(bundle);


		}

		private void saveData(String param){
			try {
				String url_param = URLEncoder.encode(param,"UTF-8");
				resultats = JSONfunctions.getJSONArrayfromURL(WS_SEARCH + url_param);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

		private void fillData() {
			iconLoading.setVisibility(View.INVISIBLE);
			if (resultats !=null) {
				jsonArrayAdapter = new MyJSONArrayAdapter(this, resultats, WS_NOM, WS_ID);
				SearchMaps.this.setListAdapter(jsonArrayAdapter);
				//Toast.makeText(this, "Fi execució", Toast.LENGTH_LONG).show();
				//textLoading.setVisibility(INVISIBLE);
				searchRelLayout.setVisibility(View.GONE);
				


			}
			else {
				//error al JSON
				Toast.makeText(this, getResources().getString(R.string.error_webservice), Toast.LENGTH_LONG).show();
				textLoading.setText(this.getResources().getString(R.string.error_webservice));
				textLoading.setVisibility(View.INVISIBLE);


				finish();
			}

		}
		

private class DownloadJSON extends AsyncTask<String,Integer,Long> {

	@Override
	protected Long doInBackground(String... params) {
		saveData(params[0]);
		return (long) 100;
	}
	
	protected void onPostExecute(Long result) {
		fillData();
	}
	
	
}



	}
