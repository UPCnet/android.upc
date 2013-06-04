package com.upcnet.upcmobil;

// URI: Mirar https://github.com/jgilfelt/android-mapviewballoons
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;


public class HomeUpcOverlayItem extends  BalloonItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public static String PREF_ID = "map_id_tap";
	
	public HomeUpcOverlayItem(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
	}

	public HomeUpcOverlayItem(Drawable defaultMarker,MapView mapView, Context context) {
		  super(defaultMarker, mapView);
		  mContext = context;
		}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
	}


	@Override
	public int size() {
	  return mOverlays.size();
	}
	/*
	@Override
	protected boolean onBalloonTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  Log.e("CLICK", "Click a " + item.getTitle() + item.getSnippet());
		Bundle bundle = new Bundle();
		bundle.putString("id_campus", item.getSnippet());
		Intent i = new Intent(mContext,MapsCampusActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.putExtras(bundle);
		mContext.startActivity(i);

	  return true;
	}*/
	
	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {

		
			Bundle bundle = new Bundle();
			bundle.putString(MapsActivity.CAMPUS_PREF, item.getTitle());
			Intent i = new Intent(mContext,InfoCampusActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtras(bundle);
			mContext.startActivity(i);
		
		
		
		return true;
	}
	/*@Override
	public boolean onTap(GeoPoint p, MapView mapView) {

		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LayoutParams lp = new MapView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, p, LayoutParams.WRAP_CONTENT);
		LinearLayout view = (LinearLayout)inflater.inflate(R.layout.map_overlay, null);

		mapView.removeView(view);    
		mapView.invalidate();    
		mapView.addView(view,lp);

		mapView.invalidate();

		return true;
		}
	*/
	@Override
	public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false);
	}

}
