package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import main.frixs.lyricssearch.model.Song;
import main.frixs.lyricssearch.service.Data;

/**
 * @author Frixs
 */
public class SearchMenuController {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;

    /** searchMenu close BTN */
    @FXML private JFXButton         searchMenuCloseBTN;
    /** search text field */
    @FXML private JFXTextField      searchField;
    /** search box */
    @FXML private TableView<Song>   searchBox;
    /** defines if search is set fulltext or not */
    @FXML private JFXCheckBox       searchFulltextCheckbox;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        initSearch();
    }

    /**
     * Inject parent - MainWindowController reference to this controller
     * @param mainWindowController     instance of the MainWindow controller
     */
    public void injectMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    /**
     * Initializes search functionality
     */
    private void initSearch() {
        FilteredList<Song> filteredData = new FilteredList<>(Data.getInstance().getList(), p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(song -> {
                // If filter text is empty, display all songs.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare title of every song with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (song.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches title.
                } else if (searchFulltextCheckbox.isSelected() && song.getText().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches title or part of the text.
                }
                return false; // Does not match.
            });
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Song> sortedData = new SortedList<>(filteredData);

        // Add sorted (and filtered) data to the search box.
        searchBox.setItems(sortedData);
    }

    // Getters
    public JFXButton getSearchMenuCloseBTN() {
        return searchMenuCloseBTN;
    }
}
