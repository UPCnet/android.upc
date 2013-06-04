package com.upcnet.upcmobil;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;


public class BuildingUpcOverlayItem extends com.google.android.maps.ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;


	public BuildingUpcOverlayItem(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
	}
	public BuildingUpcOverlayItem(Drawable defaultMarker, Context context) {
		  super(defaultMarker);
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
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  /*AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();*/
		Toast.makeText(mContext, "Edifici "+ item.getTitle(), Toast.LENGTH_SHORT).show();

		Log.e("CLICK", "Click a " + item.getTitle() + item.getSnippet());

	  return true;
	}
	@Override
	public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false);
	}

}
