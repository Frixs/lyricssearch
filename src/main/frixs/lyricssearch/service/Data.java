package main.frixs.lyricssearch.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.frixs.lyricssearch.model.Song;

/**
 * @author Frixs
 */
public class Data {
    /** list of all songs */
    private static ObservableList<Song> list = FXCollections.observableArrayList();

    /**
     * Generate data to debug
     */
    public static void generateLoremIpsumData() {
        list.add(new Song("Zajímavý song", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras pede libero, dapibus nec, pretium sit amet, tempor quis. Integer imperdiet lectus quis justo. Nulla est."));
        list.add(new Song("Bezvýznamný", "Donec ipsum massa, ullamcorper in, auctor et, scelerisque sed, est. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Etiam neque. Nullam rhoncus aliquam metus. Praesent vitae arcu tempor neque lacinia pretium. Suspendisse nisl.\n\nInteger vulputate sem a nibh rutrum consequat. Quisque porta."));
        list.add(new Song("White Habbit", "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla est. Ut tempus purus at lorem. Nunc tincidunt ante vitae massa. Maecenas sollicitudin."));
    }

    /**
     * Load all songs from the database file
     */
    public static void loadSongs() {
        // TODO add song - file (xml) structure
    }
}
