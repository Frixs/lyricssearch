package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;

/**
 * @author Frixs
 */
public class SettingMenuController {
    /** reference to MainWindow controller */
    private MainWindowController    mainWindowController;

    /** settingMenu close BTN */
    @FXML private JFXButton settingMenuCloseBTN;
    /** Lyrics SIZE slider */
    @FXML private JFXSlider textWidthSLIDER;
    /** Lyrics WIDTH slider */
    @FXML private JFXSlider textSizeSLIDER;
    /** Dark theme toggle BTN */
    @FXML private JFXToggleButton darkThemeBTN;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        setDragEventTextWidthSLIDER();
        setDragEventTextSizeSLIDER();
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
     * Reload settings / on load
     */
    public void reloadSettings() {
        setTextWidth(getTextWidth());
        setTextSize(getTextSize());
        // TODO dark theme switch
    }

    /**
     * Set drag event listener
     */
    private void setDragEventTextWidthSLIDER() {
        this.textWidthSLIDER.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int nv = ((Double) newValue).intValue();
                setTextWidth(nv);
                //Log.getInstance().log(LogType.INFO, getClass().getName() +": Text width slider value was changed.");
            }
        });
    }

    /**
     * Set drag event listener
     */
    private void setDragEventTextSizeSLIDER() {
        this.textSizeSLIDER.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int nv = ((Double) newValue).intValue();
                setTextSize(nv);
                //Log.getInstance().log(LogType.INFO, getClass().getName() +": Text size slider value was changed.");
            }
        });
    }

    // Events
    @FXML
    void onActionDarkThemeBTN(ActionEvent event) {
        // TODO dark theme switch

        Log.getInstance().log(LogType.INFO, getClass().getName() +": Dark theme button was switched.");
    }

    // Getters
    public JFXButton getSettingMenuCloseBTN() {
        return settingMenuCloseBTN;
    }

    public int getTextWidth() {
        return this.textWidthSLIDER.valueProperty().getValue().intValue();
    }

    public int getTextSize() {
        return this.textSizeSLIDER.valueProperty().getValue().intValue();
    }

    // Setters
    public void setTextWidth(int width) {
        double pixelFullWidth = mainWindowController.getContentBP().getWidth();

        if (width < 100) {
            mainWindowController.getPreviewTabController().getLyricsTextSP().setMaxWidth((int)((pixelFullWidth / 100) * width));
        } else {
            mainWindowController.getPreviewTabController().getLyricsTextSP().setMaxWidth(Region.USE_COMPUTED_SIZE);
        }
    }

    public void setTextSize(int size) {
        mainWindowController.getPreviewTabController().getLyricsTextTF().setStyle("-fx-font-size:"+ size);
    }
}
