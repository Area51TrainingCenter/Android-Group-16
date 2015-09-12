package pe.area51.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlarmManager alarmManager;
    public static final String ACTION_ALARM_FIRED = "pe.area51.alarmapp.ALARM_FIRED";
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        findViewById(R.id.activity_main_button_register_alarm).setOnClickListener(this);
        findViewById(R.id.activity_main_button_cancel_alarm).setOnClickListener(this);
        updateAlarmStatus();
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, new IntentFilter(ACTION_ALARM_FIRED));
    }

    private void updateAlarmStatus() {
        final TextView alarmStatusTextView = (TextView) findViewById(R.id.activity_main_textview_alarm_status);
        if (pendingIntentExist()) {
            alarmStatusTextView.setText(getString(R.string.alarm_status, getString(R.string.alarm_status_set)));
        } else {
            alarmStatusTextView.setText(getString(R.string.alarm_status, getString(R.string.alarm_status_no_set)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_button_register_alarm:
                if (!pendingIntentExist()) {
                    final String editTextValue = ((EditText) findViewById(R.id.activity_main_edittext_time_in_seconds)).getText().toString();
                    try {
                        final int timeInSeconds = Integer.valueOf(editTextValue);
                        registerAlarm(timeInSeconds);
                        updateAlarmStatus();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, R.string.number_format_error, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.activity_main_button_cancel_alarm:
                if (pendingIntentExist()) {
                    cancelAlarm();
                    cancelPendingIntent();
                    updateAlarmStatus();
                }
                break;
        }
    }

    private void registerAlarm(final int timeInSeconds) {
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, getIntentForAlarm(), PendingIntent.FLAG_CANCEL_CURRENT);
        final long realtimeTriggerAtMillis = SystemClock.elapsedRealtime() + (timeInSeconds * 1000);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, realtimeTriggerAtMillis, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, realtimeTriggerAtMillis, pendingIntent);
        }
    }

    private Intent getIntentForAlarm() {
        return new Intent(ACTION_ALARM_FIRED);
    }

    private boolean pendingIntentExist() {
        return PendingIntent.getBroadcast(this, 0, getIntentForAlarm(), PendingIntent.FLAG_NO_CREATE) != null;
    }

    private void cancelPendingIntent() {
        PendingIntent.getBroadcast(this, 0, getIntentForAlarm(), 0).cancel();
    }

    private void cancelAlarm() {
        alarmManager.cancel(PendingIntent.getBroadcast(this, 0, getIntentForAlarm(), 0));
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            cancelPendingIntent();
            updateAlarmStatus();
        }

    }
}
