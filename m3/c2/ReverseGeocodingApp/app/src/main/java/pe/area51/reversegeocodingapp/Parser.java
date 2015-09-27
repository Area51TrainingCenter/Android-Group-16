package pe.area51.reversegeocodingapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Parser {

    private static final String JSON_LATITUDE = "lat";
    private static final String JSON_LONGITUDE = "lon";
    private static final String JSON_ADDRESS = "address";
    private static final String JSON_COUNTRY = "country";
    private static final String JSON_DISPLAY_NAME = "display_name";

    public static Location parseLocation(final String response) throws JSONException {
        final JSONObject jsonObject = new JSONObject(response);
        final double latitude = Double.valueOf(jsonObject.getString(JSON_LATITUDE));
        final double longitude = Double.valueOf(jsonObject.getString(JSON_LONGITUDE));
        final String address = jsonObject.getString(JSON_DISPLAY_NAME);
        final String country = jsonObject.getJSONObject(JSON_ADDRESS).getString(JSON_COUNTRY);
        return new Location(latitude, longitude, address, country);
    }

}
