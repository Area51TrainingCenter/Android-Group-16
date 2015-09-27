package pe.area51.reversegeocodingapp;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NominatimRequest {

    private static final String SERVER_URL = "http://nominatim.openstreetmap.org/reverse";
    private static final String FORMAT_JSON = "format=json";
    private static final String LATITUDE = "lat=";
    private static final String LONGITUDE = "lon=";
    private static final String UTF_8 = "utf8";

    public static Location doReverseGeocodingRequest(final double latitude, final double longitude) throws IOException, JSONException {
        final String serverResponse = Connection.doHttpGet(buildReverseGeocodingQuery(latitude, longitude));
        return Parser.parseLocation(serverResponse);
    }

    private static String buildReverseGeocodingQuery(final double latitude, final double longitude) throws UnsupportedEncodingException {
        return new StringBuilder().append(SERVER_URL)
                .append("?")
                .append(FORMAT_JSON)
                .append("&")
                .append(LATITUDE)
                .append(URLEncoder.encode(String.valueOf(latitude), UTF_8))
                .append("&")
                .append(LONGITUDE)
                .append(URLEncoder.encode(String.valueOf(longitude), UTF_8))
                .toString();
    }

}
