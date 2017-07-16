package main.frixs.lyricssearch.service;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
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
    final JFXButton editBTN         = new JFXButton();
    /** pads and centers the buttons in the cell. */
    final StackPane paneSP          = new StackPane();

    /**
     * constructor
     */
    public ManageBTNsTableCell() {
        paneSP.setPadding(new Insets(3));
        paneSP.setAlignment(Pos.CENTER_RIGHT);

        // delBTN styles
        Glyph delBTNglyph = Glyph.create("FontAwesome|CLOSE");
        delBTNglyph.setTranslateY(-1);
        delBTN.setGraphic(delBTNglyph);
        delBTN.setPrefWidth(24);
        delBTN.setPrefHeight(24);
        delBTN.setFocusTraversable(false);
        delBTN.setCursor(Cursor.HAND);
        delBTN.getStyleClass().add("btn-norm-light");
        // editBTN styles
        Glyph editBTNglyph = Glyph.create("FontAwesome|PENCIL");
        editBTNglyph.setTranslateY(-1);
        editBTN.setGraphic(editBTNglyph);
        editBTN.setPrefWidth(24);
        editBTN.setPrefHeight(24);
        editBTN.setFocusTraversable(false);
        editBTN.setCursor(Cursor.HAND);
        editBTN.getStyleClass().add("btn-norm-light");
        editBTN.setTranslateX(-34);

        paneSP.getChildren().add(delBTN);
        paneSP.getChildren().add(editBTN);
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
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Program.APP_ICON);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Log.getInstance().log(LogType.INFO, getClass().getName() +": Song - "+ item.getTitle() +" - was deleted.");
                    Data.getInstance().removeSong(item);
                }
            });
            // editBTN event
            editBTN.setOnAction(event -> {
                // Create the custom dialog.
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle(Program.APP_NAME +" | UPDATE");
                dialog.setHeaderText("Update data in the song - "+ item.getTitle() +".");
                ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(Program.APP_ICON);

                // Set the button types.
                ButtonType submitButtonType = new ButtonType("SUBMIT", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

                // Create the title and lyrics labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(10, 10, 0, 10));

                TextField titleTF = new TextField();
                titleTF.setPromptText("Song title");
                titleTF.setText(item.getTitle());
                TextArea textTA = new TextArea();
                textTA.setPromptText("Your lyrics");
                textTA.setText(item.getText());

                grid.add(new Label("Title:"), 0, 0);
                grid.add(titleTF, 1, 0);
                grid.add(new Label("Lyrics:"), 0, 1);
                grid.add(textTA, 1, 1);

                // Enable/Disable SUBMIT BTN depending on whether a title was entered.
                Node submitBTN = dialog.getDialogPane().lookupButton(submitButtonType);
                if (titleTF.getText().trim().isEmpty())
                    submitBTN.setDisable(true);

                // Do some validation (using the Java 8 lambda syntax).
                titleTF.textProperty().addListener((observable, oldValue, newValue) -> {
                    submitBTN.setDisable(newValue.trim().isEmpty());
                });

                dialog.getDialogPane().setContent(grid);

                // Request focus on the title field by default.
                Platform.runLater(() -> titleTF.requestFocus());

                // Convert the result to a title-lyrics-pair when the submit button is clicked.
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == submitButtonType) {
                        return new Pair<>(titleTF.getText(), textTA.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(param -> {
                    String title = param.getKey().trim();
                    String text  = param.getValue().trim();
                    Log.getInstance().log(LogType.INFO, getClass().getName() +": Song - "+ item.getTitle() +" - was updated.");
                    Data.getInstance().updateSong(item, new Song(title, text));
                });
            });

            setGraphic(paneSP);
        }
    }
}
