package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import main.frixs.lyricssearch.init.Program;

/**
 * @author Frixs
 */
public class PreviewTabController {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;
    /** searchMenu open BTN */
    @FXML private JFXButton searchMenuOpenBTN;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
    }

    /**
     * Inject parent - MainWindowController reference to this controller
     * @param mainWindowController     instance of the MainWindow controller
     */
    public void injectMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    // Getters
    public JFXButton getSearchMenuOpenBTN() {
        return searchMenuOpenBTN;
    }
}
