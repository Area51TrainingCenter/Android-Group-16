package pe.area51.fragmentapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends Activity implements ListFragment.OnNoteClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getFragmentManager();
        final ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.activity_main_fragment_list);
        listFragment.setOnNoteClickListener(this);
    }

    @Override
    public void onNoteClick(Note note) {
        Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
