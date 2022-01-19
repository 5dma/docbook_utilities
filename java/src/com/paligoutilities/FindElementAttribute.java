package com.paligoutilities;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FindElementAttribute extends GridPane {

    final TextField tfTargetElement = new TextField();
    final TextField tfTargetAttribute = new TextField();

    public FindElementAttribute(Utilities utilities) {
        super();

        Label lblElement = new Label("Target element:");
        tfTargetElement.setText(utilities.getLastSearchText());
        tfTargetElement.setOnMouseClicked(e -> tfTargetElement.selectAll());

        Label lblAttribute = new Label("Target attribute:");
        tfTargetAttribute.setText(utilities.getLastSearchText());
        tfTargetAttribute.setOnMouseClicked(e -> tfTargetAttribute.selectAll());

        this.add(lblElement,0,0,1,1);
        this.add(tfTargetElement,1,0,1,1);
        this.add(lblAttribute,0,1,1,1);
        this.add(tfTargetAttribute,1,1,1,1);
        this.setHgap(10);
    }

    public String getTargetElement() {
        return tfTargetElement.getText();
    }
    public String getTargetAttribute() {
        return tfTargetAttribute.getText();
    }
}
