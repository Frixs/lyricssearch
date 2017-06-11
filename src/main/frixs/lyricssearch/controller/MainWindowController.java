package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import main.frixs.lyricssearch.init.Program;

import java.io.IOException;

/**
 * @author Frixs
 */
public class MainWindowController {
    /** reference to Program controller */
    private ProgramController programController;
    /** reference to SearchMenu controller */
    @FXML private SearchMenuController searchMenuController;

    @FXML private AnchorPane searchPane;
    @FXML private JFXButton searchMenuCloseBTN;
    @FXML private JFXDrawer searchMenuDrawer;

    @FXML
    private void initialize() {
        //searchMenuController.injectMainWindowController(this);
        includeSearchMenu();
    }

    /**
     * Inject parent - ProgramController reference to this controller
     * @param programController     instance of the main controller
     */
    public void injectProgramController(ProgramController programController) {
        this.programController = programController;
    }

    /**
     * Method to append side search menu to the program
     */
    private void includeSearchMenu() {
        try {
            // load FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Program.PATH_TO_VIEW +"SearchMenu.fxml"));
            Parent searchMenuWrapper = loader.load();

            // inject this controller to searchMenu controller as a parent controller
            SearchMenuController searchMenuController = loader.getController();
            searchMenuController.injectMainWindowController(this);

            // set drawer side pane
            searchMenuDrawer.setSidePane(searchMenuWrapper);

            // add event handler to search BTN - open & close
            searchMenuCloseBTN.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                if(searchMenuDrawer.isShown()) {
                    searchMenuDrawer.close();
                } else {
                    searchMenuDrawer.open();
                }
            });

            // add event handler to close BTN
            searchMenuCloseBTN.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                searchMenuDrawer.close();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
