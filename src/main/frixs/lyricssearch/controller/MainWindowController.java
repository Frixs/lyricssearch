package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.service.Log;
import main.frixs.lyricssearch.service.LogType;

import java.io.IOException;

/**
 * @author Frixs
 */
public class MainWindowController {
    /** search side menu width in pixels */
    private int searchSideMenuWidth = 260;
    /** reference to Program controller */
    private ProgramController programController;
    /** reference to SearchMenu controller */
    @FXML private SearchMenuController searchMenuController;
    /** reference to PreviewTab controller */
    @FXML private PreviewTabController previewTabController;

    /** searchMenu drawer */
    @FXML private JFXDrawer searchMenuDrawer;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        previewTabController.injectMainWindowController(this);
        includeSearchMenu();
    }

    /**
     * Inject parent - ProgramController reference to this controller
     * @param programController     instance of the main controller
     */
    public void injectProgramController(ProgramController programController) {
        if(this.programController == null) {
            this.programController = programController;
        } else {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": You are trying to rewrite controller reference.");
        }
    }

    /**
     * Method to append side search menu to the program
     */
    private void includeSearchMenu() {
        try {
            // load FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Program.PATH_TO_SRC +"view/SearchMenu.fxml"));
            Parent searchMenuWrapper = loader.load();

            // inject this controller to searchMenu controller as a parent controller
            SearchMenuController searchMenuController = loader.getController();
            searchMenuController.injectMainWindowController(this);

            // set drawer side pane
            searchMenuDrawer.setSidePane(searchMenuWrapper);

            // set menu width
            searchMenuDrawer.setTranslateX(-searchSideMenuWidth);
            searchMenuDrawer.setDefaultDrawerSize(searchSideMenuWidth);
            searchMenuDrawer.setPrefWidth(searchSideMenuWidth);

            // add event handler to search BTN - open & close
            previewTabController.getSearchMenuOpenBTN().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                searchMenuDrawer.open();
                searchMenuDrawer.setTranslateX(0);
            });

            // add event handler to close BTN
            searchMenuController.getSearchMenuCloseBTN().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                searchMenuDrawer.close();
                searchMenuDrawer.setOnDrawerClosed(event -> {
                    searchMenuDrawer.setTranslateX(-searchSideMenuWidth);
                });
            });

            Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SearchMenu successfully included to layout!");

        } catch (IOException e) {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": SearchMenu cannot be loaded!");
            e.printStackTrace();
        }
    }

    // Getters
    public ProgramController getProgramController() {
        return programController;
    }

    public SearchMenuController getSearchMenuController() {
        return searchMenuController;
    }

    public PreviewTabController getPreviewTabController() {
        return previewTabController;
    }
}
