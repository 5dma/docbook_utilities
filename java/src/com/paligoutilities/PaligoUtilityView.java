package com.paligoutilities;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;


import java.nio.file.Paths;

public class PaligoUtilityView extends HBox {

    final PaligoUtilityList model;
    final Button btnClose = new Button("Close");
    final Button btnResourceFile = new Button("Choose");
    final FileChooser fileChooser = new FileChooser();
    final Label lblResourceFileName = new Label();
    final Button btnRun = new Button("Run");
    final ListView<PaligoUtility> listViewUtility;
    final VBox vboxSpacer = new VBox();
    final Label lblDescriptions = new Label();
    final GridPane gridPane = new GridPane();

    public PaligoUtilityView(PaligoUtilityList model, Utilities utilities) {

        String lastResourceFileName = Paths.get(utilities.getLastResourceFile()).getFileName().toString();
        lblResourceFileName.setText(lastResourceFileName);
        lblResourceFileName.setWrapText(Boolean.TRUE);
        this.model = model;
        this.listViewUtility = new ListView<>(model.data);
        listViewUtility.setMaxHeight(200);
        listViewUtility.setMinWidth(150);
        listViewUtility.setMaxWidth(250);
        lblDescriptions.setText(model.data.get(0).getDescription());
        this.addEventHandler(KeyEvent.KEY_RELEASED, eventHandler);
        utilities.setNodeCurrentlyVisibleOptions(vboxSpacer);
        listViewUtility.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewUtility.setCellFactory(
                new Callback<ListView<PaligoUtility>, ListCell<PaligoUtility>>() {
                    @Override
                    public ListCell<PaligoUtility> call(ListView<PaligoUtility> listView) {
                        return new ListCell<PaligoUtility>() {
                            @Override
                            public void updateItem(PaligoUtility paligoUtility, boolean empty) {
                                super.updateItem(paligoUtility, empty);
                                int index = this.getIndex();
                                if (index == -1 || empty) {
                                } else {
                                    this.setText(paligoUtility.getLabel());
                                }

                            }
                        };
                    }
                }
        );

        lblDescriptions.setPadding(new Insets(0,10,0,00));
        lblDescriptions.setWrapText(true);

        fileChooser.setTitle("Resource file");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML files", "*.xml"));

        gridPane.add(listViewUtility,0,0,2,2);
        gridPane.add(lblDescriptions,2,0,1,1);
        gridPane.add(vboxSpacer,2,1,1,1);
        gridPane.add(btnRun,0,2,1,1);
        gridPane.add(btnClose,1,2,1,1);
        gridPane.add(lblResourceFileName,0,3,1,1);
        gridPane.add(btnResourceFile,1,3,1,1);

        RowConstraints row1 = new RowConstraints(105);
        RowConstraints row2 = new RowConstraints(105);
        RowConstraints row3 = new RowConstraints(30);
        RowConstraints row4 = new RowConstraints(30);
        gridPane.getRowConstraints().addAll(row1,row2,row3,row4);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        this.setPadding(new Insets(4,4,4,4));
        this.getChildren().add(gridPane);
    }

    public Node getOptions() {
        return this.gridPane.getChildren().get(6);
    }

    /**
     * Traps pressing the Enter key. When the Enter key is pressed, fire the Run button.
     */
    EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            if (e.getCode().equals(KeyCode.ENTER)) {
                btnRun.fire();
            }
        }
    };
}

