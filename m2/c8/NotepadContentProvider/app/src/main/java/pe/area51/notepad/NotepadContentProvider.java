package pe.area51.notepad;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class NotepadContentProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://pe.area51.notepad.ContentProvider");

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;

    private SQLiteManager sqLiteManager;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI.getAuthority(), "note", NOTE);
        uriMatcher.addURI(URI.getAuthority(), "note/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        this.sqLiteManager = SQLiteManager.getInstance(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NOTE:
                return NoteContract.MIME_ITEM;
            case NOTE_ID:
                return NoteContract.MIME_DIR;
            default:
                return null;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            if (uriMatcher.match(uri) == NOTE_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getReadableDatabase().query(
                    "notes", projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == NOTE) {
            final long id = sqLiteManager.getWritableDatabase().insert(
                    "notes", null, values);
            return id != -1 ? ContentUris.withAppendedId(uri, id) : null;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            if (uriMatcher.match(uri) == NOTE_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getWritableDatabase().delete(
                    "notes", selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            if (uriMatcher.match(uri) == NOTE_ID) {
                selection = "_id" + uri.getLastPathSegment();
            }
            return sqLiteManager.getWritableDatabase().update(
                    "notes", values, selection, selectionArgs);
        }
        return 0;
    }
}
