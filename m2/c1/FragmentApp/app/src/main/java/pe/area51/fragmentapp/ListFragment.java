package pe.area51.fragmentapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment {

    private ListView listView;

    private OnNoteClickListener onNoteClickListener;

    final private Note[] notes = new Note[]{
            new Note("Title 1", "Content 1"),
            new Note("Title 2", "Content 2"),
            new Note("Title 3", "Content 3"),
            new Note("Title 4", "Content 4"),
            new Note("Title 5", "Content 5"),
            new Note("Title 6", "Content 6"),
            new Note("Title 7", "Content 7"),
            new Note("Title 8", "Content 8"),
            new Note("Title 9", "Content 9"),
            new Note("Title 10", "Content 10")
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        this.listView = (ListView) view.findViewById(R.id.fragment_list_listview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.listView.setAdapter(new NoteListViewAdapter(this, notes));
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

        public NoteListViewAdapter(final Fragment fragment, final Note[] notes) {
            super(fragment.getActivity(), 0, notes);
            this.fragment = fragment;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LayoutInflater layoutInflater = this.fragment.getActivity().getLayoutInflater();
            final View listElement = layoutInflater.inflate(R.layout.note_list_element, null, false);
            final TextView title = (TextView) listElement.findViewById(R.id.note_list_element_title);
            final TextView content = (TextView) listElement.findViewById(R.id.note_list_element_content);
            title.setText(getItem(position).getTitle());
            content.setText(getItem(position).getContent());
            return listElement;
        }
    }

    public static interface OnNoteClickListener {

        public void onNoteClick(final Note note);

    }

}
