package main.frixs.lyricssearch.controller;

import javafx.fxml.FXML;
import main.frixs.lyricssearch.init.Program;

/**
 * @author Frixs
 */
public class ProgramController {
    /** reference to main class - Program */
    private Program program;
    /** reference to MainWindowController controller */
    @FXML private MainWindowController mainWindowController;

    @FXML
    private void initialize() {
        mainWindowController.injectProgramController(this);
    }

    /**
     * Inject Program class reference to this controller
     * @param program       Program class instance
     */
    public void injectProgram(Program program) {
        this.program = program;
    }

    /**
     * Return instance of Program class
     * @return      instance of Program class
     */
    public Program getProgram() {
        return program;
    }
}