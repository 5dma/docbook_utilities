package com.paligoutilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class PaligoUtilityPresenter {
    private final PaligoUtilityList model;
    private final PaligoUtilityView view;
    private File resourceFile;

    public PaligoUtilityPresenter(PaligoUtilityList model, PaligoUtilityView view, Utilities utilities) {
        this.model = model;
        this.view = view;
        if (utilities.getLastResourceFile() != null) {
            this.resourceFile = new File(utilities.getLastResourceFile());
        }
        attachEvents(utilities);
    }

    private void attachEvents(Utilities utilities) {
        view.btnClose.setOnAction(e -> {
            utilities.saveProperties();
            view.getScene().getWindow().hide();
        });

        view.btnResourceFile.setOnAction(e -> {
            resourceFile = view.fileChooser.showOpenDialog( view.getScene().getWindow());
            view.lblResourceFileName.setText(resourceFile.getName());
            utilities.setLastResourceFile(resourceFile.getAbsolutePath());

        });


        view.btnRun.setOnAction(event -> {
            PaligoUtility selectedPaligoUtility = view.listViewUtility.getSelectionModel().getSelectedItem();

            Node currentlyVisibleOptionsNode = utilities.getNodeCurrentlyVisibleOptions();
            switch (currentlyVisibleOptionsNode.getClass().getSimpleName()) {
                case "FindTextOptions":
                    utilities.setLastSearchText(((FindTextOptions) currentlyVisibleOptionsNode).getTfSearchText());
                    break;
                case "FindElement":
                    utilities.setLastElement(((FindElement) currentlyVisibleOptionsNode).getSearchText());
                    break;
                case "FindElementAttribute":
                    utilities.setLastElementAttributeElement(((FindElementAttribute) currentlyVisibleOptionsNode).getTargetElement());
                    utilities.setLastElementAttributeAttribute(((FindElementAttribute) currentlyVisibleOptionsNode).getTargetAttribute());
                    break;
                default:

            }

            String outputFile = selectedPaligoUtility.getOutputFile();
            Transform transform = new Transform(selectedPaligoUtility.getTransformFile(),
                    resourceFile.getAbsolutePath(),
                    outputFile);
            transform.transform(selectedPaligoUtility.getId(), view,utilities);
            if (selectedPaligoUtility.getId() != UtilityId.EXTERNAL_LINKS) {
                try {
                    Desktop.getDesktop().browse(new URI("file://" + outputFile));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        /* Displays the options panel for the selected utility */
        view.listViewUtility.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PaligoUtility>() {
            @Override
            public void changed(ObservableValue<? extends PaligoUtility> observable, PaligoUtility oldValue, PaligoUtility newValue) {
                view.lblDescriptions.setText(newValue.getDescription());
                view.gridPane.getChildren().remove(utilities.getNodeCurrentlyVisibleOptions());
                view.gridPane.add(newValue.getOptions(),2,1,1,1);
                utilities.setNodeCurrentlyVisibleOptions(newValue.getOptions());
                if (newValue.getId() == UtilityId.WELCOME) {
                    view.btnRun.setDisable(true);
                } else {
                    view.btnRun.setDisable(false);
                }
            }
        });
    }
}
