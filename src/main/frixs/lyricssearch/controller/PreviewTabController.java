package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.frixs.lyricssearch.model.ITabControllable;
import main.frixs.lyricssearch.model.Song;
import main.frixs.lyricssearch.service.Data;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;
import main.frixs.lyricssearch.service.QueueListCell;

/**
 * @author Frixs
 */
public class PreviewTabController implements ITabControllable {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;
    /** queue pane width in pixels */
    private int queuePaneWidth = 250;
    /** current displayed song */
    private Song currentSong = null;
    /** queue box */
    private JFXListView<Song> queueBoxLV;

    /** root of the file */
    @FXML private AnchorPane previewTab;
    /** searchMenu open BTN */
    @FXML private JFXButton searchMenuOpenBTN;
    /** Title label */
    @FXML private Label titleLabel;
    /** @textTF 's Scroll Pane which can define width of the lyrics */
    @FXML private ScrollPane textSP;
    /** Text Flow for lyrics */
    @FXML private TextFlow textTF;
    /** queue wrapper */
    @FXML private AnchorPane queuePane;
    /** queue scroll pane */
    @FXML private ScrollPane queueSP;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        queuePane.setVisible(false);
        queuePane.setPrefWidth(0);

        initQueueBox();
        this.queueSP.setContent(this.queueBoxLV);
        initQueue();
    }

    /**
     * Inject parent - MainWindowController reference to this controller
     * @param mainWindowController     instance of the MainWindow controller
     */
    public void injectMainWindowController(MainWindowController mainWindowController) {
        if(this.mainWindowController == null) {
            this.mainWindowController = mainWindowController;
        } else {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": You are trying to rewrite controller reference.");
        }
    }

    /**
     * Initializes queue box table with its parameters and event listeners
     */
    private void initQueueBox() {
        queueBoxLV = new JFXListView<>();

        // inject this controller reference to QueueListCell class
        QueueListCell.injectPreviewTabController(this);

        // LV properties
        queueBoxLV.getStyleClass().add("searchBox");
        queueBoxLV.setEditable(false);
        queueBoxLV.setCellFactory(param -> new QueueListCell());

        // add event listener ON SELECT item
        queueBoxLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                loadSong(newValue);
                Log.getInstance().log(LogType.INFO, getClass().getName() +": Current song changed to "+ newValue.getTitle() +".");
            }
        });

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": QueueBox view created!");
    }

    /**
     * Initializes queue data
     */
    private void initQueue() {
        queueBoxLV.setItems(Data.getInstance().getQueueList());

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": QueueBox completely initialized!");
    }

    /**
     * Load song as current one.
     * @param song      instance of the song
     */
    public void loadSong(Song song) {
        // set current displayed song
        this.currentSong = song;

        // set title label
        titleLabel.setText("\u266B "+ this.currentSong.getTitle() +" \u266B");

        // create Text wrapper/box for lyrics
        Text text = new Text(this.currentSong.getText());
        text.getStyleClass().add("text-box");
        // add text wrapper to TF
        this.textTF.getChildren().clear();
        this.textTF.getChildren().add(text);
    }

    // Events
    @FXML
    private void onActionQueueBTN(ActionEvent event) {
        if(queuePane.isVisible()) {
            queuePane.setVisible(false);
            queuePane.setPrefWidth(0);
        } else {
            queuePane.setVisible(true);
            queuePane.setPrefWidth(queuePaneWidth);
        }
    }

    @FXML
    void onActionAddToQueueBTN(ActionEvent event) {
        if(currentSong != null) {
            Data.getInstance().addSongToQueue(currentSong);
            Log.getInstance().log(LogType.INFO, getClass().getName() +": Current song ("+ currentSong.getTitle() +") added to queue.");
        }
    }

    @FXML
    void onActionQueueNextBTN(ActionEvent event) {
        if (Data.getInstance().getQueueList().size() > 0) {
            Song removedSong = Data.getInstance().removeSongOnIndexFromQueue(0);
            Log.getInstance().log(LogType.INFO, getClass().getName() + ": Song (" + removedSong.getTitle() + ") removed from queue.");
            if (Data.getInstance().getQueueList().size() > 0) {
                Song nextSong = Data.getInstance().getQueueList().get(0);
                this.loadSong(nextSong);
                Log.getInstance().log(LogType.INFO, getClass().getName() + ": Current song changed to " + nextSong.getTitle() + ".");
            }
        }
    }

    // Getters
    public JFXButton getSearchMenuOpenBTN() {
        return this.searchMenuOpenBTN;
    }

    public TextFlow getLyricsTextTF() {
        return this.textTF;
    }

    public ScrollPane getLyricsTextSP() {
        return this.textSP;
    }
}
