package com.paligoutilities;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FindTextOptions extends GridPane {

    final TextField tfSearchText = new TextField();

    public FindTextOptions(Utilities utilities) {
        super();

        Label lblSearchText = new Label("Search text:");
        tfSearchText.setText(utilities.getLastSearchText());

        tfSearchText.setOnMouseClicked(e -> tfSearchText.selectAll());

        this.add(lblSearchText,0,0,1,1);
        this.add(tfSearchText,1,0,1,1);
        this.setHgap(10);
    }


    public String getTfSearchText() {
        return tfSearchText.getText();
    }
}
