package main.frixs.lyricssearch.service;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import main.frixs.lyricssearch.model.Song;
import org.controlsfx.glyphfont.Glyph;

/**
 * @author Frixs
 */
public class ManageBTNsTableCell extends TableCell<Song, Boolean> {
    /** a button for deleting a song. */
    final JFXButton delBTN          = new JFXButton();
    /** pads and centers the buttons in the cell. */
    final StackPane paneSP          = new StackPane();

    /**
     * AddPersonCell constructor
     * @param table     the table to which a new person can be added.
     */
    public ManageBTNsTableCell(final TableView table) {
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
        // BTN event
        delBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                System.out.println("pressed!");
            }
        });

        paneSP.getChildren().add(delBTN);
    }

    /**
     * places an add button in the row only if the row is not empty.
     */
    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(paneSP);
        }
    }
}
