package main.frixs.lyricssearch.init;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.frixs.lyricssearch.controller.ProgramController;

import java.io.IOException;

/**
 * @author Frixs
 */
public class Program extends Application {

    private Stage                   primaryStage;
    private Parent                  rootLayout;
    public static final String      APP_NAME        = "LyricsSearch";
    public static final String      VERSION_PREFIX  = "v";
    public static final String      VERSION         = "1.0.0";
    public static final int         MIN_HEIGHT      = 480;
    public static final int         MIN_WIDTH       = 640;
    public static final String      PATH_TO_VIEW    = "/main/frixs/lyricssearch/view/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APP_NAME + " " + VERSION_PREFIX + VERSION);

        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Program.class.getResource(Program.PATH_TO_VIEW +"Program.fxml"));
            rootLayout = loader.load();

            ProgramController programController = loader.getController();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, MIN_WIDTH, MIN_HEIGHT);
            //scene.getStylesheets().add("/main/resources/css/main.css");

            primaryStage.setScene(scene);

            primaryStage.setResizable(true);
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setMinHeight(MIN_HEIGHT);

            primaryStage.setOnCloseRequest(e -> Platform.exit());
            programController.injectProgram(this);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method occurs before application will be closed
     */
    @Override
    public void stop() {
        System.out.println("Application is closing.");
    }

    // Getters
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}