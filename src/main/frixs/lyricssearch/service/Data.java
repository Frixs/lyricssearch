package main.frixs.lyricssearch.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.frixs.lyricssearch.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Frixs
 */
public class Data {
    /** singleton reference */
    private static Data instance = null;
    /** list of all songs */
    private ObservableList<Song> list = FXCollections.observableArrayList();

    /**
     * No-parameter constructor
     */
    private Data() {
    }

    /**
     * Singleton option to get instance
     * @return      instance of this class
     */
    public static Data getInstance() {
        if(instance == null) {
            instance = new Data();
        }

        return instance;
    }

    /**
     * Generate data to debug
     */
    public void generateLoremIpsumData() {
        list.add(new Song("Zajímavý song", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras pede libero, dapibus nec, pretium sit amet, tempor quis. Integer imperdiet lectus quis justo. Nulla est."));
        list.add(new Song("Bezvýznamný", "Donec ipsum massa, ullamcorper in, auctor et, scelerisque sed, est. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Etiam neque. Nullam rhoncus aliquam metus. Praesent vitae arcu tempor neque lacinia pretium. Suspendisse nisl.\n\nInteger vulputate sem a nibh rutrum consequat. Quisque porta."));
        list.add(new Song("White Habbit", "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla est. Ut tempus purus at lorem. Nunc tincidunt ante vitae massa. Maecenas sollicitudin."));
        list.add(new Song("ROCK", "Xum sociis natoque penatibus et magnis nas sollicitudin."));
        list.add(new Song("LDOCK", "Konec ipsum massa, ullamcorper in, auctor et, scelerisque sed."));
        list.add(new Song("No title selexc", "Rumsociis natoque penatibus et magnis nas sollicitudin."));
        list.add(new Song("Impossible", "XXpede libero, dapibus nec, pretium sit amet, tempor quis. Integer imperdiet lec."));
        list.add(new Song("Battle Fury", "Cras pede libero, dapibus nec, pretium sit amet."));
        list.add(new Song("Jelino", "In, auctor et, scelerisque sed, est. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Etiam neque. Nullam rhoncus aliquam metus. Praesent vitae arcu tempor neque lacinia pretium. Suspendisse nisl.\n\nInteger vulputate sem a nibh rutrum consequat. Quisque porta."));
        list.add(new Song("Veni, Vidi, Vici", "Nascetur ridiculus mus. Nulla est. Ut tempus purus at lorem. Nunc tincidunt ante vitae massa. Maecenas sollicitudin."));
        list.add(new Song("Inhabbitans", "Ipsum massa, ullamcorper in, auctor et, scelerisque sed, est. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Class apten."));
        list.add(new Song("ROCK", "Nulla est. Ut tempus purus at lorem. Nunc tincidunt ante vitae massa. Maecenas sollicitudin."));
        list.add(new Song("Locker dock", "Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla est. Ut tempus purus at lorem. Nunc."));

        this.sort();

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": Data (LoremIpsum) successfully loaded!");
    }

    /**
     * Load all songs from the database file
     */
    public void loadSongs() {
        // TODO add song - file (xml) structure

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": Data successfully loaded!");
    }

    /**
     *  Sort this.list alphabetically
     */
    private void sort() {
        Collections.sort(this.list, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }
        });
    }

    // Getters
    public ObservableList<Song> getList() {
        return list;
    }
}
