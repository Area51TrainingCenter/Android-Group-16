package pe.area51.sharedpreferencestest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView messageTextView;
    private EditText nameEditText;

    private final static String SHARED_PREFERENCES_NAME = "pe.area51.sharedpreferencestest.LAST_VISITOR";
    private final static String KEY_LAST_VISITOR = "lastVisitor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTextView = (TextView) findViewById(R.id.activity_main_textview_message);
        nameEditText = (EditText) findViewById(R.id.activity_main_edittext_name);
        final String lastVisitorName = loadLastVisitor();
        if (lastVisitorName != null) {
            messageTextView.setText(getString(R.string.last_visitor, lastVisitorName));
        } else {
            messageTextView.setText(getString(R.string.first_visitor));
        }
        findViewById(R.id.activity_main_button_register)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String name = nameEditText.getText().toString();
        messageTextView.setText(getString(R.string.welcome, name));
        saveLastVisitor(name);
    }

    private boolean saveLastVisitor(final String name) {
        return getSharedPreferences()
                .edit()
                .putString(KEY_LAST_VISITOR, name)
                .commit();
    }

    private String loadLastVisitor() {
        return getSharedPreferences().getString(KEY_LAST_VISITOR, null);
    }

    private SharedPreferences getSharedPreferences() {
        /*
        Si el sharedPreferences no existe entonces se crea, si existe se obtiene el existente.
         */
        return getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }
}
