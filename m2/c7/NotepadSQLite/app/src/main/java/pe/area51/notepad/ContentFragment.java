package pe.area51.notepad;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {

    private TextView contentTextView;
    private TextView titleTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content, container, false);
        contentTextView = (TextView) view.findViewById(R.id.fragment_content_textview_content);
        titleTextView = (TextView) view.findViewById(R.id.fragment_content_textview_title);
        return view;
    }

    public void showNote(final Note note) {
        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());
    }
}
