package main.frixs.lyricssearch.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.frixs.lyricssearch.model.ITabControllable;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;
import main.frixs.lyricssearch.model.Song;

/**
 * @author Frixs
 */
public class ManagementTabController implements ITabControllable {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;

    /** management tab root pane */
    @FXML private AnchorPane managementTab;
    /** TableView of the songs */
    @FXML private TableView<Song> songTableTV;

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
