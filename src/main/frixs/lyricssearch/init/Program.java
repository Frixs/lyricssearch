package main.frixs.lyricssearch.init;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.frixs.lyricssearch.controller.ProgramController;
import main.frixs.lyricssearch.service.Data;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;

import java.io.File;
import java.io.IOException;

/**
 * @author Frixs
 */
public class Program extends Application {

    public static final String      APP_NAME        = "LyricsSearch";
    public static final String      VERSION         = "0.9.0 (beta)";
    public static final String      VERSION_PREFIX  = "v";
    /** Application window min height */
    public static final int         MIN_HEIGHT      = 480;
    /** Application window min width */
    public static final int         MIN_WIDTH       = 640;
    /** Package structure path */
    public static final String      PATH_TO_SRC     = "/main/frixs/lyricssearch/";
    /** Data path from the root structure (or from the application file) */
    public static final String      DATA_PATH       = File.separator + "lyricssearch_data.xml";

    private Stage                   primaryStage;
    private Parent                  rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APP_NAME + " " + VERSION_PREFIX + VERSION);

        // load init data
        initData();

        // load root layout
        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Program.class.getResource(Program.PATH_TO_SRC +"view/Program.fxml"));
            rootLayout = loader.load();

            ProgramController programController = loader.getController();

            //set icon of the application
            Image applicationIcon = new Image(getClass().getResourceAsStream(Program.PATH_TO_SRC +"resources/images/icon.png"));
            primaryStage.getIcons().add(applicationIcon);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, MIN_WIDTH, MIN_HEIGHT);
            scene.getStylesheets().add(Program.PATH_TO_SRC +"resources/css/program.css");
            scene.getStylesheets().add(Program.PATH_TO_SRC +"resources/css/mainWindow.css");

            primaryStage.setScene(scene);

            primaryStage.setResizable(true);
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setMinHeight(MIN_HEIGHT);

            primaryStage.setOnCloseRequest(e -> Platform.exit());
            programController.injectProgram(this);

            primaryStage.show();

            Log.getInstance().log(LogType.CONFIG, getClass().getName() +": Root layout successfully loaded!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes data
     */
    public void initData() {
        Data.getInstance().loadSongs();
        //Data.getInstance().generateLoremIpsumSongs();
    }

    /**
     * This method occurs before application will be closed
     */
    @Override
    public void stop() {
        Log.getInstance().log(LogType.CONFIG, "Application is closing.");
    }

    // Getters
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}