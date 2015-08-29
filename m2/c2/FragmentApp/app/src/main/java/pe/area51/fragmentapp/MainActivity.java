package pe.area51.fragmentapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends Activity implements ListFragment.OnNoteClickListener {

    public final static String EXTRA_NOTE = "note";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        final ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.activity_main_fragment_list);
        listFragment.setOnNoteClickListener(this);
    }

    @Override
    public void onNoteClick(final Note note) {
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            ((ContentFragment) fragmentManager.findFragmentById(R.id.activity_main_fragment_content)).showNote(note);
        }else{
            final Intent intent = new Intent(this, NoteActivity.class)
                    .putExtra(EXTRA_NOTE, note);
            startActivity(intent);
        }
    }
}
