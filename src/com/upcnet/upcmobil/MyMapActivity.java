package com.upcnet.upcmobil;



import java.util.Locale;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.maps.MapActivity;

public class MyMapActivity extends MapActivity {

	// Inflamos el menu About del Main
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mainaboutoption,menu);
		//			menu.findItem(R.id.main_menu_report).setIntent(new Intent(this,AboutActivity.class));
		menu.findItem(R.id.main_menu_about).setIntent(new Intent(this,AboutActivity.class));
		menu.findItem(R.id.main_menu_home).setIntent(new Intent(this,IUPCActivity.class));

		Locale.setDefault(new Locale("ca_ES"));

		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		startActivity(item.getIntent());
		return true;
	}


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onStart() {
		super.onStart();
		//Analytics
		EasyTracker.getInstance(this).activityStart(this); 

	}
	@Override
	public void onStop() {
		super.onStop();
		//Analytics
		EasyTracker.getInstance(this).activityStop(this);  
	}
}
