package main.frixs.lyricssearch.model;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.frixs.lyricssearch.init.Program;

/**
 * @author Frixs
 */
public class CustomInformAlert {
    /**
     * Constructor
     * @param type      alert type
     * @param title     title of the alert window
     * @param header    alert header text
     * @param context   context alert text
     */
    public CustomInformAlert(Alert.AlertType type, String title, String header, String context) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Program.APP_ICON);
        alert.showAndWait();
    }
}
