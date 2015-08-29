package pe.area51.fragmentapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable{

    private final String title;
    private final String content;

    public Note(final Parcel parcel){
        this.title = parcel.readString();
        this.content = parcel.readString();
    }

    public Note(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
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
