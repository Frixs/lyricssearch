package main.frixs.lyricssearch.service;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.StackPane;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;
import main.frixs.lyricssearch.model.Song;
import org.controlsfx.glyphfont.Glyph;

import java.util.Optional;

/**
 * @author Frixs
 */
public class ManageBTNsTableCell extends TableCell<Song, Song> {
    /** a button for deleting a song. */
    final JFXButton delBTN          = new JFXButton();
    /** pads and centers the buttons in the cell. */
    final StackPane paneSP          = new StackPane();

    /**
     * constructor
     */
    public ManageBTNsTableCell() {
        paneSP.setPadding(new Insets(3));
        paneSP.setAlignment(Pos.CENTER_RIGHT);

        // BTN styles
        Glyph delBTNglyph = Glyph.create("FontAwesome|CLOSE");
        delBTNglyph.setTranslateY(-1);
        delBTN.setGraphic(delBTNglyph);
        delBTN.setPrefWidth(24);
        delBTN.setPrefHeight(24);
        delBTN.setFocusTraversable(false);
        delBTN.setCursor(Cursor.HAND);
        delBTN.getStyleClass().add("btn-norm-light");

        paneSP.getChildren().add(delBTN);
    }

    /**
     * Places buttons in the row only if the row is not empty.
     */
    @Override
    protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            // delBTN event
            delBTN.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(Program.APP_NAME +" | "+ Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Do you want to delete the song - "+ item.getTitle() +"?");
                alert.setContentText("You will not be able to reverse this change.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Log.getInstance().log(LogType.INFO, getClass().getName() +": Song - "+ item.getTitle() +" - was deleted.");
                    Data.getInstance().removeSong(item);
                }
            });

            setGraphic(paneSP);
        }
    }
}
