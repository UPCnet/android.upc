package com.upcnet.upcmobil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
//import android.app.DownloadManager;



public class EstudisWebkit extends MyActivity {

	private static String MIME_DOWNLOAD = "application/x-forcedownload";
	private static String MIME_PDF = "application/pdf";
	
	WebView myWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estudis_webkit);

		Bundle extras = getIntent().getExtras();


		if (extras != null) {
			String url = extras.getString(Utils.PREF_URL);
			TextView header = (TextView) findViewById(R.id.textWebkitHeader);

			header.setText(extras.getString(Utils.PREF_TITOL));
			//header.setShadowLayer(5, 0, 0, 40000000);
			myWebView = (WebView) findViewById(R.id.estudisWebView);
			myWebView.getSettings().setJavaScriptEnabled(true);
			
			myWebView.setDownloadListener(new DownloadListener() {
		        public void onDownloadStart(String url, String userAgent,
		                String contentDisposition, String mimetype,
		                long contentLength) {
		        	
		        	//DEBUG
		        	//Toast.makeText(EstudisWebkit.this, "Baixar un fitxer: "+url +"Type: "+ mimetype, Toast.LENGTH_SHORT).show();
		        	
		        	
		        	//Prova 1
		        	// Resultat: Obre el fitxer al navegador: Desc(ok) Fin(Ok) Integrat(Ko) || Fora atenea (Ok)
		        	Intent i = new Intent(Intent.ACTION_VIEW);
		            i.setData(Uri.parse(url));
		            startActivity(i);
		        	
		        	
		        	
		     /*   	if ( mimetype.equals(MIME_DOWNLOAD) || mimetype.equals(MIME_PDF)) {
		        		
		        		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		        		request.setDescription("Some descrition");
	                     request.setTitle("Some title");
	                     // in order for this if to run, you must use the android 3.2 to compile your app
	                     request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "test.html");
	                     
	                     // get download service and enqueue file
	                     DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
	                     manager.enqueue(request);
		        		
		        	}*/
		          //Intent i = new Intent(Intent.ACTION_VIEW);
		          //i.setData(Uri.parse(url));
		          //startActivity(i);
		        	 //DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                     //request.setDescription("Some descrition");
                     //request.setTitle("Some title");
                     // in order for this if to run, you must use the android 3.2 to compile your app
                     //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, null);
                     
                     // get download service and enqueue file
                     //DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                     //manager.enqueue(request);
		        }
		    });
			myWebView.setWebViewClient(new WebViewClient() {
				 public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
				 handler.proceed();
				 }
				});

			myWebView.loadUrl(url);

			//myWebView.setWebViewClient(new HelloWebViewClient());

		}
		else {

			Toast.makeText(this, getResources().getString(R.string.error_webservice_network), Toast.LENGTH_LONG).show();


			finish();
		}
		
		

	}

	@Override
	public void onStart() {
		super.onStart();


	}
	@Override
	public void onResume(){
		super.onResume();


	}

	@Override
	public void onStop() {
		super.onStop();


	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
			myWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			Toast.makeText(EstudisWebkit.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		}
	}




}
