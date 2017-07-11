package main.frixs.lyricssearch.controller;

import javafx.fxml.FXML;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;

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
        if(this.program == null) {
            this.program = program;
        } else {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": You are trying to rewrite controller reference.");
        }
    }

    /**
     * Return instance of Program class
     * @return      instance of Program class
     */
    public Program getProgram() {
        return program;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }
}