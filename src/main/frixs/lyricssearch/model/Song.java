package main.frixs.lyricssearch.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import main.frixs.lyricssearch.init.Program;

/**
 * @author Frixs
 */
public class Song {
    /** default ID value if it is not defined */
    public static final int ID_NULL = -1;
    /** last used ID (the highest one) */
    public static int lastId = ID_NULL;
    /** title of the song */
    private SimpleStringProperty title;
    /** lyrics */
    private SimpleStringProperty text;
    /** song ID */
    private SimpleIntegerProperty id;

    public Song(String title, String text, int id) {
        if (Song.lastId < id) {
            this.title  = new SimpleStringProperty(title);
            this.text   = new SimpleStringProperty(text);
            this.id     = new SimpleIntegerProperty(id);
            Song.lastId = id;
        } else {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": Error occurs during declaring Song!");
            new CustomInformAlert(Alert.AlertType.ERROR, Program.APP_NAME +" | "+ Alert.AlertType.ERROR.toString(), "Error occurs during declaring Song!", "ID of added Song is lower than Song.lastId!");
            Platform.exit();
        }
    }

    public Song(String title, String text) {
        this(title, text, ++Song.lastId);
    }

    public Song(Song copyInstance) {
        this(copyInstance.getTitle(), copyInstance.getText(), copyInstance.getId());
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

    public int getId() {
        return id.get();
    }

    public String getIdString() {
        return "#"+ id.get();
    }
}
