package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.*;

import java.io.IOException;

/**
 * @author Frixs
 */
public class MainWindowController {
    /** reference to Program controller */
    private ProgramController programController;
    /** reference to AddNewTab controller */
    private AddNewTabController addNewTabController = null;
    /** setting side menu width in pixels */
    private int settingSideMenuWidth = 350;

    /** reference to SearchMenu controller */
    @FXML private SearchMenuController searchMenuController;
    /** reference to PreviewTab controller */
    @FXML private PreviewTabController previewTabController;
    /** reference to SettingMenu controller */
    @FXML private SettingMenuController settingMenuController;
    /** Content pane */
    @FXML private BorderPane contentBP;
    /** settingMenu drawer */
    @FXML private JFXDrawer settingMenuDrawer;
    /** settingMenu open BTN */
    @FXML private JFXButton settingMenuOpenBTN;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        previewTabController.injectMainWindowController(this);
        includeSettingMenu();
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
     * Method to append side setting menu to the program
     */
    private void includeSettingMenu() {
        try {
            // load FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Program.PATH_TO_SRC +"view/SettingMenu.fxml"));
            Parent settingMenuWrapper = loader.load();

            // inject this controller to settingMenu controller as a parent controller
            this.settingMenuController = loader.getController();
            this.settingMenuController.injectMainWindowController(this);

            // set drawer side pane
            settingMenuDrawer.setSidePane(settingMenuWrapper);

            // set menu width
            settingMenuDrawer.setTranslateX(settingSideMenuWidth);
            settingMenuDrawer.setDefaultDrawerSize(settingSideMenuWidth);
            settingMenuDrawer.setPrefWidth(settingSideMenuWidth);

            // add event handler to open BTN
            this.settingMenuOpenBTN.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                settingMenuDrawer.open();
                settingMenuDrawer.setTranslateX(0);
            });

            // add event handler to close BTN
            this.settingMenuController.getSettingMenuCloseBTN().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                settingMenuDrawer.close();
                settingMenuDrawer.setOnDrawerClosed(event -> {
                    settingMenuDrawer.setTranslateX(settingSideMenuWidth);
                });
            });

            Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SettingMenu successfully included to layout!");

        } catch (IOException e) {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": SettingMenu cannot be loaded!");
            e.printStackTrace();
        }
    }

    // Events
    @FXML
    void onActionPreviewTabWindowBTN(ActionEvent event) {
        Log.getInstance().log(LogType.INFO, getClass().getName() +": MainWindow "+ MainWindowTabType.PREVIEW.toString() +" tab swapped.");
        MainWindowTab window = new MainWindowTab(MainWindowTabType.PREVIEW);
        this.previewTabController = (PreviewTabController) window.getController();
        this.previewTabController.injectMainWindowController(this);
        this.contentBP.setCenter(window.getTabReference());
        // reloadSettings settings
        this.settingMenuController.reloadSettings();
    }

    @FXML
    void onActionAddNewWindowBTN(ActionEvent event) {
        Log.getInstance().log(LogType.INFO, getClass().getName() +": MainWindow "+ MainWindowTabType.ADDNEW.toString() +" tab swapped.");
        MainWindowTab window = new MainWindowTab(MainWindowTabType.ADDNEW);
        this.addNewTabController = (AddNewTabController) window.getController();
        this.addNewTabController.injectMainWindowController(this);
        this.contentBP.setCenter(window.getTabReference());
    }

    @FXML
    void onActionManagementWindowBTN(ActionEvent event) {
        Log.getInstance().log(LogType.INFO, getClass().getName() +": MainWindow "+ MainWindowTabType.MANAGEMENT.toString() +" tab swapped.");
        // TODO management pane
    }

    // Getters
    public ProgramController getProgramController() {
        return programController;
    }

    public SearchMenuController getSearchMenuController() {
        return searchMenuController;
    }

    public SettingMenuController getSettingMenuController() {
        return settingMenuController;
    }

    public PreviewTabController getPreviewTabController() {
        return previewTabController;
    }

    public BorderPane getContentBP() {
        return contentBP;
    }
}
