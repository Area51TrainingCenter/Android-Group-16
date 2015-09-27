package pe.area51.reversegeocodingapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView statusTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.statusTextView = (TextView) findViewById(R.id.activity_main_status);
        this.latitudeTextView = (TextView) findViewById(R.id.activity_main_latitude);
        this.longitudeTextView = (TextView) findViewById(R.id.activity_main_longitude);
        findViewById(R.id.activity_main_query_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final double latitude = Double.valueOf(latitudeTextView.getText().toString());
                    final double longitude = Double.valueOf(longitudeTextView.getText().toString());
                    new ReverseGeocodingRequest(latitude, longitude).execute();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, R.string.input_error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_switch_location_service) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLocation(final Location location) {
        final String locationInfo = new StringBuilder()
                .append(getString(R.string.location_latitude, String.valueOf(location.getLatitude())))
                .append("\n")
                .append(getString(R.string.location_longitude, String.valueOf(location.getLongitude())))
                .append("\n")
                .append(getString(R.string.location_country, location.getCountry()))
                .append("\n")
                .append(getString(R.string.location_address, location.getAddress()))
                .toString();
        statusTextView.setText(locationInfo);
    }

    private class ReverseGeocodingRequest extends AsyncTask<Void, Void, Bundle> {

        ProgressDialog progressDialog;
        final double latitude;
        final double longitude;

        private static final String ERROR_MESSAGE = "error_message";

        private Location location;

        public ReverseGeocodingRequest(final double latitude, final double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(MainActivity.this, "", getString(R.string.getting_address), true, false);
        }

        @Override
        protected Bundle doInBackground(Void... params) {
            final Bundle responseBundle = new Bundle();
            try {
                this.location = NominatimRequest.doReverseGeocodingRequest(latitude, longitude);
            } catch (IOException e) {
                e.printStackTrace();
                final String errorMessage = getString(R.string.connection_error);
                responseBundle.putString(ERROR_MESSAGE, errorMessage);
            } catch (JSONException e) {
                e.printStackTrace();
                final String errorMessage = getString(R.string.parse_error);
                responseBundle.putString(ERROR_MESSAGE, errorMessage);
            }
            return responseBundle;
        }

        @Override
        protected void onPostExecute(Bundle responseBundle) {
            this.progressDialog.dismiss();
            if (responseBundle.getString(ERROR_MESSAGE, null) == null) {
                showLocation(this.location);
            } else {
                Toast.makeText(MainActivity.this, responseBundle.getString(ERROR_MESSAGE), Toast.LENGTH_LONG).show();
            }
        }
    }
}
