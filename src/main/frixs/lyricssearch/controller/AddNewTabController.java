package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.*;
import main.frixs.lyricssearch.service.Data;

/**
 * @author Frixs
 */
public class AddNewTabController implements ITabControllable {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;

    /** root pane */
    @FXML private AnchorPane addNewTab;
    /** title text field */
    @FXML private JFXTextField songTitleTF;
    /** lyrics text area */
    @FXML private JFXTextArea songLyricsTA;

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

    // Events
    @FXML
    void onActionSubmitBTN(ActionEvent event) {
        String title    = songTitleTF.getText();
        String text     = songLyricsTA.getText();

        if (title.length() > 0 && text.length() > 0) {
            Data.getInstance().addNewSong(new Song(title, text));

            Log.getInstance().log(LogType.INFO, getClass().getName() +": Added new song - "+ title +".");
        } else {
            new CustomInformAlert(Alert.AlertType.WARNING, Program.APP_NAME +" | "+ Alert.AlertType.WARNING.toString(), "Some text fields are empty.", "Please, fill empty text fields in the form.");
        }
    }
}
