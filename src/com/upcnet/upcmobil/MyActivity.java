package com.upcnet.upcmobil;



import java.util.Locale;
import com.google.analytics.tracking.android.EasyTracker;


import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MyActivity extends Activity {

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
