package main.frixs.lyricssearch.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Frixs
 */
public class Song {
    /** title of the song */
    private SimpleStringProperty title;
    /** lyrics */
    private SimpleStringProperty text;

    public Song(String title, String text) {
        this.title  = new SimpleStringProperty(title);
        this.text   = new SimpleStringProperty(text);
    }

    public Song(Song copyInstance) {
        this(copyInstance.getTitle(), copyInstance.getText());
    }

    // Getters & Setters
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
