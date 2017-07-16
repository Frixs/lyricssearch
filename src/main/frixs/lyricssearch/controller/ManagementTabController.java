package main.frixs.lyricssearch.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
        // Title column
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(150);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Action column with buttons
        TableColumn<Song, Song> actionCol = new TableColumn<>("");
        actionCol.setMinWidth(100);
        actionCol.setSortable(false);
        actionCol.setCellValueFactory(o -> new ReadOnlyObjectWrapper<>(o.getValue()));
        actionCol.setCellFactory(param -> new ManageBTNsTableCell());

        // put all together
        this.songTableTV.getColumns().addAll(titleColumn, actionCol);

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
