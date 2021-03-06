package main.frixs.lyricssearch.service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import main.frixs.lyricssearch.controller.PreviewTabController;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;
import main.frixs.lyricssearch.model.Song;
import org.controlsfx.glyphfont.Glyph;

/**
 * @author Frixs
 */
public class QueueListCell extends JFXListCell<Song> {
    /** reference to SearchMenu controller */
    private static PreviewTabController previewTabController;

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
            Glyph glyph;

            hbox.getStyleClass().add("hbox");
            // label styles
            label.setStyle("-fx-max-width: 195;");
            // GLYPH styles
            glyph = Glyph.create("FontAwesome|CLOSE");
            glyph.setTranslateX(-1);
            glyph.setTranslateY(-1);
            // BTN styles
            btn.setGraphic(glyph);
            btn.setPrefWidth(30);
            btn.setPrefHeight(30);
            // BTN event
            btn.setOnAction(event -> {
                Data.getInstance().removeSongFromQueue(item);
                Log.getInstance().log(LogType.INFO, getClass().getName() +": Song - "+ item.getTitle() +" - removed from queue.");
            });

            // put all together
            hbox.getChildren().addAll(label, pane, btn);
            HBox.setHgrow(pane, Priority.ALWAYS);

            // set
            setGraphic(hbox);
        }
    }

    /**
     * Inject parent - PreviewTabController reference to this controller
     * @param previewTabController     instance of the previewTab controller
     */
    public static void injectPreviewTabController(PreviewTabController previewTabController) {
        if(QueueListCell.previewTabController != null) {
            Log.getInstance().log(LogType.WARNING, QueueListCell.class.getName() + ": You just rewrote controller reference.");
        }
        QueueListCell.previewTabController = previewTabController;
    }
}