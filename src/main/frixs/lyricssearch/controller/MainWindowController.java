package main.frixs.lyricssearch.controller;

import javafx.fxml.FXML;

/**
 * Created by Frixs.
 */
public class MainWindowController {
    /** reference to Program controller */
    private ProgramController programController;

    @FXML
    private void initialize() {
    }

    /**
     * Inject ProgramController reference to this controller
     * @param programController     instance of the main controller
     */
    public void injectProgramController(ProgramController programController) {
        this.programController = programController;
    }
}
