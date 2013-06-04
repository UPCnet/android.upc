package com.upcnet.upcmobil;

// URI: Mirar https://github.com/jgilfelt/android-mapviewballoons
import java.util.ArrayList;

import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;
import com.readystatesoftware.mapviewballoons.BalloonOverlayView;
//import com.readystatesoftware.mapviewballoons.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;


public class UpcOverlayItem extends  BalloonItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private static String TIPUS_EDIFICI = "edifici";
	private static String TIPUS_UNITAT = "unitat";
	public static String PREF_ID = "map_id_tap";
	
	public UpcOverlayItem(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
	}
	public UpcOverlayItem(Drawable defaultMarker,MapView mapView, Context context) {
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
		//Toast.makeText(mContext	, "onBalloonTap for overlay index " + item.getSnippet(),
		//		Toast.LENGTH_LONG).show();
		String[] snippet = item.getSnippet().split(" ");
		//Toast.makeText(mContext	, "Snippet 0 :" + snippet[0] +" Snippet 1 :" +snippet[1],
		//		Toast.LENGTH_LONG).show();
		
		if (snippet[0].equals(TIPUS_EDIFICI)) {
			Bundle bundle = new Bundle();
			bundle.putString(PREF_ID, snippet[1]);
			Intent i = new Intent(mContext,InfoEdificiActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtras(bundle);
			mContext.startActivity(i);
		} else if (snippet[0].equals(TIPUS_UNITAT)) {
			Bundle bundle = new Bundle();
			bundle.putString(PREF_ID, snippet[1]);
			Intent i = new Intent(mContext,InfoUnitatActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtras(bundle);
			mContext.startActivity(i);
		} else {
			Toast.makeText(mContext	, mContext.getResources().getString(R.string.error_npi),Toast.LENGTH_LONG).show();

		}
		
		
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
