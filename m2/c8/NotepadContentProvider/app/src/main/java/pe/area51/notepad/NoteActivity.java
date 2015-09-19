package pe.area51.notepad;

import android.app.Activity;
import android.os.Bundle;

public class NoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        final ContentFragment contentFragment = (ContentFragment) getFragmentManager().findFragmentById(R.id.activity_note_fragment_content);
        final Note note = getIntent().getParcelableExtra(MainActivity.EXTRA_NOTE);
        contentFragment.showNote(note);
    }
}
