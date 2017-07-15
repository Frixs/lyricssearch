package main.frixs.lyricssearch.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.CustomInformAlert;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;
import main.frixs.lyricssearch.model.Song;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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
        this.songList = FXCollections.observableArrayList(getStoredSongs());
        this.sortSongs();

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": Data successfully loaded!");
    }

    /**
     * Add new song to the list and to the data file
     * @param song      new song instance
     */
    public void addNewSong(Song song) {
        // create line breaks with replacing line break characters
        song.setText(song.getText().replaceAll("\\\\n", "\n"));

        this.songList.add(song);

        this.writeNewDataFile(this.songList);
    }

    /**
     * Rewrite data file
     * @param songList      Song list
     */
    public void writeNewDataFile(ObservableList<Song> songList) {
        BufferedWriter bw = null;

        try {
            StringWriter stringWriter = new StringWriter();

            XMLOutputFactory xMLOutputFactory   = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter     = xMLOutputFactory.createXMLStreamWriter(stringWriter);

            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeStartElement("songs");

            for (Song song : songList) {
                xMLStreamWriter.writeStartElement("song");
                xMLStreamWriter.writeAttribute("id", Integer.toString(song.getId()));

                xMLStreamWriter.writeStartElement("title");
                xMLStreamWriter.writeCharacters(
                        song.getTitle()
                                .replaceAll("(\\r|\\n|\\t|\\r\\n)", " ")
                );
                xMLStreamWriter.writeEndElement();
                xMLStreamWriter.writeStartElement("text");
                xMLStreamWriter.writeCharacters(
                        song.getText()
                                .replaceAll("(\\r|\\n|\\t|\\r\\n)", "\\\\n")
                );
                xMLStreamWriter.writeEndElement();
                xMLStreamWriter.writeEndElement();
            }

            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.writeEndDocument();

            xMLStreamWriter.flush();
            xMLStreamWriter.close();

            String xmlString = stringWriter.getBuffer().toString();
            stringWriter.close();

            // write content to the file
            bw = new BufferedWriter(new FileWriter(new File(new File("./").getCanonicalPath() + Program.DATA_PATH)));
            bw.write(xmlString);
            bw.close();

        } catch (XMLStreamException e) {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": Error occurs during writing data!");
            e.printStackTrace();
            new CustomInformAlert(Alert.AlertType.ERROR, Program.APP_NAME +" | "+ Alert.AlertType.ERROR.toString(), "Error occurs during writing data!", e.getMessage());
            Platform.exit();
        } catch (IOException e) {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": Error occurs during writing data!");
            e.printStackTrace();
            new CustomInformAlert(Alert.AlertType.ERROR, Program.APP_NAME +" | "+ Alert.AlertType.ERROR.toString(), "Error occurs during writing data!", e.getMessage());
            Platform.exit();
        }
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
        if (queueList.get(index) == null)
            return null;

        Song curr = new Song(queueList.get(index));
        queueList.remove(index);
        return curr;
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
     * Get all stored songs
     * @return      list of the songs
     */
    private ArrayList<Song> getStoredSongs() {
        ArrayList<Song> list    = new ArrayList<>();
        boolean bTitle          = false;
        boolean bText           = false;
        String sTitle           = "";
        String sText            = "";
        int iIdentifier         = Song.ID_NULL;

        try {
            XMLInputFactory factory     = XMLInputFactory.newInstance();
            XMLEventReader eventReader  = factory.createXMLEventReader(new FileReader(
                    new File(new File("./").getCanonicalPath() + Program.DATA_PATH)
            ));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement   = event.asStartElement();
                        String qName                = startElement.getName().getLocalPart();

                        if (qName.equalsIgnoreCase("song")) {
                            //System.out.println("Start Element : song");
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            iIdentifier = Integer.parseInt(attributes.next().getValue());
                            //System.out.println("ID : " + iIdentifier);
                        } else if (qName.equalsIgnoreCase("title")) {
                            bTitle = true;
                        } else if (qName.equalsIgnoreCase("text")) {
                            bText = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();

                        if (bTitle) {
                            //System.out.println("Title: "+ characters.getData());
                            sTitle = characters.getData();
                            bTitle = false;
                        }
                        if (bText) {
                            //System.out.println("Text: "+ characters.getData());
                            sText = characters.getData().replaceAll("\\\\n", "\n");
                            bText = false;
                        }
                        break;

                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();

                        if (endElement.getName().getLocalPart().equalsIgnoreCase("song")) {
                            //System.out.println("End Element : song\n");
                        }
                        break;
                }

                if (sTitle.length() > 0 && sText.length() > 0 && iIdentifier > Song.ID_NULL) {
                    list.add(new Song(sTitle, sText, iIdentifier));
                    sTitle = "";
                    sText  = "";
                    iIdentifier = Song.ID_NULL;
                }
            }

            Log.getInstance().log(LogType.CONFIG, getClass().getName() +": Reading songs successfully finished from data file.");

        } catch (Exception e) {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": Error occurs during reading data!");
            e.printStackTrace();
            new CustomInformAlert(Alert.AlertType.ERROR, Program.APP_NAME +" | "+ Alert.AlertType.ERROR.toString(), "Error occurs during reading data!", e.getMessage());
            Platform.exit();

        }

        return list;
    }

    // Getters
    public ObservableList<Song> getSongList() {
        return songList;
    }

    public ObservableList<Song> getQueueList() {
        return queueList;
    }
}
