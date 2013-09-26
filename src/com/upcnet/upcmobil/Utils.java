package com.upcnet.upcmobil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	public static String URL_BIB = "http://m.bibliotecnica.upc.edu/home/index.php?app=true";
	public static String URL_UPCOMMONS = "http://m.bibliotecnica.upc.edu/upcommons/index.php?app=true";

	public static String PREF_URL = "url_inicial";
	public static String URL_GRAUS = "http://www.upc.edu/grau/graus.php?lang=ca";
	public static String URL_MASTERS = "http://www.upc.edu/master/masters.php?lang=ca";
	
	
	public static String URL_ATENEA = "http://m.atenea.upc.edu?app=true&lang=ca";
	public static String URL_FPC = "http://m.formaciocontinua.upc.edu/cat";

	public static String PREF_TITOL = "webkit_window_title";

	
	//private static final String NETWORK = "Networking";

	static public boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		
		return (activeNetworkInfo != null) && (activeNetworkInfo.isConnected());
		
		/* NO VA, AIXÒ BLOQUEJA!!!
		 * if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())){
			try {
				if(InetAddress.getByName("alderamin1.upc.es").isReachable(10000)) {
					return true;
				}
			} catch (UnknownHostException e) {
				Log.e(NETWORK,e.getMessage());
			} catch (IOException e) {
				Log.e(NETWORK,e.getMessage());
			}
		}
		
		return false;*/  
	}
	
	
	public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
            .openConnection();
            connection.setDoInput(true);
            connection.connect();
            connection.setReadTimeout(120000);
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}

/* prova update comentari */
