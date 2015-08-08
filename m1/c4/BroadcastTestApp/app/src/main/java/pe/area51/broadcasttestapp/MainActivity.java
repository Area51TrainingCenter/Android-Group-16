package pe.area51.broadcasttestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    public static final String ACTION_MENU_BUTTON_CLICKED = "pe.area51.broadcasttestapp.MENU_BUTTON_CLICKED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_send_custom_broadcast:
                /*
                Este flag hará que se incluyan las aplicaciones que se encuentren en estado detenido.
                Una aplicación está en estado detenido cuando:
                -Se ha instalado pero nunca ejecutado.
                -Se ha forzado el cierre con la opción "Forzar cierre" del menú de configuración.
                */
                sendBroadcast(new Intent(ACTION_MENU_BUTTON_CLICKED)
                        .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
