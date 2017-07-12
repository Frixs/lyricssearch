package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.ITabControllable;
import main.frixs.lyricssearch.model.Song;
import main.frixs.lyricssearch.service.Data;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;
import main.frixs.lyricssearch.service.QueueListCell;

import java.io.IOException;

/**
 * @author Frixs
 */
public class PreviewTabController implements ITabControllable {
    /** reference to MainWindow controller */
    private MainWindowController mainWindowController;
    /** queue pane width in pixels */
    private int queuePaneWidth = 250;
    /** search side menu width in pixels */
    private int searchSideMenuWidth = 260;
    /** current displayed song */
    private Song currentSong = null;
    /** queue box */
    private JFXListView<Song> queueBoxLV;

    /** root of the file */
    @FXML private AnchorPane previewTab;
    /** searchMenu open BTN */
    @FXML private JFXButton searchMenuOpenBTN;
    /** Title label */
    @FXML private Label titleLabel;
    /** @textTF 's Scroll Pane which can define width of the lyrics */
    @FXML private ScrollPane textSP;
    /** Text Flow for lyrics */
    @FXML private TextFlow textTF;
    /** queue wrapper */
    @FXML private AnchorPane queuePane;
    /** queue scroll pane */
    @FXML private ScrollPane queueSP;
    /** searchMenu drawer */
    @FXML private JFXDrawer searchMenuDrawer;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        // init search box
        includeSearchMenu();

        // init queue box
        queuePane.setVisible(false);
        queuePane.setPrefWidth(0);

        buildQueueBox();
        this.queueSP.setContent(this.queueBoxLV);
        initQueueBox();
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
     * Method to append side search menu to the program
     */
    private void includeSearchMenu() {
        try {
            // load FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Program.PATH_TO_SRC +"view/SearchMenu.fxml"));
            Parent searchMenuWrapper = loader.load();

            // inject this controller to searchMenu controller as a parent controller
            SearchMenuController searchMenuController = loader.getController();
            searchMenuController.injectPreviewTabController(this);

            // set drawer side pane
            searchMenuDrawer.setSidePane(searchMenuWrapper);

            // set menu width
            searchMenuDrawer.setTranslateX(-searchSideMenuWidth);
            searchMenuDrawer.setDefaultDrawerSize(searchSideMenuWidth);
            searchMenuDrawer.setPrefWidth(searchSideMenuWidth);

            // add event handler to search BTN - open & close
            this.searchMenuOpenBTN.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                searchMenuDrawer.open();
                searchMenuDrawer.setTranslateX(0);
            });

            // add event handler to close BTN
            searchMenuController.getSearchMenuCloseBTN().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                searchMenuDrawer.close();
                searchMenuDrawer.setOnDrawerClosed(event -> {
                    searchMenuDrawer.setTranslateX(-searchSideMenuWidth);
                });
            });

            Log.getInstance().log(LogType.CONFIG, getClass().getName() +": SearchMenu successfully included to layout!");

        } catch (IOException e) {
            Log.getInstance().log(LogType.SEVERE, getClass().getName() +": SearchMenu cannot be loaded!");
            e.printStackTrace();
        }
    }

    /**
     * Initializes queue box table with its parameters and event listeners
     */
    private void buildQueueBox() {
        queueBoxLV = new JFXListView<>();

        // inject this controller reference to QueueListCell class
        QueueListCell.injectPreviewTabController(this);

        // LV properties
        queueBoxLV.getStyleClass().add("searchBox");
        queueBoxLV.setEditable(false);
        queueBoxLV.setCellFactory(param -> new QueueListCell());

        // add event listener ON SELECT item
        queueBoxLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if (newValue == null)
                    return;

                loadSong(newValue);
                Log.getInstance().log(LogType.INFO, getClass().getName() +": Current song changed to "+ newValue.getTitle() +".");
            }
        });

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": QueueBox view created!");
    }

    /**
     * Initializes queue data
     */
    private void initQueueBox() {
        queueBoxLV.setItems(Data.getInstance().getQueueList());

        Log.getInstance().log(LogType.CONFIG, getClass().getName() +": QueueBox completely initialized!");
    }

    /**
     * Load song as current one.
     * @param song      instance of the song
     */
    public void loadSong(Song song) {
        // set current displayed song
        this.currentSong = song;

        // set title label
        this.titleLabel.setText("\u266B "+ this.currentSong.getTitle() +" \u266B");

        // create Text wrapper/box for lyrics
        Text text = new Text(this.currentSong.getText());
        text.getStyleClass().add("text-box");
        // add text wrapper to TF
        this.textTF.getChildren().clear();
        this.textTF.getChildren().add(text);
    }

    // Events
    @FXML
    private void onActionQueueBTN(ActionEvent event) {
        if(queuePane.isVisible()) {
            queuePane.setVisible(false);
            queuePane.setPrefWidth(0);
        } else {
            queuePane.setVisible(true);
            queuePane.setPrefWidth(queuePaneWidth);
        }
    }

    @FXML
    void onActionAddToQueueBTN(ActionEvent event) {
        if(currentSong != null) {
            Data.getInstance().addSongToQueue(currentSong);
            Log.getInstance().log(LogType.INFO, getClass().getName() +": Current song ("+ currentSong.getTitle() +") added to queue.");
        }
    }

    @FXML
    void onActionQueueNextBTN(ActionEvent event) {
        if (Data.getInstance().getQueueList().size() > 0) {
            Song removedSong = Data.getInstance().removeSongOnIndexFromQueue(0);
            Log.getInstance().log(LogType.INFO, getClass().getName() + ": Song (" + removedSong.getTitle() + ") removed from queue.");
            if (Data.getInstance().getQueueList().size() > 0) {
                Song nextSong = Data.getInstance().getQueueList().get(0);
                this.loadSong(nextSong);
                Log.getInstance().log(LogType.INFO, getClass().getName() + ": Current song changed to " + nextSong.getTitle() + ".");
            }
        }
    }

    // Getters
    public TextFlow getLyricsTextTF() {
        return this.textTF;
    }

    public ScrollPane getLyricsTextSP() {
        return this.textSP;
    }
}
