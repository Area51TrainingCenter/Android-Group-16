package pe.area51.notepad;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class NoteContract {

    public static final Uri URI = Uri.parse("content://pe.area51.notepad.ContentProvider/note");

    public static final String MIME_ITEM = "vnd.android.cursor.item/vnd.pe.area51.notepad.note";
    public static final String MIME_DIR = "vnd.android.cursor.dir/vnd.pe.area51.notepad.note";

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String CREATION_TIMESTAMP = "creationTimestamp";

    public static List<Note> manyFromCursor(final Cursor cursor) {
        final List<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex("_id"));
            final String title = cursor.getString(cursor.getColumnIndex("title"));
            final String content = cursor.getString(cursor.getColumnIndex("content"));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex("creationTimestamp"));
            final Note note = new Note(id, title, content, creationTimestamp);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

}
