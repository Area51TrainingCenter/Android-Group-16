package pe.area51.mybroadcasttestapp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String message;
        switch (intent.getAction()) {
            case "pe.area51.broadcasttestapp.MENU_BUTTON_CLICKED":
                message = "MENU_BUTTON_CLICKED";
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                message = "AIRPLANE_MODE_CHANGED";
                break;
            default:
                message = "N/A";
                break;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
