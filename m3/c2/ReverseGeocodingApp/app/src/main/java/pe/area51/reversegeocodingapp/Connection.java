package pe.area51.reversegeocodingapp;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Connection {

    public static String doHttpGet(final String uri) throws IOException {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Accept", "application/json");
        return httpClient.execute(httpGet, new BasicResponseHandler());
    }

}
