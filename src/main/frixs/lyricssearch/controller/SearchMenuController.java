package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.frixs.lyricssearch.model.Song;
import main.frixs.lyricssearch.service.SearchListCell;
import main.frixs.lyricssearch.service.Data;
import main.frixs.lyricssearch.service.Log;
import main.frixs.lyricssearch.service.LogType;

/**
 * @author Frixs
 */
public class SearchMenuController {
    /** reference to MainWindow controller */
    private MainWindowController    mainWindowController;
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
     * @param mainWindowController     instance of the MainWindow controller
     */
    public void injectMainWindowController(MainWindowController mainWindowController) {
        if(this.mainWindowController == null) {
            this.mainWindowController = mainWindowController;
        } else {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": You are trying to rewrite controller reference.");
        }
    }

    /**
     * Initializes search box table
     */
    public void initSearchBox() {
        searchBoxLV = new JFXListView<Song>();

        // LV properties
        searchBoxLV.getStyleClass().add("searchBox");
        searchBoxLV.setEditable(false);
        searchBoxLV.setCellFactory(param -> new SearchListCell());

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SearchBox view created!");
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
        searchBoxLV.setItems(sortedData);

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": searchBox completely initialized!");
    }

    // Getters
    public JFXButton getSearchMenuCloseBTN() {
        return searchMenuCloseBTN;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }
}