package main.frixs.lyricssearch.controller;

import javafx.fxml.FXML;
import main.frixs.lyricssearch.model.ITabControllable;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;

/**
 * @author Frixs
 */
public class AddNewTabController implements ITabControllable {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;

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
        if(this.mainWindowController == null) {
            this.mainWindowController = mainWindowController;
        } else {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": You are trying to rewrite controller reference.");
        }
    }
}
