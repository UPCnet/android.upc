package com.upcnet.upcmobil;

//import android.app.Activity;
//import android.app.Activity;


import java.util.Locale;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IUPCActivity extends MyActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);






		/*LinearLayout layoutEvents = (LinearLayout) findViewById(R.id.layoutimg4);
		layoutEvents.setBackgroundResource(R.drawable.background_menu);
		layoutEvents.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					Intent i = new Intent(IUPCActivity.this,EventsActivityThread.class);

					startActivity(i);
				} catch (Exception e) {

					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();

				}

			}
		});

		 */
		LinearLayout videoLayout = (LinearLayout) findViewById(R.id.layoutimg7);
		videoLayout.setBackgroundResource(R.drawable.background_menu);
		videoLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {

					Intent i = new Intent (Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/user/upc"));
					startActivity (i);
				}
				catch(Exception e) {
					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();
				}


			}
		});

		LinearLayout layoutMaps = (LinearLayout) findViewById(R.id.layoutimg8);
		layoutMaps.setBackgroundResource(R.drawable.background_menu);
		layoutMaps.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (Utils.isNetworkAvailable(getApplicationContext())){



					try {
						Intent i = new Intent(IUPCActivity.this,MapsActivity.class);
						//Intent i = new Intent(IUPCActivity.this,MapsActivity.class);

						startActivity(i);
					} catch (Exception e) {

						AlertDialog ad = alert.create();
						ad.setMessage(e.getMessage());
						ad.show();


					}
				}
				else {

					Toast.makeText(IUPCActivity.this,getResources().getString(R.string.connexion_needed), Toast.LENGTH_LONG).show();
					/* URI: No cal, millor Toast
				 	final AlertDialog ad = alert.create();

					ad.setMessage(getResources().getString(R.string.connexion_needed));
					//ad.setTitle("Prova");
					ad.show();
					//Al cap de 2 segons treiem el missatge
					Handler handler = new Handler(); 
				    handler.postDelayed(new Runnable() { 
				         public void run() { 
				        	 ad.cancel();
				         } 
				    }, 2000);*/

				}

			}
		});
		LinearLayout layoutGraus = (LinearLayout) findViewById(R.id.layoutimg1);
		layoutGraus.setBackgroundResource(R.drawable.background_menu);




		layoutGraus.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (Utils.isNetworkAvailable(getApplicationContext())){
						Intent i = new Intent(IUPCActivity.this,EstudisWebkit.class);

						Bundle bundle = new Bundle();
						bundle.putString(Utils.PREF_URL, Utils.URL_GRAUS);
						bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_graus));


						i.putExtras(bundle);
						startActivity(i);
					}
					else {

						Toast.makeText(IUPCActivity.this,getResources().getString(R.string.connexion_needed), Toast.LENGTH_LONG).show();


					}
				} catch (Exception e) {

					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();


				}


			}
		});

		LinearLayout layoutMasters = (LinearLayout) findViewById(R.id.layoutimg2);
		layoutMasters.setBackgroundResource(R.drawable.background_menu);

		layoutMasters.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (Utils.isNetworkAvailable(getApplicationContext())){

						Intent i = new Intent(IUPCActivity.this,EstudisWebkit.class);

						Bundle bundle = new Bundle();
						bundle.putString(Utils.PREF_URL, Utils.URL_MASTERS);
						bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_masters));


						i.putExtras(bundle);
						startActivity(i);
					}
					else {

						Toast.makeText(IUPCActivity.this,getResources().getString(R.string.connexion_needed), Toast.LENGTH_LONG).show();


					}
				} catch (Exception e) {

					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();


				}


			}
		});


		LinearLayout layoutAtenea = (LinearLayout) findViewById(R.id.layoutimg4);
		layoutAtenea.setBackgroundResource(R.drawable.background_menu);

		layoutAtenea.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (Utils.isNetworkAvailable(getApplicationContext())){

						Intent i = new Intent(IUPCActivity.this,EstudisWebkit.class);

						Bundle bundle = new Bundle();
						bundle.putString(Utils.PREF_URL, Utils.URL_ATENEA);
						bundle.putString(Utils.PREF_TITOL, getString(R.string.menu_atenea));


						i.putExtras(bundle);
						startActivity(i);
					}
					else {

						Toast.makeText(IUPCActivity.this,getResources().getString(R.string.connexion_needed), Toast.LENGTH_LONG).show();


					}
				} catch (Exception e) {

					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();


				}


			}
		});

		LinearLayout layoutFPC = (LinearLayout) findViewById(R.id.layoutimg3);
		layoutFPC.setBackgroundResource(R.drawable.background_menu);

		layoutFPC.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (Utils.isNetworkAvailable(getApplicationContext())){

						Intent i = new Intent(IUPCActivity.this,EstudisWebkit.class);

						Bundle bundle = new Bundle();
						bundle.putString(Utils.PREF_URL, Utils.URL_FPC);
						bundle.putString(Utils.PREF_TITOL, getString(R.string.header_fpc));


						i.putExtras(bundle);
						startActivity(i);
					}
					else {

						Toast.makeText(IUPCActivity.this,getResources().getString(R.string.connexion_needed), Toast.LENGTH_LONG).show();


					}
				} catch (Exception e) {

					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();


				}


			}
		});

		LinearLayout layoutBiblioteca = (LinearLayout) findViewById(R.id.layoutimg5);
		layoutBiblioteca.setBackgroundResource(R.drawable.background_menu);

		layoutBiblioteca.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (Utils.isNetworkAvailable(getApplicationContext())){

						Intent i = new Intent(IUPCActivity.this,EstudisWebkit.class);

						Bundle bundle = new Bundle();
						bundle.putString(Utils.PREF_URL, Utils.URL_BIB);
						bundle.putString(Utils.PREF_TITOL, getString(R.string.header_bib));


						i.putExtras(bundle);
						startActivity(i);
					}
					else {

						Toast.makeText(IUPCActivity.this,getResources().getString(R.string.connexion_needed), Toast.LENGTH_LONG).show();


					}
				} catch (Exception e) {

					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();


				}


			}
		});
		
		LinearLayout layoutUPComons = (LinearLayout) findViewById(R.id.layoutimg6);
		layoutUPComons.setBackgroundResource(R.drawable.background_menu);

		layoutUPComons.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (Utils.isNetworkAvailable(getApplicationContext())){

						Intent i = new Intent(IUPCActivity.this,EstudisWebkit.class);

						Bundle bundle = new Bundle();
						bundle.putString(Utils.PREF_URL, Utils.URL_UPCOMMONS);
						bundle.putString(Utils.PREF_TITOL, getString(R.string.header_upcommons));


						i.putExtras(bundle);
						startActivity(i);
					}
					else {

						Toast.makeText(IUPCActivity.this,getResources().getString(R.string.connexion_needed), Toast.LENGTH_LONG).show();


					}
				} catch (Exception e) {

					AlertDialog ad = alert.create();
					ad.setMessage(e.getMessage());
					ad.show();


				}


			}
		});

	}

	// Inflamos el menu About del Main
	/*public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.mainaboutoption,menu);
//			menu.findItem(R.id.main_menu_report).setIntent(new Intent(this,AboutActivity.class));
			menu.findItem(R.id.main_menu_about).setIntent(new Intent(this,AboutActivity.class));
			return true;
		}


		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
	        	super.onOptionsItemSelected(item);
				startActivity(item.getIntent());
			return true;
		}*/


}