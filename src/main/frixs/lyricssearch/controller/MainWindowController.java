package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.*;

import java.io.IOException;

/**
 * @author Frixs
 */
public class MainWindowController {
    /** search side menu width in pixels */
    private int searchSideMenuWidth = 260;
    /** setting side menu width in pixels */
    private int settingSideMenuWidth = 350;
    /** reference to Program controller */
    private ProgramController programController;
    /** reference to AddNewTab controller */
    private AddNewTabController addNewTabController = null;

    /** reference to SearchMenu controller */
    @FXML private SearchMenuController searchMenuController;
    /** reference to PreviewTab controller */
    @FXML private PreviewTabController previewTabController;
    /** reference to SettingMenu controller */
    @FXML private SettingMenuController settingMenuController;
    /** Content pane */
    @FXML private BorderPane contentBP;
    /** Tab content */
    @FXML private AnchorPane tabContentAP;
    /** searchMenu drawer */
    @FXML private JFXDrawer searchMenuDrawer;
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
            SettingMenuController settingMenuController = loader.getController();
            settingMenuController.injectMainWindowController(this);

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
            settingMenuController.getSettingMenuCloseBTN().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
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
        MainWindowTab window = new MainWindowTab(MainWindowTabType.PREVIEW);
        this.contentBP.setCenter(window.getTabReference());
        // TODO search menu move into preview pane + (rekapitulace, jestli by nebylo lepší si reference na záložky uchovávat a nevytvářet vždy nové objekty při otevření)
    }

    @FXML
    void onActionAddNewWindowBTN(ActionEvent event) {
        MainWindowTab window = new MainWindowTab(MainWindowTabType.ADDNEW);
        this.contentBP.setCenter(window.getTabReference());

        // Load FXML at first time
        if (this.addNewTabController == null) {
            this.addNewTabController = (AddNewTabController) window.getController();
            this.addNewTabController.injectMainWindowController(this);
        }
    }

    @FXML
    void onActionManagementWindowBTN(ActionEvent event) {
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
}
