package pe.area51.notepad;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private final long id;
    private final String title;
    private final String content;
    private final long creationTimestamp;

    public Note(final Parcel parcel) {
        this.id = parcel.readLong();
        this.title = parcel.readString();
        this.content = parcel.readString();
        this.creationTimestamp = parcel.readLong();
    }

    public Note(final long id, final String title, final String content, final long creationTimestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeLong(this.creationTimestamp);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
