package pe.area51.myfirstapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class HelloActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        //Obtenemos el Intent con el que se inició este Activity para luego obtener el String enviado.
        final String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        //Asignamos la cadena enviada al TextView.
        ((TextView) findViewById(R.id.activity_hello_textview_hello))
                .setText(getString(R.string.hello, name));
    }
}
