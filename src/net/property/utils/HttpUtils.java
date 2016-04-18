package net.property.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	private static final HttpClient client;
	static {    	
    	PoolingClientConnectionManager connManager = new PoolingClientConnectionManager();
    	connManager.setDefaultMaxPerRoute(20); // Allow upto 20 connections for solr server
    	connManager.setMaxTotal(1000);
    	client = new DefaultHttpClient(connManager);
    	HttpParams params = client.getParams();
    	HttpConnectionParams.setSoTimeout(params, 30000);
    	HttpConnectionParams.setConnectionTimeout(params, 5000);
	}
	
	private enum Reason {CONNECTION_TIMEOUT, SOCKET_TIMEOUT, NONE};
	
	public static String getHTMLcontent(String URL)  {
		return getHTMLcontent(URL, null, 0);
	}
	
	public static String getHTMLcontent(String URL, String encodingDef, int retry)  {
		String html = null;
		InputStream ios = null;
		int trial = 0;
		boolean captured = false;
		Reason reason = Reason.NONE;
		do {
			trial++;
			HttpGet method = null;
			try {
				method = new HttpGet(new URI(URL));
				HttpResponse response = client.execute(method);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == HttpStatus.SC_OK) {
					if(encodingDef!=null && !encodingDef.isEmpty()) {
						html = EntityUtils.toString(response.getEntity(), encodingDef);
					} else {
						html = EntityUtils.toString(response.getEntity());
					}
					captured = true;
				}
			} catch (ConnectTimeoutException e) {
				reason = Reason.CONNECTION_TIMEOUT;
			} catch (SocketTimeoutException e) {
				reason = Reason.SOCKET_TIMEOUT;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(ios!=null) {
					try {
						ios.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(method!=null) {
					method.releaseConnection();
				}
			}
		} while(!captured && reason != Reason.NONE && trial<retry+1);
		
		try {
			Thread.sleep(GlobalConstants.CRAWLING_INTERVAL);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// System.out.println();
		return html;
	}
	
	public static HttpClient getHttpClient() {
		return client;
	}
}
