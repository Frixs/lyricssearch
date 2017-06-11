package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;
import main.frixs.lyricssearch.init.Program;

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
        this.programController = programController;
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
            searchMenuDrawer.setDefaultDrawerSize(searchSideMenuWidth);
            searchMenuDrawer.setPrefWidth(searchSideMenuWidth);
            searchMenuDrawer.setTranslateX(-searchSideMenuWidth);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
