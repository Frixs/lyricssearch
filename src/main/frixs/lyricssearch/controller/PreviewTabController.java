package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.frixs.lyricssearch.model.Song;
import main.frixs.lyricssearch.service.Data;
import main.frixs.lyricssearch.service.Log;
import main.frixs.lyricssearch.service.LogType;

/**
 * @author Frixs
 */
public class PreviewTabController {
    /** queue pane width in pixels */
    private int queuePaneWidth = 250;
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;
    /** current displayed song */
    private Song currentSong = null;
    /** searchMenu open BTN */
    @FXML private JFXButton searchMenuOpenBTN;
    /** queue scroll pane */
    @FXML private ScrollPane queuePane;
    /** Title label */
    @FXML private Label titleLabel;
    /** @textTF 's Scroll Pane which can define width of the lyrics */
    @FXML private ScrollPane textSP;
    /** Text Flow for lyrics */
    @FXML private TextFlow textTF;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        queuePane.setVisible(false);
        queuePane.setPrefWidth(0);
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
     * Add Song to the queue
     * @param song      Song instance
     */
    public void addToQueue(Song song) {
        Data.getInstance().getQueue().add(song);
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
        //this.textTF.getChildren().clear();
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
            this.addToQueue(currentSong);
            Log.getInstance().log(LogType.INFO, getClass().getName() +": Current song ("+ currentSong.getTitle() +") added to queue.");
        }
    }

    // Getters
    public JFXButton getSearchMenuOpenBTN() {
        return searchMenuOpenBTN;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }
}
