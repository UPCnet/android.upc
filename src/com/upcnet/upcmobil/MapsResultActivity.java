package com.upcnet.upcmobil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//LINK per la llista + checkbox:http://appfulcrum.com/2010/09/12/listview-example-3-simple-multiple-selection-checkboxes/



public class MapsResultActivity extends MyMapActivity {

	MapView mapView; 
	MapController mc;
	GeoPoint p;
	TextView loadText;
	ProgressBar updateStatus;
	private EditText searchBox;


	private static final int INVISIBLE = View.INVISIBLE;
	public static final String SEARCH_PREF = "SearchMap";
	private static final int DIF_LAT = 100;
	private static final int DIF_LON = 0;

	private static final int VISIBLE = View.VISIBLE;
	//private static final String WS_MAPS_URL = "http://alderamin1.upc.es/campusWebService.php";

	
	private ArrayList<OverlayItem> pointList;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_root_search);
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		pointList= new ArrayList<OverlayItem>();
		updateStatus = (ProgressBar) findViewById(R.id.map_loading);
		updateStatus.setVisibility(View.INVISIBLE);
		Bundle extras = getIntent().getExtras();
		searchBox = (EditText) findViewById(R.id.searchET);

		TextView.OnEditorActionListener autoSearchSubmit = new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView editSearch, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_SEARCH) {
					submit_search();
					//Toast.makeText(MapsActivity.this,"Submit", Toast.LENGTH_LONG).show();
				}
				return true;
			}
		};
		searchBox.setOnEditorActionListener(autoSearchSubmit);

		findViewById(R.id.imageSearch1).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				try {
					submit_search();

				} catch (Exception e) {
					Log.e("SampleApp", "Failed to invoke call", e);
				}
			}	   
		});
		

		if (extras != null) {
			Integer numPoI = extras.getInt(SearchMaps.NUM_POINTS);
		
			for (int i=0; i < numPoI; i++) {
				extras.getDouble(SearchMaps.LAT_POINT + i);
				extras.getDouble(SearchMaps.LON_POINT + i);
				extras.getString(SearchMaps.NAME_POINT + i);
				extras.getString(SearchMaps.ID_POINT + i);
				extras.getString(SearchMaps.TIPUS_POINT + i);
				int my_lat = (int) (extras.getDouble(SearchMaps.LAT_POINT + i) * 1E6);
				int my_lon = (int) (extras.getDouble(SearchMaps.LON_POINT + i) * 1E6);
				
				
				// Inici xapusseta pels punts iguals
				for (int k =0; k < pointList.size(); k++) {
					if (pointList.get(k).getPoint().getLatitudeE6() == my_lat && pointList.get(k).getPoint().getLongitudeE6() == my_lon ) {
						my_lat += DIF_LAT;
						my_lon += DIF_LON;
					}
				}
				// Final xapusseta pels punts iguals
				GeoPoint myP = new GeoPoint(my_lat,my_lon);
				pointList.add(new OverlayItem(myP,extras.getString(SearchMaps.NAME_POINT + i), extras.getString(SearchMaps.TIPUS_POINT + i) +" "+  extras.getString(SearchMaps.ID_POINT + i)));
				//Toast.makeText(this, extras.getString(SearchMaps.NAME_POINT + i), Toast.LENGTH_SHORT);
				Log.v("TEST", "Recollim punt "+ SearchMaps.NAME_POINT + i + ":" +extras.getString(SearchMaps.NAME_POINT + i));
			}
		
			
			
			List<Overlay> listOfOverlays = mapView.getOverlays();
			listOfOverlays.clear();
			Drawable drawable = MapsResultActivity.this.getResources().getDrawable(R.drawable.maps_piu);
			drawable.setBounds(-24, -48, 24 , +0); //Centrem abaix al mig
			UpcOverlayItem itemizedoverlay = new UpcOverlayItem (drawable, mapView, getApplicationContext());

			int minLat=Integer.MAX_VALUE;
			int maxLat = Integer.MIN_VALUE;
			int minLon= Integer.MAX_VALUE;
			int maxLon= Integer.MIN_VALUE;
			
			
			for (int i=0;i<pointList.size();i++) {
				itemizedoverlay.addOverlay(pointList.get(i));
				listOfOverlays.add(itemizedoverlay);    
				
				maxLat=Math.max(pointList.get(i).getPoint().getLatitudeE6(), maxLat);
				minLat=Math.min(pointList.get(i).getPoint().getLatitudeE6(), minLat);
				maxLon=Math.max(pointList.get(i).getPoint().getLongitudeE6(), maxLon);
				minLon=Math.min(pointList.get(i).getPoint().getLongitudeE6(), minLon);
				
			}
			mc = mapView.getController();
			double fitFactor = 1.5;
			int spanx = (int) (Math.abs(maxLat - minLat) * fitFactor);
			int spany = (int)(Math.abs(maxLon - minLon) * fitFactor);
			
			if(spanx < 2000){
				spanx=2000;
			}
			if(spany < 2000){
				spany=2000;
			}
			mc.zoomToSpan(spanx,spany);
			
			mc.animateTo(new GeoPoint( (maxLat + minLat)/2, 
					(maxLon + minLon)/2 )); 
			
	
			mapView.postInvalidate();
		}

		//new PrintNewPoints().execute(WS_MAPS_URL);

		//printPoints();



	}

private void submit_search() {
		
		Intent i = new Intent(this,SearchMaps.class);
		Bundle bundle = new Bundle();

		bundle.putString(SEARCH_PREF, searchBox.getText().toString());
		i.putExtras(bundle);

		startActivity(i);
	}


	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}



}