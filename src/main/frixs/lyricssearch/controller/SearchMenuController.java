package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import main.frixs.lyricssearch.model.Song;
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
    private TableView<Song>         searchBoxTV;

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

        // paste search box table to the search box wrapper
        searchBP.setCenter(searchBoxTV);

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
        searchBoxTV = new TableView<Song>();

        TableColumn<Song, String> titleCol      = new TableColumn<Song, String>("Title");
        TableColumn<Song, Song> queueBTNCol 	= new TableColumn<Song, Song>("Add Queue");

        // col properties
        titleCol.setSortable(false);
        titleCol.setEditable(false);
        queueBTNCol.setSortable(false);
        queueBTNCol.setEditable(false);

        // col settings
        titleCol.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        queueBTNCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        queueBTNCol.setCellFactory(param -> new TableCell<Song, Song>() {
            private final JFXButton btn = new JFXButton("Add to queue");

            @Override
            protected void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);

                if (song == null) {
                    setGraphic(null);
                    return;
                }

                // BTN event
                setGraphic(btn);
                btn.setOnAction(event -> mainWindowController.getPreviewTabController().addToQueue(song));
            }
        });

        // get cols together
        searchBoxTV.getColumns().addAll(titleCol, queueBTNCol);

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
        searchBoxTV.setItems(sortedData);

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
