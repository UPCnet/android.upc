package com.upcnet.upcmobil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoEdificiActivity extends MyActivity {

	//static final String URL = "http://cercador.upc.edu/search?site=maps_units&client=default_frontend&output=xml_no_dtd&getfields=id_edifici&num=100&filter=0&q=";
	static final String WS_SEARCH ="http://nucli.upc.edu/ws/InfoEdificiv1.php?id=";

	public static final String WS_NOM_CAMPUS = "campus_ca";
	public static final String WS_NOM = "nom_ca";

	public static final String WS_DIRECCIO ="adreca_sencera";
	public static final String WS_CP = "codi_postal";
	public static final String WS_LOCALITAT = "localitat";
	public static final String WS_COORD = "coord";
	public static final String WS_COORD_LAT = "lat";
	public static final String WS_COORD_LON = "lon";
	public static final String WS_UNITATS = "unitats";

	public static final String WS_TELEFON = "telefon";

	public static final String WS_UNITAT_ID = "id";
	public static final String GOOGLE_MAPS_URL = "http://maps.google.com/maps?";

	String my_titol = "";







	public static final String WS_MAIL = "email";

	public static final String WS_WEB = "web_ca";

	// public static final String resta dades unitats falten...


	TextView titol;
	TextView info1;
	//TextView info2;
	//TextView info3;
	TextView infoTelefon;
	TextView infoMail;
	TextView infoWWW;
	LinearLayout btnCompartirUbicacio;
	LinearLayout btnComAnar;

	JSONObject resultats;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_info_edifici);
		//Toast.makeText(this, "test",Toast.LENGTH_SHORT).show();


		titol = (TextView) findViewById(R.id.buildingTitol);
		info1 = (TextView) findViewById(R.id.buildingInfo1);
		//info2 = (TextView) findViewById(R.id.edificiInfo2);
		//info3 = (TextView) findViewById(R.id.edificiInfo3);
		btnCompartirUbicacio = (LinearLayout) findViewById(R.id.unitatBotons2);
		btnComAnar = (LinearLayout) findViewById(R.id.unitatBotons);




		Bundle extras = getIntent().getExtras();


		if (extras != null) {
			String searchText = extras.getString(UpcOverlayItem.PREF_ID);


			if (searchText != null) {

				if (Utils.isNetworkAvailable(getApplicationContext())) {



					new DownloadJSON().execute(searchText);




				}

				else {
					Toast.makeText(this, getResources().getString(R.string.error_webservice_network), Toast.LENGTH_LONG).show();


					finish();

				}


			}
		}
	}



	private void saveData(String param){
		resultats = JSONfunctions.getJSONfromURL(WS_SEARCH + param);
		//        JSONObject json = JSONfunctions.getJSONfromURL("http://alderamin1.upc.es/estudis_grau.php");

	}

	private void fillData() {
		if (resultats !=null) {


			try {
				//Toast.makeText(this, "Fi execució", Toast.LENGTH_LONG).show();

				String my_titol = "";
				if (resultats.getString(WS_NOM) != null  && !resultats.getString(WS_NOM).equals("")) {
					my_titol = resultats.getString(WS_NOM);
				}


				titol.setText(my_titol);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			try {
				info1.setText(resultats.getString(WS_DIRECCIO));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//info2.setText(resultats.getString(WS_DIRECCIO));
			//info3.setText(resultats.getString(WS_LOCALITAT));





			int campsBuits = 0;

			try {
				JSONArray jsongraus = resultats.getJSONArray(WS_UNITATS);

				LinearLayout listgraus = (LinearLayout) findViewById(R.id.buildingListGrau);

				if (jsongraus == null || jsongraus.length() == 0) {
					listgraus.setVisibility(View.GONE);
					findViewById(R.id.buildingUnitatsTitol).setVisibility(View.GONE);
					campsBuits++;

				}
				else {

					for (int i = 0; i < jsongraus.length(); i++) {
						// View vi =  getLayoutInflater().inflate(R.layout.edifici_fitxa_single, null);
						LinearLayout layout = new LinearLayout(this);
						layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,             LayoutParams.WRAP_CONTENT));
						TextView vi_text = new TextView(this);
						vi_text.setText(jsongraus.getJSONObject(i).getString(WS_NOM));
						vi_text.setTextColor(Color.parseColor("#007BC0"));
						LayoutParams layoutP = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
						((MarginLayoutParams) layoutP).setMargins(7,5,2,2);
						vi_text.setLayoutParams(layoutP);
						final String unitat_id = jsongraus.getJSONObject(i).getString(WS_UNITAT_ID);

						if (unitat_id.length() > 0 && !unitat_id.equals(null)) {
							vi_text.setOnClickListener(new OnClickListener() {						
								@Override
								public void onClick(View v) {
									try {
										/*
										   Intent intent = new Intent(Intent.ACTION_VIEW);
										   intent.setData(Uri.parse(grau_url));
										   startActivity(intent);
										 */
										/*
										 * 
										 * 		Bundle bundle = new Bundle();
				bundle.putString(PREF_ID, snippet[1]);
				Intent i = new Intent(mContext,InfoUnitatActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtras(bundle);
				mContext.startActivity(i);
										 */
										Intent i = new Intent(InfoEdificiActivity.this,InfoUnitatActivity.class);

										Bundle bundle = new Bundle();									
										bundle.putString(UpcOverlayItem.PREF_ID, unitat_id);
										//										bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_graus));

										i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										i.putExtras(bundle);
										startActivity(i);

									} catch (Exception e) {
										Log.e("SampleApp", "Failed to invoke call", e);
									}
								}	   
							});
						}

						layout.addView(vi_text);
						listgraus.addView(layout);

						if (i != jsongraus.length()-1) {
							View v = getLayoutInflater().inflate(R.layout.linebreak_taula, null);

							listgraus.addView(v);
						}

					}
				}


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				findViewById(R.id.buildingListGrau).setVisibility(View.GONE);

				findViewById(R.id.buildingUnitatsTitol).setVisibility(View.GONE);
				campsBuits++;

				e.printStackTrace();
			}





			if (campsBuits > 0) {
				findViewById(R.id.unitatEstudis).setVisibility(View.GONE);
			}


		}
		else {
			//error al JSON
			Toast.makeText(this, getResources().getString(R.string.error_webservice), Toast.LENGTH_LONG).show();


			finish();
		}

		// COMPARTIR UBICACION
		btnCompartirUbicacio.setOnClickListener(new OnClickListener() {						
			@Override
			public void onClick(View v) {
				try {

					JSONObject jsoncoord = resultats.getJSONObject(WS_COORD);
					String coord_lat = jsoncoord.getString(WS_COORD_LAT);
					String coord_lon = jsoncoord.getString(WS_COORD_LON);

					//Toast.makeText(InfoUnitatActivity.this, "lat: "+coord_lat+" lon:"+coord_lon, Toast.LENGTH_LONG).show();
					Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
					sharingIntent.setType("text/plain");
					sharingIntent.putExtra(Intent.EXTRA_SUBJECT, my_titol);

					// parametro z = zoom en el mapa
					sharingIntent.putExtra(Intent.EXTRA_TEXT, GOOGLE_MAPS_URL+"q="+coord_lat+","+coord_lon+"&z=17" );
					//https://maps.google.com/maps?ll=44.460938000000006,-93.18519500000001&z=7


					startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.sharelocation)));

				} catch (Exception e) {
					Log.e("SampleApp", "Failed to invoke call", e);
				}
			}	   
		});
		// --

		// COMPARTIR UBICACION
		btnComAnar.setOnClickListener(new OnClickListener() {						
			@Override
			public void onClick(View v) {
				try {
					JSONObject jsoncoord = resultats.getJSONObject(WS_COORD);
					String coord_lat = jsoncoord.getString(WS_COORD_LAT);
					String coord_lon = jsoncoord.getString(WS_COORD_LON);

					//Toast.makeText(InfoUnitatActivity.this, "lat: "+coord_lat+" lon:"+coord_lon, Toast.LENGTH_LONG).show();


					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(GOOGLE_MAPS_URL+"daddr="+coord_lat+","+coord_lon+"&z=17"));
					startActivity(intent);




				} catch (Exception e) {
					Log.e("SampleApp", "Failed to invoke call", e);
				}
			}	   
		});
		// --

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