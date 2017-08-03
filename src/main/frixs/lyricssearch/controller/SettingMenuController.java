package main.frixs.lyricssearch.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import main.frixs.lyricssearch.init.Program;
import main.frixs.lyricssearch.model.Log;
import main.frixs.lyricssearch.model.LogType;

/**
 * @author Frixs
 */
public class SettingMenuController {
    /** reference to MainWindow controller */
    private MainWindowController    mainWindowController;
    /** lyrics alignment setting */
    private String textAlignmentSetting = "CENTER";

    /** settingMenu close BTN */
    @FXML private JFXButton settingMenuCloseBTN;
    /** Lyrics SIZE slider */
    @FXML private JFXSlider textWidthSLIDER;
    /** Lyrics WIDTH slider */
    @FXML private JFXSlider textSizeSLIDER;
    /** Dark theme toggle BTN */
    @FXML private JFXToggleButton darkThemeBTN;
    /** copyright label */
    @FXML private Label copyrightLabel;

    /**
     * default initialize
     */
    @FXML
    private void initialize() {
        setDragEventTextWidthSLIDER();
        setDragEventTextSizeSLIDER();

        copyrightLabel.setText(Program.COPYRIGHT);
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
        setTextAlignment();
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
        this.setThemeColor();
    }

    @FXML
    void onActionAlignCenterBTN(ActionEvent event) {
        this.textAlignmentSetting = "CENTER";
        this.setTextAlignment();
    }

    @FXML
    void onActionAlignLeftBTN(ActionEvent event) {
        this.textAlignmentSetting = "LEFT";
        this.setTextAlignment();
    }

    @FXML
    void onActionAlignRightBTN(ActionEvent event) {
        this.textAlignmentSetting = "RIGHT";
        this.setTextAlignment();
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
        setStyles(size, this.textAlignmentSetting, isDarkTheme());
    }

    public void setThemeColor() {
        setStyles(getTextSize(), this.textAlignmentSetting, isDarkTheme());
    }

    public void setTextAlignment() {
        setStyles(getTextSize(), this.textAlignmentSetting, isDarkTheme());
    }

    /**
     * Set styles in 1 method
     * @param fontSize      font size
     * @param textAlign     text align
     */
    private void setStyles(int fontSize, String textAlign, boolean isThemeDark) {
        String cText = Program.SETTING_PREVIEW_TEXT_COLOR;
        String cBG   = Program.SETTING_PREVIEW_BG_COLOR;

        if (isThemeDark) {
            cText = Program.SETTING_PREVIEW_TEXT_COLOR_DARK;
            cBG   = Program.SETTING_PREVIEW_BG_COLOR_DARK;
        }

        mainWindowController.getPreviewTabController().getLyricsTextTF().setStyle(
                "-fx-font-size:"+ fontSize
                +";-fx-text-alignment:"+ textAlign
                +";-fx-background-color:"+ cBG
        );

        mainWindowController.getPreviewTabController().getLyricsTextSP().getParent().setStyle(
                "-fx-background-color:"+ cBG
        );

        for (int i = 0 ;i < mainWindowController.getPreviewTabController().getLyricsTextTF().getChildren().size(); i++) {
            mainWindowController.getPreviewTabController().getLyricsTextTF().getChildren().get(i).setStyle("-fx-fill:"+ cText);
        }
    }

    public boolean isDarkTheme() {
        return this.darkThemeBTN.isSelected();
    }
}
