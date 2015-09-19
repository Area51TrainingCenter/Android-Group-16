package pe.area51.notepad;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private ListView listView;

    private OnNoteClickListener onNoteClickListener;

    private NoteListViewAdapter noteListViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //El tercer parámetro del método inflate debe ser siempre falso en el onCreateView del fragment,
        //esto es así porque por defecto el Activity añade a las vistas generadas por sus fragments a su contenedor,
        //por lo que de ser verdadero se añadiría dos veces.
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        this.listView = (ListView) view.findViewById(R.id.fragment_list_listview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.noteListViewAdapter = new NoteListViewAdapter(this, new ArrayList<Note>());
        this.listView.setAdapter(this.noteListViewAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Note note = (Note) parent.getItemAtPosition(position);
                if (onNoteClickListener != null) {
                    onNoteClickListener.onNoteClick(note);
                }
            }
        });
    }

    public void setOnNoteClickListener(final OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public static class NoteListViewAdapter extends ArrayAdapter<Note> {

        private Fragment fragment;

        public NoteListViewAdapter(final Fragment fragment, final List<Note> notes) {
            super(fragment.getActivity(), 0, notes);
            this.fragment = fragment;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LayoutInflater layoutInflater = this.fragment.getActivity().getLayoutInflater();
            final View listElement = layoutInflater.inflate(R.layout.note_list_element, null, false);
            final TextView title = (TextView) listElement.findViewById(R.id.note_list_element_title);
            final TextView content = (TextView) listElement.findViewById(R.id.note_list_element_content);
            final TextView creationTimestamp = (TextView) listElement.findViewById(R.id.note_list_element_creation_timestamp);
            title.setText(getItem(position).getTitle());
            content.setText(getItem(position).getContent());
            creationTimestamp.setText(String.valueOf(getItem(position).getCreationTimestamp()));
            return listElement;
        }
    }

    public static interface OnNoteClickListener {

        public void onNoteClick(final Note note);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                final String title = "Test title";
                final String content = "Test content";
                final long creationTimestamp = System.currentTimeMillis();

                final ContentValues contentValues = new ContentValues();
                contentValues.put("title", title);
                contentValues.put("content", content);
                contentValues.put("creationTimestamp", creationTimestamp);
                final Uri uri = getActivity().getContentResolver().insert(
                        NoteContract.URI, contentValues);

                final long id = Long.valueOf(uri.getLastPathSegment());

                final Note note = new Note(id, title, content, creationTimestamp);
                this.noteListViewAdapter.add(note);
                return true;
            case R.id.action_get_notes:
                final Cursor cursor = getActivity().getContentResolver().query(NoteContract.URI, null, null, null, null);
                this.noteListViewAdapter.clear();
                this.noteListViewAdapter.addAll(NoteContract.manyFromCursor(cursor));
                return true;
            case R.id.action_remove_notes:
                this.noteListViewAdapter.clear();
                getActivity().getContentResolver().delete(NoteContract.URI, null, null);
        }
        return super.onOptionsItemSelected(item);
    }
}
