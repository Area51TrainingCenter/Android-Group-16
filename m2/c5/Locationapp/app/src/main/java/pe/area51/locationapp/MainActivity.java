package pe.area51.locationapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private boolean isListeningLocation;
    private TextView locationInfoTextView;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isListeningLocation = false;
        locationInfoTextView = (TextView) findViewById(R.id.activity_main_textview_location);
        myBroadcastReceiver = new MyBroadcastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        } else {
            intentFilter.addAction(LocationManager.MODE_CHANGED_ACTION);
        }
        registerReceiver(myBroadcastReceiver, intentFilter);
        if (!areLocationProvidersEnabled()) {
            showEnableProvidersDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_get_location:
                if (isListeningLocation) {
                    unregisterLocationListener();
                    item.setTitle(R.string.get_location);
                } else {
                    registerLocationListener();
                    item.setTitle(R.string.stop_get_location);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void registerLocationListener() {
        isListeningLocation = true;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
    }

    private void unregisterLocationListener() {
        isListeningLocation = false;
        locationManager.removeUpdates(this);
    }

    private void showLocation(final Location location) {
        final StringBuilder locationInfo = new StringBuilder()
                .append(getString(R.string.latitude, location.getLatitude()))
                .append("\n")
                .append(getString(R.string.longitude, location.getLongitude()))
                .append("\n")
                .append(getString(R.string.accuracy, location.getAccuracy()))
                .append("\n")
                .append(getString(R.string.timestamp, location.getTime()));
        locationInfoTextView.setText(locationInfo.toString());
    }

    private boolean areLocationProvidersEnabled() {
        for (String provider : locationManager.getAllProviders()) {
            if (!locationManager.isProviderEnabled(provider))
                return false;
        }
        return true;
    }

    private void showEnableProvidersDialog() {
        if (getFragmentManager().findFragmentByTag("dialog_fragment") == null) {
            new EnableLocationDialogFragment().show(getSupportFragmentManager(), "dialog_fragment");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!areLocationProvidersEnabled()) {
                showEnableProvidersDialog();
            }
        }
    }

    public static class EnableLocationDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            /*
            Si se desea hacer que un DialogFragment no sea cancelable se debe llamar
            al método "setCancelable" del DialogFragment y no al método "setCancelable"
            del Dialog devuelto por este método.
            */
            setCancelable(false);
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.enable_location_dialog_title)
                    .setMessage(R.string.enable_location_dialog_message)
                    .setPositiveButton(R.string.enable_location_dialog_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            dismiss();
                        }
                    }).create();
        }
    }
}
