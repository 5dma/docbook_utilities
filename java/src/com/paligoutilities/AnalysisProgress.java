package com.paligoutilities;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

import java.net.URL;

public class AnalysisProgress extends GridPane {

    private ProgressBar progressBar = new ProgressBar(0d);
    private Label label = new Label();

    public AnalysisProgress() {
        super();
        progressBar.setProgress(0d);
        this.add(progressBar,0,0,1,1);
        this.add(label,0,1,1,1);
        this.setHgap(10);
    }

    public void setLabel(URL url) {
        this.label.setText("Checking URL " + url.toString());
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}
