package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import main.frixs.lyricssearch.init.Program;

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
        this.mainWindowController = mainWindowController;
    }

    // Events
    @FXML
    void onActionQueueBTN(ActionEvent event) {
        if(queuePane.isVisible()) {
            queuePane.setVisible(false);
            queuePane.setPrefWidth(0);
        } else {
            queuePane.setVisible(true);
            queuePane.setPrefWidth(queuePaneWidth);
        }
    }

    // Getters
    public JFXButton getSearchMenuOpenBTN() {
        return searchMenuOpenBTN;
    }
}