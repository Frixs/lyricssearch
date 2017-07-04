package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import main.frixs.lyricssearch.service.Log;
import main.frixs.lyricssearch.service.LogType;

/**
 * @author Frixs
 */
public class SettingMenuController {
    /** reference to MainWindow controller */
    private MainWindowController    mainWindowController;

    /** settingMenu close BTN */
    @FXML private JFXButton settingMenuCloseBTN;

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

    // Getters
    public JFXButton getSettingMenuCloseBTN() {
        return settingMenuCloseBTN;
    }
}
