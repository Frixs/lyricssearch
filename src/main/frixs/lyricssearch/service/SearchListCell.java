package main.frixs.lyricssearch.service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import main.frixs.lyricssearch.model.Song;
import org.controlsfx.glyphfont.Glyph;

/**
 * @author Frixs
 */
public class SearchListCell extends JFXListCell<Song> {
    @Override
    public void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox hbox       = new HBox();
            Pane pane       = new Pane();
            Label label     = new Label(item.getTitle());
            JFXButton btn   = new JFXButton();

            hbox.getStyleClass().add("hbox");
            // BTN styles
            btn.setGraphic(Glyph.create("FontAwesome|KEY"));
            btn.setPrefWidth(30);
            btn.setPrefHeight(30);
            // BTN event
            btn.setOnAction(event -> System.out.println("clicked!"));

            // put all together
            hbox.getChildren().addAll(label, pane, btn);
            HBox.setHgrow(pane, Priority.ALWAYS);

            // set
            setGraphic(hbox);
        }
    }
}