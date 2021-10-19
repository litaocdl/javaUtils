package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.*;
import org.apache.http.impl.client.HttpClients;


public class ApacheHttpClient {

    public static void main(String[] args) throws Exception{
        testHttpsGet() ;
    }

    public static void testHttpsGet() throws Exception{
		SSLContextBuilder builder = new SSLContextBuilder();
	
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				builder.build(),
				new String[]{"TLSv1.2"},  //Support starting from TLSv1, can be configurable
				null,
				new NoopHostnameVerifier());
        // Allow TLSv1 protocol only
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();


       
        HttpGet httpget = new HttpGet("https://xxxxx");
        
       
        httpget.addHeader("Accept", "application/json");
        httpget.addHeader("Content-Type", "application/json; charset=UTF-8");
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpc3MiOiJLTk9YU1NPIiwiYXVkIjoiRFNYIiwicm9sZSI6IkFkbWluIiwicGVybWlzc2lvbnMiOlsiYWRtaW5pc3RyYXRvciIsImNhbl9wcm92aXNpb24iXSwidWlkIjoiMTAwMDMzMDk5OSIsImF1dGhlbnRpY2F0b3IiOiJkZWZhdWx0IiwiZGlzcGxheV9uYW1lIjoiYWRtaW4iLCJjYW5fcmVmcmVzaF91bnRpbCI6MTU5MTk4MzkwMDQ0NCwiaWF0IjoxNTkxOTQ1ODkwLCJleHAiOjE1OTE5ODkwOTB9.pUEmdhahgLE5V5NVysd3AU9NI4MqzMffumfknWcgw4L-bMwaJxQpdtz2_k6yZQFw_R1JVffoLW-Ui6JvWBXb1kab1PeuNWu2JehwK-3s22HdRBDVuPHoPY2aXKYCuYXL7ER4FUhjMzRa9PA1wnqtcmRg_jiv4S5HYe8NXTR0T1g43MC63wxC51blYpdPmxwfDkRlnBybM0huEpMtRztGKRYZNvNssNMnz9S9_MHve2_hZHGmaRq3rL0Tnbj9SOcE6qe4v9vosGQa4D_9wBLOkIQ2TosEnyYBx3w62FOoHprfVMLvRA8UCkek4f9vFgsKEuBYdLZFffwEjQqCWQkuXw" ;
        httpget.addHeader("Authorization", "Bearer " + token);
        System.out.println("Executing request " + httpget.getRequestLine());

        CloseableHttpResponse response = httpclient.execute(httpget);

        try {
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            BufferedReader reader = new BufferedReader( new InputStreamReader(entity.getContent())) ;
            StringBuffer jsonStringScoring = new StringBuffer();
			String lineScoring;
			while ((lineScoring = reader.readLine()) != null) {
				jsonStringScoring.append(lineScoring);
			}
			System.out.println(jsonStringScoring);
            
            EntityUtils.consume(entity);
        	
        } finally {
            response.close();
        }
        httpclient.close();
    }
}
