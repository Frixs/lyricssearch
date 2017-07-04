package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    /** searchMenu open BTN */
    @FXML private JFXButton searchMenuOpenBTN;
    /** queue scroll pane */
    @FXML private ScrollPane queuePane;
    /** Title label */
    @FXML private Label titleLabel;

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
     * Load to as current one.
     * @param song      instance of the song
     */
    public void loadSong(Song song) {
        // set title label
        titleLabel.setText("\u266B "+ song.getTitle() +" \u266B");

        // set text flow
        // TODO text flow
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
        this.getMainWindowController()
                .getSearchMenuController()
                .getSearchBoxLV()   // TODO doesn't work - NullPointerException
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView Selection Changed!");
        });

        //this.addToQueue(Song song);
    }

    // Getters
    public JFXButton getSearchMenuOpenBTN() {
        return searchMenuOpenBTN;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }
}
