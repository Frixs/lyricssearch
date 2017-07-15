package main.frixs.lyricssearch.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import main.frixs.lyricssearch.model.ITabControllable;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;
import main.frixs.lyricssearch.model.Song;
import main.frixs.lyricssearch.service.Data;
import main.frixs.lyricssearch.service.ManageBTNsTableCell;

/**
 * @author Frixs
 */
public class ManagementTabController implements ITabControllable {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;

    /** management tab root pane */
    @FXML private AnchorPane managementTab;
    /** TableView of the songs */
    @FXML private TableView<Song> songTableTV;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        buildSongTable();
        initSongTable();
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
     * Initializes song table with its parameters and event listeners
     */
    private void buildSongTable() {
        // ID column
        TableColumn<Song, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(20);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idString"));

        // Title column
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(50);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Action column with buttons
        TableColumn<Song, Boolean> actionCol = new TableColumn<>("");
        actionCol.setMinWidth(100);
        actionCol.setSortable(false);
        // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
        actionCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Song, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Song, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        // create a cell value factory with an add button for each row in the table.
        actionCol.setCellFactory(new Callback<TableColumn<Song, Boolean>, TableCell<Song, Boolean>>() {
            @Override
            public TableCell<Song, Boolean> call(TableColumn<Song, Boolean> personBooleanTableColumn) {
                return new ManageBTNsTableCell(songTableTV);
            }
        });

        // put all together
        this.songTableTV.getColumns().addAll(idColumn, titleColumn, actionCol);

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SongTableTV view created!");
    }

    /**
     * Initializes song table data
     */
    private void initSongTable() {
        this.songTableTV.setItems(Data.getInstance().getSongList());

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SongTableTV completely initialized!");
    }
}
