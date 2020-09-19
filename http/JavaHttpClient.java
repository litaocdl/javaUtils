package http;


import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.apache.http.conn.ssl.NoopHostnameVerifier;


public class JavaHttpClient {

	public static void testHttpGet() throws MalformedURLException, NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		// No verify certificate
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		// No verify hostname
		HttpsURLConnection.setDefaultHostnameVerifier(new NoopHostnameVerifier() {
		});

		HttpsURLConnection conn = null ;	
		URL url = new URL("https://xxxxx");
		
		try {
			conn = (HttpsURLConnection)url.openConnection() ;

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("username", "admin");
			conn.setRequestProperty("password","password");
			
			conn.getInputStream();
			
			System.out.println("Success");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
