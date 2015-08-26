package pe.area51.fragmentapp;

public class Note {

    private final String title;
    private final String content;

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
}
