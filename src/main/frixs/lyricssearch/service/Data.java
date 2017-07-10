package main.frixs.lyricssearch.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.frixs.lyricssearch.model.Song;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Frixs
 */
public class Data {
    /** singleton reference */
    private static Data instance = null;
    /** songList of all songs */
    private ObservableList<Song> songList   = FXCollections.observableArrayList();
    /** observable queue */
    private ObservableList<Song> queueList  = FXCollections.observableArrayList();

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
    public void generateLoremIpsumSongs() {
        songList.add(new Song("Zajímavý song", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras pede libero,\ndapibus nec, pretium sit amet, tempor quis. Integer imperdiet lectus quis justo. Nulla est."));
        songList.add(new Song("Bezvýznamný", "Donec ipsum massa, ullamcorper in, auctor et,\n\nscelerisque sed, est. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Etiam neque. Nullam rhoncus aliquam metus. Praesent vitae arcu tempor neque lacinia pretium. Suspendisse nisl.\n\nInteger vulputate sem a nibh rutrum consequat. Quisque porta."));
        songList.add(new Song("White Habbit", "Cum sociis\nnatoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla est. Ut tempus purus at lorem. Nunc tincidunt ante vitae massa. Maecenas sollicitudin."));
        songList.add(new Song("ROCK", "Xum sociis natoque penatibus et magnis nas sollicitudin."));
        songList.add(new Song("LDOCK", "Konec ipsum massa, ullamcorper in, auctor et,\nscelerisque sed."));
        songList.add(new Song("No title selexc", "Rumsociis\nnatoque penatibus et magnis nas sollicitudin."));
        songList.add(new Song("Impossible", "XXpede libero, dapibus nec, pretium sit\n\namet, tempor quis. Integer imperdiet lec."));
        songList.add(new Song("Battle Fury", "Cras pede libero, dapibus nec, pretium\n\nsit amet."));
        songList.add(new Song("Jelino", "In, auctor et, scelerisque sed, est. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Etiam neque. Nullam rhoncus aliquam metus. Praesent vitae arcu tempor neque lacinia pretium. Suspendisse nisl.\n\nInteger vulputate sem a nibh rutrum consequat. Quisque porta."));
        songList.add(new Song("Veni, Vidi, Vici", "Nascetur ridiculus mus. Nulla est. Ut tempus purus at lorem. Nunc tincidunt ante vitae massa. Maecenas sollicitudin."));
        songList.add(new Song("Inhabbitans", "Ipsum massa,\n\nullamcorper in, auctor et, scelerisque sed, est. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Class apten."));
        songList.add(new Song("ROCK", "Nulla est. Ut tempus purus at lorem.\n\nNunc tincidunt ante vitae massa. Maecenas sollicitudin."));
        songList.add(new Song("Locker dock", "Sociis natoque penatibus et magnis dis parturient montes,\nnascetur ridiculus mus.\nNulla est. Ut tempus purus at lorem. Nunc."));

        this.sortSongs();

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
     *  Sort this.songList alphabetically
     */
    private void sortSongs() {
        Collections.sort(this.songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }
        });
    }

    /**
     * Add song to the queue
     * @param song
     */
    public void addSongToQueue(Song song) {
        queueList.add(song);
    }

    /**
     * Remove song from the queue
     * @param song
     */
    public void removeSongFromQueue(Song song) {
        queueList.remove(song);
    }

    /**
     * Remove song from the queue on the current index
     * @param index     removed index
     */
    public Song removeSongOnIndexFromQueue(int index) {
        Song curr = new Song(queueList.get(index));
        queueList.remove(index);
        return curr;
    }

    // Getters
    public ObservableList<Song> getSongList() {
        return songList;
    }

    public ObservableList<Song> getQueueList() {
        return queueList;
    }
}
