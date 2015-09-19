package pe.area51.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notepad_database";
    public static final int DATABASE_VERSION = 1;

    private static SQLiteManager instance;

    public static SQLiteManager getInstance(final Context context) {
        if (instance == null) {
            instance = new SQLiteManager(context.getApplicationContext());
        }
        return instance;
    }

    private SQLiteManager(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String creationScript = "CREATE TABLE notes (_id INTEGER PRIMARY KEY, title TEXT, content TEXT, creationTimestamp INTEGER)";
        db.execSQL(creationScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            final String upgradeScript = "DROP TABLE notes";
            db.execSQL(upgradeScript);
            onUpgrade(db, 2, 3);
        }
        if (oldVersion == 2 && newVersion == 3) {

        }
        if (oldVersion == 3 && newVersion == 4) {

        }
        if (oldVersion == 4 && newVersion == 5) {

        }
    }

    public List<Note> selectNotes() {
        final String selectScript = "SELECT * FROM notes";
        final Cursor cursor = getReadableDatabase().rawQuery(selectScript, null);
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

    public long insertNote(final Note note) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("content", note.getContent());
        contentValues.put("creationTimestamp", note.getCreationTimestamp());
        return getWritableDatabase().insert("notes", null, contentValues);
    }

}
