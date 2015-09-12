package pe.area51.alarmapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, R.string.alarm_fired, Toast.LENGTH_LONG).show();
        PendingIntent.getBroadcast(context, 0, new Intent(MainActivity.ACTION_ALARM_FIRED), 0).cancel();
    }
}
