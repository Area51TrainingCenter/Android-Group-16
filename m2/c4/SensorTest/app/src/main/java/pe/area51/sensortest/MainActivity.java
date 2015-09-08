package pe.area51.sensortest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private boolean isAccelerometerOn;
    private TextView sensorInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        isAccelerometerOn = false;
        sensorInfoTextView = (TextView) findViewById(R.id.activity_main_sensor_data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch_accelerometer:
                if (isAccelerometerOn) {
                    item.setTitle(R.string.enable_accelerometer);
                    disableAccelerometer();
                } else {
                    item.setTitle(R.string.disable_accelerometer);
                    enableAccelerometer();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void enableAccelerometer() {
        isAccelerometerOn = true;
        final Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void disableAccelerometer() {
        isAccelerometerOn = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            updateSensorInfo(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void updateSensorInfo(final SensorEvent event) {
        final float x = event.values[0];
        final float y = event.values[1];
        final float z = event.values[2];
        final String sensorInfo =
                getString(R.string.axis_x, x) + "\n" +
                        getString(R.string.axis_y, y) + "\n" +
                        getString(R.string.axis_z, z);
        sensorInfoTextView.setText(sensorInfo);
    }
}
