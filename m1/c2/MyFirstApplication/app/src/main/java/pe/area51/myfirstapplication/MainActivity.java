package pe.area51.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    //Utilizaremos este valor para evitar equivocarnos al escribir la clave.
    public static final String EXTRA_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establecemos la vista para este Activity.
        setContentView(R.layout.activity_main);
        //Buscamos el Button y le asignamos un objeto OnClickListener para ejecutar una serie de rutinas cuando lo presionemos.
        findViewById(R.id.activity_main_button_send)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Obtenemos el nombre del EditText.
                        final String name = ((EditText) findViewById(R.id.activity_main_edittext_name)).getText().toString();
                        //Creamos un Intent para iniciar el Activity "HelloActivity".
                        final Intent intent = new Intent(MainActivity.this, HelloActivity.class);
                        //Le asignamos el nombre al Intent para poder recuperarlo desde el "HelloActivity".
                        intent.putExtra(EXTRA_NAME, name);
                        //Iniciamos el Activity "HelloActivity".
                        startActivity(intent);
                    }
                });
    }
}
