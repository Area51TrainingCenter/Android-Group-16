package pe.area51.broadcasttestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String message;
        switch (intent.getAction()) {
            case MainActivity.ACTION_MENU_BUTTON_CLICKED:
                message = "Button clicked!";
                break;
            case Intent.ACTION_BATTERY_LOW:
                message = "Battery low!";
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                message = "Airplane mode!";
                break;
            case Intent.ACTION_BOOT_COMPLETED:
                message = "Boot completed!";
                break;
            default:
                message = "N/A";
                break;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
