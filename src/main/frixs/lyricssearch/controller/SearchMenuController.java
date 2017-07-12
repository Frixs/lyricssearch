package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.frixs.lyricssearch.model.Song;
import main.frixs.lyricssearch.service.SearchListCell;
import main.frixs.lyricssearch.service.Data;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;

/**
 * @author Frixs
 */
public class SearchMenuController {
    /** reference to MainWindow controller */
    private PreviewTabController previewTabController;
    /** search box */
    private JFXListView<Song>       searchBoxLV;

    /** search wrapper border pane */
    @FXML private BorderPane        searchBP;
    /** searchMenu close BTN */
    @FXML private JFXButton         searchMenuCloseBTN;
    /** search text field */
    @FXML private JFXTextField      searchField;
    /** defines if search is set fulltext or not */
    @FXML private JFXCheckBox       searchFulltextCheckbox;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        // create table view as search box
        initSearchBox();

        // create wrapper for search box
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("menu-grandsub-light");
        anchorPane.getChildren().add(searchBoxLV);
        AnchorPane.setTopAnchor(searchBoxLV,    0D);
        AnchorPane.setLeftAnchor(searchBoxLV,   0D);
        AnchorPane.setRightAnchor(searchBoxLV,  0D);
        AnchorPane.setBottomAnchor(searchBoxLV, 0D);
        // paste search box table to the search box wrapper
        searchBP.setCenter(anchorPane);

        // set search box functionality
        initSearch();
    }

    /**
     * Inject parent - MainWindowController reference to this controller
     * @param previewTabController     instance of the MainWindow controller
     */
    public void injectPreviewTabController(PreviewTabController previewTabController) {
        if(this.previewTabController == null) {
            this.previewTabController = previewTabController;
        } else {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": You are trying to rewrite controller reference.");
        }
    }

    /**
     * Initializes search box table with its parameters and event listeners
     */
    public void initSearchBox() {
        searchBoxLV = new JFXListView<>();

        // inject this controller reference to SearchListCell class
        SearchListCell.injectSearchMenuController(this);

        // LV properties
        searchBoxLV.getStyleClass().add("searchBox");
        searchBoxLV.setEditable(false);
        searchBoxLV.setCellFactory(param -> new SearchListCell());

        // add event listener ON SELECT item
        searchBoxLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if(newValue == null)
                    return;

                previewTabController.loadSong(newValue);
                Log.getInstance().log(LogType.INFO, getClass().getName() + ": Current song changed to " + newValue.getTitle() + ".");
            }
        });

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SearchBox view created!");
    }

    /**
     * Initializes search functionality
     */
    private void initSearch() {
        ObservableList<Song> observableData = Data.getInstance().getSongList();
        FilteredList<Song> filteredData     = new FilteredList<>(observableData, p -> true);

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
        searchBoxLV.setItems(sortedData);

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SearchBox completely initialized!");
    }

    // Getters
    public JFXButton getSearchMenuCloseBTN() {
        return searchMenuCloseBTN;
    }
}