package com.paligoutilities;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FindElement extends GridPane {

    final TextField tfTargetElement = new TextField();

    public FindElement(Utilities utilities) {
        super();

        Label label = new Label("Target element:");
        tfTargetElement.setText(utilities.getLastSearchText());

        tfTargetElement.setOnMouseClicked(e -> tfTargetElement.selectAll());

        this.add(label,0,0,1,1);
        this.add(tfTargetElement,1,0,1,1);
        this.setHgap(10);
    }

    public String getSearchText() {
        return tfTargetElement.getText();
    }
}
