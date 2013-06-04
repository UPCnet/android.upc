package com.upcnet.upcmobil;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoUnitatActivity extends MyActivity {


	//static final String URL = "http://cercador.upc.edu/search?site=maps_units&client=default_frontend&output=xml_no_dtd&getfields=id_edifici&num=100&filter=0&q=";
	static final String WS_SEARCH ="http://nucli.upc.edu/ws/InfoUnitatv1.php?id=";
	public static final String WS_NOM_CAMPUS = "campus_ca";
	public static final String WS_NOM = "nom_ca";
	public static final String WS_SIGLES = "sigles";

	public static final String WS_DIRECCIO ="adreca_sencera";
	public static final String WS_CP = "codi_postal";
	public static final String WS_LOCALITAT = "localitat";
	public static final String WS_COORD = "coord";
	public static final String WS_COORD_LAT = "lat";
	public static final String WS_COORD_LON = "lon";
	public static final String WS_UNITATS = "unitats";
	public static final String WS_TELEFON = "telefon";
	public static final String WS_IMAGES = "fotos";
	public static final String WS_IMAGES_VIDEO = "video";
	public static final String WS_IMAGES_NORMAL = "normal";
	public static final String WS_ESTUDIS = "estudis";
	public static final String WS_ESTUDIS_GRAUS = "graus";
	public static final String WS_ESTUDIS_NOM = "nom_ca";
	public static final String WS_ESTUDIS_ID = "id";
	public static final String WS_ESTUDIS_MASTERS = "masters";
	public static final String WS_ESTUDIS_CICLES = "2ns_cicles";

	public static final String WS_ESTUDIS_DOBLES = "dobles_titulacions";
	public static final String WS_VIDEO_YOUTUBE = "video_youtube";
	public static final String GOOGLE_MAPS_URL = "http://maps.google.com/maps?";
	public static final String FITXA_GRAU_URL = "http://www.upc.edu/grau/fitxa_grau.php?lang=ca&id_estudi=";
	public static final String IDIOMA_FITXA_URL = "&lang=cat#content";
	public static final String FITXA_MASTER_URL = "http://www.upc.edu/master/fitxa_master.php?lang=ca&id_estudi=";
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
	LinearLayout btnAfegirContacte;
	LinearLayout btnCompartirUbicacio;
	LinearLayout btnComAnar;

	JSONObject resultats;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_info_unitat);
		//Toast.makeText(this, "test",Toast.LENGTH_SHORT).show();


		titol = (TextView) findViewById(R.id.edificiTitol);
		info1 = (TextView) findViewById(R.id.edificiInfo1);
		//info2 = (TextView) findViewById(R.id.edificiInfo2);
		//info3 = (TextView) findViewById(R.id.edificiInfo3);
		infoTelefon = (TextView) findViewById(R.id.edificiTelefon);
		infoMail = (TextView) findViewById(R.id.edificiMail);
		infoWWW = (TextView) findViewById(R.id.edificiURL);
		btnAfegirContacte = (LinearLayout) findViewById(R.id.unitatBotons1);
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


				if (resultats.getString(WS_NOM) != null  && !resultats.getString(WS_NOM).equals("")) {
					my_titol = resultats.getString(WS_NOM);
				}
				if (resultats.getString(WS_SIGLES) != null  && !resultats.getString(WS_SIGLES).equals("")) {
					my_titol = my_titol + " (" + resultats.getString(WS_SIGLES) + ")";
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

			// IMATGE
			try{
				String urlImage = "";
				if (resultats.getJSONObject(WS_IMAGES).getString(WS_IMAGES_VIDEO) != null  && !resultats.getJSONObject(WS_IMAGES).getString(WS_IMAGES_VIDEO).equals("")) {
					urlImage=resultats.getJSONObject(WS_IMAGES).getString(WS_IMAGES_VIDEO);					
				}
				else {
					if (resultats.getJSONObject(WS_IMAGES).getString(WS_IMAGES_NORMAL) != null  && !resultats.getJSONObject(WS_IMAGES).getString(WS_IMAGES_NORMAL).equals("")) {
						urlImage=resultats.getJSONObject(WS_IMAGES).getString(WS_IMAGES_NORMAL);
					}
					else {

						findViewById(R.id.edificiImage).setVisibility(View.GONE);
			
					}
				}
				if (findViewById(R.id.edificiImage).getVisibility() != View.GONE) {
					if (resultats.getString(WS_VIDEO_YOUTUBE) != null  && !resultats.getString(WS_VIDEO_YOUTUBE).equals("")) {
						findViewById(R.id.edificiImage).setOnClickListener(new OnClickListener() {			
							@Override
							public void onClick(View v) {
								try {
									// NO se puede hacer intent a youtube directamente porque la URL esta "shortened"
									//Toast.makeText(this, resultats.getString(WS_MAIL), Toast.LENGTH_LONG).show();
									/* Intent intent = new Intent(Intent.ACTION_VIEW, 
					                               Uri.parse("vnd.youtube:" 
					                             + resultats.getString(WS_VIDEO_YOUTUBE) ));
									    startActivity(intent);*/
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setData(Uri.parse(resultats.getString(WS_VIDEO_YOUTUBE)));
									startActivity(intent);

								} catch (Exception e) {
									Log.e("SampleApp", "Failed to invoke call", e);
								}
							}	   
						});
					}
				}
				if (!urlImage.equals("")) {
					//Toast.makeText(this, urlImage, Toast.LENGTH_SHORT).show();

					new DownloadImage().execute(urlImage);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				findViewById(R.id.edificiImage).setVisibility(View.GONE);

				e.printStackTrace();
			}
			//Toast.makeText(this, urlImage, Toast.LENGTH_SHORT).show();


			int campsBuits =0;
			//ENDIMAGE
			try {

				if (resultats.getString(WS_TELEFON) != null  && !resultats.getString(WS_TELEFON).equals("")) {
					infoTelefon.setText(resultats.getString(WS_TELEFON));
					infoTelefon.setOnClickListener(new OnClickListener() {						
						@Override
						public void onClick(View v) {
							try {
								Intent intent = new Intent(Intent.ACTION_DIAL);
								intent.setData(Uri.parse("tel:"+resultats.getString(WS_TELEFON)));
								startActivity(intent);
							} catch (Exception e) {
								Log.e("SampleApp", "Failed to invoke call", e);
							}
						}	   
					});


				}
				else {
					infoTelefon.setVisibility(View.GONE);
					findViewById(R.id.edificiTelfMailSeparator).setVisibility(View.GONE);
					campsBuits++;
				}



				if (resultats.getString(WS_MAIL) != null  && !resultats.getString(WS_MAIL).equals("")) {
					infoMail.setText(resultats.getString(WS_MAIL));
					infoMail.setOnClickListener(new OnClickListener() {						
						@Override
						public void onClick(View v) {
							try {
								Intent intent = new Intent(Intent.ACTION_SEND);
								intent.setType("plain/text");
								intent.putExtra(Intent.EXTRA_EMAIL, new String[] { resultats.getString(WS_MAIL) });
								startActivity(intent);
							} catch (Exception e) {
								Log.e("SampleApp", "Failed to invoke call", e);
							}
						}	   
					});
				}
				else {
					infoMail.setVisibility(View.GONE);
					findViewById(R.id.edificiMailURLSeparator).setVisibility(View.GONE);
					campsBuits++;

				}
				// ADD CONTACT BUTTON VISIBILITY
				if ((infoTelefon.getVisibility() == View.GONE) && (infoMail.getVisibility() == View.GONE)) {
					btnAfegirContacte.setVisibility(View.GONE);
				}
				else {
					btnAfegirContacte.setOnClickListener(new OnClickListener() {						
						@Override
						public void onClick(View v) {
							try {
								Intent addContactIntent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
								addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME,resultats.getString(WS_NOM));
								addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL,resultats.getString(WS_MAIL));
								addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE,resultats.getString(WS_TELEFON));
								startActivity(addContactIntent);

							} catch (Exception e) {
								Log.e("SampleApp", "Failed to invoke call", e);
							}
						}	   
					});


				}				
				// --

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

				if (resultats.getString(WS_WEB) != null  && !resultats.getString(WS_WEB).equals("") && !resultats.getString(WS_WEB).equals("http://")) {
					infoWWW.setText(resultats.getString(WS_WEB));
					infoWWW.setOnClickListener(new OnClickListener() {						
						@Override
						public void onClick(View v) {
							try {
								Intent intent = new Intent(Intent.ACTION_VIEW);
								intent.setData(Uri.parse(resultats.getString(WS_WEB)));
								startActivity(intent);
							} catch (Exception e) {
								Log.e("SampleApp", "Failed to invoke call", e);
							}
						}	   
					});

				}
				else {
					infoWWW.setVisibility(View.GONE);
					campsBuits++;

				}

				if (campsBuits > 2) {
					findViewById(R.id.unitatContacte).setVisibility(View.GONE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			campsBuits = 0;

			try {
				JSONArray jsongraus = resultats.getJSONObject(WS_ESTUDIS).getJSONArray(WS_ESTUDIS_GRAUS);

				LinearLayout listgraus = (LinearLayout) findViewById(R.id.edificiListGrau);

				if (jsongraus == null || jsongraus.length() == 0) {
					listgraus.setVisibility(View.GONE);
					findViewById(R.id.edificiGrauTitol).setVisibility(View.GONE);
					campsBuits++;

				}
				else {

					for (int i = 0; i < jsongraus.length(); i++) {
						// View vi =  getLayoutInflater().inflate(R.layout.edifici_fitxa_single, null);
						LinearLayout layout = new LinearLayout(this);
						layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,             LayoutParams.WRAP_CONTENT));
						TextView vi_text = new TextView(this);
						vi_text.setText(jsongraus.getJSONObject(i).getString(WS_ESTUDIS_NOM));
						vi_text.setTextColor(Color.parseColor("#007BC0"));
						LayoutParams layoutP = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
						((MarginLayoutParams) layoutP).setMargins(7,5,2,2);
						vi_text.setLayoutParams(layoutP);

						final String grau_url = FITXA_GRAU_URL+jsongraus.getJSONObject(i).getString(WS_ESTUDIS_ID)+IDIOMA_FITXA_URL;
						vi_text.setOnClickListener(new OnClickListener() {						
							@Override
							public void onClick(View v) {
								try {
									/*
									   Intent intent = new Intent(Intent.ACTION_VIEW);
									   intent.setData(Uri.parse(grau_url));
									   startActivity(intent);
									 */
									Intent i = new Intent(InfoUnitatActivity.this,EstudisWebkit.class);

									Bundle bundle = new Bundle();
									bundle.putString(Utils.PREF_URL, grau_url);
									bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_graus));


									i.putExtras(bundle);
									startActivity(i);

								} catch (Exception e) {
									Log.e("SampleApp", "Failed to invoke call", e);
								}
							}	   
						});

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
				findViewById(R.id.edificiListGrau).setVisibility(View.GONE);

				findViewById(R.id.edificiGrauTitol).setVisibility(View.GONE);
				campsBuits++;

				e.printStackTrace();
			}

			try {
				JSONArray jsongraus = resultats.getJSONObject(WS_ESTUDIS).getJSONArray(WS_ESTUDIS_CICLES);

				LinearLayout listgraus = (LinearLayout) findViewById(R.id.edificiList2nCicle);

				if (jsongraus == null || jsongraus.length() == 0) {
					listgraus.setVisibility(View.GONE);
					findViewById(R.id.edifici2nCicleTitol).setVisibility(View.GONE);
					campsBuits++;

				}
				else {

					for (int i = 0; i < jsongraus.length(); i++) {
						// View vi =  getLayoutInflater().inflate(R.layout.edifici_fitxa_single, null);
						LinearLayout layout = new LinearLayout(this);
						layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,             LayoutParams.WRAP_CONTENT));
						TextView vi_text = new TextView(this);
						vi_text.setText(jsongraus.getJSONObject(i).getString(WS_ESTUDIS_NOM));
						vi_text.setTextColor(Color.parseColor("#007BC0"));
						LayoutParams layoutP = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
						((MarginLayoutParams) layoutP).setMargins(7,5,2,2);
						vi_text.setLayoutParams(layoutP);

						final String grau_url = FITXA_GRAU_URL+jsongraus.getJSONObject(i).getString(WS_ESTUDIS_ID)+IDIOMA_FITXA_URL;
						vi_text.setOnClickListener(new OnClickListener() {						
							@Override
							public void onClick(View v) {
								try {
									/*
									   Intent intent = new Intent(Intent.ACTION_VIEW);
									   intent.setData(Uri.parse(grau_url));
									   startActivity(intent);
									 */
									Intent i = new Intent(InfoUnitatActivity.this,EstudisWebkit.class);

									Bundle bundle = new Bundle();
									bundle.putString(Utils.PREF_URL, grau_url);
									bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_graus));


									i.putExtras(bundle);
									startActivity(i);

								} catch (Exception e) {
									Log.e("SampleApp", "Failed to invoke call", e);
								}
							}	   
						});

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
				findViewById(R.id.edificiList2nCicle).setVisibility(View.GONE);

				findViewById(R.id.edifici2nCicleTitol).setVisibility(View.GONE);
				campsBuits++;

				e.printStackTrace();
			}

			try {
				JSONArray jsongraus = resultats.getJSONObject(WS_ESTUDIS).getJSONArray(WS_ESTUDIS_MASTERS);

				LinearLayout listgraus = (LinearLayout) findViewById(R.id.edificiListMasters);


				if (jsongraus == null || jsongraus.length() == 0) {
					listgraus.setVisibility(View.GONE);
					findViewById(R.id.edificiMastersTitol).setVisibility(View.GONE);
					campsBuits++;

				}
				else {

					for (int i = 0; i < jsongraus.length(); i++) {
						// View vi =  getLayoutInflater().inflate(R.layout.edifici_fitxa_single, null);
						LinearLayout layout = new LinearLayout(this);
						layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,             LayoutParams.WRAP_CONTENT));
						TextView vi_text = new TextView(this);
						vi_text.setText(jsongraus.getJSONObject(i).getString(WS_ESTUDIS_NOM));
						vi_text.setTextColor(Color.parseColor("#007BC0"));
						LayoutParams layoutP = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
						((MarginLayoutParams) layoutP).setMargins(7,5,2,2);
						vi_text.setLayoutParams(layoutP);

						final String master_url = FITXA_MASTER_URL+jsongraus.getJSONObject(i).getString(WS_ESTUDIS_ID)+IDIOMA_FITXA_URL;
						vi_text.setOnClickListener(new OnClickListener() {						
							@Override
							public void onClick(View v) {
								try {
									/*
									   Intent intent = new Intent(Intent.ACTION_VIEW);
									   intent.setData(Uri.parse(master_url));
									   startActivity(intent);
									 */
									Intent i = new Intent(InfoUnitatActivity.this,EstudisWebkit.class);

									Bundle bundle = new Bundle();
									bundle.putString(Utils.PREF_URL, master_url);
									bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_masters));


									i.putExtras(bundle);
									startActivity(i);

								} catch (Exception e) {
									Log.e("SampleApp", "Failed to invoke call", e);
								}
							}	   
						});


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
				findViewById(R.id.edificiListMasters).setVisibility(View.GONE);

				findViewById(R.id.edificiMastersTitol).setVisibility(View.GONE);
				campsBuits++;

				e.printStackTrace();


			}
			try {
				JSONArray jsongraus = resultats.getJSONObject(WS_ESTUDIS).getJSONArray(WS_ESTUDIS_DOBLES);

				LinearLayout listgraus = (LinearLayout) findViewById(R.id.edificiListDobles);
				if (jsongraus == null || jsongraus.length() == 0) {
					listgraus.setVisibility(View.GONE);
					findViewById(R.id.edifici2nCicleTitol).setVisibility(View.GONE);
					campsBuits++;


				}
				else {

					for (int i = 0; i < jsongraus.length(); i++) {
						// View vi =  getLayoutInflater().inflate(R.layout.edifici_fitxa_single, null);
						LinearLayout layout = new LinearLayout(this);
						layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,             LayoutParams.WRAP_CONTENT));
						TextView vi_text = new TextView(this);
						vi_text.setText(jsongraus.getJSONObject(i).getString(WS_ESTUDIS_NOM));
						vi_text.setTextColor(Color.parseColor("#007BC0"));
						LayoutParams layoutP = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
						((MarginLayoutParams) layoutP).setMargins(7,5,2,2);
						vi_text.setLayoutParams(layoutP);

						final String grau_url = FITXA_GRAU_URL+jsongraus.getJSONObject(i).getString(WS_ESTUDIS_ID)+IDIOMA_FITXA_URL;
						vi_text.setOnClickListener(new OnClickListener() {						
							@Override
							public void onClick(View v) {
								try {
									/*
									   Intent intent = new Intent(Intent.ACTION_VIEW);
									   intent.setData(Uri.parse(grau_url));
									   startActivity(intent);
									 */
									Intent i = new Intent(InfoUnitatActivity.this,EstudisWebkit.class);

									Bundle bundle = new Bundle();
									bundle.putString(Utils.PREF_URL, grau_url);
									bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_graus));


									i.putExtras(bundle);
									startActivity(i);

								} catch (Exception e) {
									Log.e("SampleApp", "Failed to invoke call", e);
								}
							}	   
						});


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
				findViewById(R.id.edificiListDobles).setVisibility(View.GONE);
				findViewById(R.id.edificiDoblesTitol).setVisibility(View.GONE);
				campsBuits++;

				e.printStackTrace();
			}


			if (campsBuits > 3) {
				findViewById(R.id.unitatEstudis).setVisibility(View.GONE);
			}


		}
		else {
			//error al JSON
			Toast.makeText(this, getResources().getString(R.string.error_webservice), Toast.LENGTH_LONG).show();


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
	private class DownloadImage extends AsyncTask<String,Integer,Long> {


		Bitmap bimage;
		@Override
		protected Long doInBackground(String... params) {
			// TODO Auto-generated method stub


			bimage=  getBitmapFromURL(params[0]);
			return null;
		}


		protected void onPostExecute(Long result) {


			ImageView imgLogo = (ImageView) findViewById(R.id.edificiImage);
			imgLogo.setImageBitmap(bimage);
			imgLogo.setVisibility(View.VISIBLE);


		}
		private Bitmap getBitmapFromURL(String src) {
			try {

				URL url = new URL(src);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);

				return myBitmap;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}


}