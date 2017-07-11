package main.frixs.lyricssearch.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.frixs.lyricssearch.init.Program;

import java.io.IOException;

/**
 * @author Frixs
 */
public class MainWindowTab extends AnchorPane {
    /** type of the window */
    private MainWindowTabType type;
    /** tab reference */
    private AnchorPane tabReference;
    /** tab controller */
    private ITabControllable controller;

    public MainWindowTab(MainWindowTabType type) {
        try {
            // load FXML file
            FXMLLoader loader = new FXMLLoader();

            switch (type) {
                case PREVIEW:
                    loader.setLocation(getClass().getResource(Program.PATH_TO_SRC + "view/"+ type.toString() +"Tab.fxml"));
                    break;
                case ADDNEW:
                    loader.setLocation(getClass().getResource(Program.PATH_TO_SRC + "view/"+ type.toString() +"Tab.fxml"));
                    break;
                case MANAGEMENT:
                    loader.setLocation(getClass().getResource(Program.PATH_TO_SRC + "view/"+ type.toString() +"Tab.fxml"));
                    break;
            }

            this.tabReference = loader.load(); // File's root pane
            this.controller = loader.getController();

        } catch (IOException e) {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": "+ type.toString() +" tab cannot be loaded!");
            e.printStackTrace();
        }
    }

    // Getters
    public MainWindowTabType getType() {
        return type;
    }

    public AnchorPane getTabReference() {
        return tabReference;
    }

    public ITabControllable getController() {
        return controller;
    }
}
