package pe.area51.serviceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start_service:
                startService(new Intent(this, MyService.class));
                return true;
            case R.id.action_stop_service:
                stopService(new Intent(this, MyService.class));
                return true;
            case R.id.action_launch_intent_service:
                startService(new Intent(this, MyIntentService.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
