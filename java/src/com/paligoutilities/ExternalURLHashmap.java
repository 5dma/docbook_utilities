package com.paligoutilities;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ExternalURLHashmap  {

    private List<ExternalLinks> externalLinksList;
    private HashMap<URL,Integer> urlIntegerHashMap = new HashMap<>();

    public ExternalURLHashmap(List<ExternalLinks> externalLinksList) {
        this.externalLinksList = externalLinksList;
        externalLinksList.forEach((e)->{
            this.urlIntegerHashMap.put(e.getExternalURL(),0);
        });
    }

    void getStatusCodes(PaligoUtilityView view, String outputFile) {
        ProgressBar progressBar = ((AnalysisProgress) view.getOptions()).getProgressBar();
        progressBar.setProgress(0d);

        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                double counter = 0.0d;

                for (URL url : urlIntegerHashMap.keySet()) {
                    counter++;
                    System.out.println(counter + " " + url.toString());
                    if (!url.toString().contains("localhost")) {
                        try {
                            Thread.sleep(10);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.connect();
                            int code = connection.getResponseCode();
                            urlIntegerHashMap.replace(url, code);
                            System.out.println(url.toString() + " " + code);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                        updateProgress(counter, urlIntegerHashMap.size());
                    }
                }
                return null;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());

        final Thread thread = new Thread(task);
        thread.setDaemon(true);
        task.setOnRunning((succeesesEvent) -> {
          System.out.println("Running");
        });

        task.setOnSucceeded((succeededEvent) -> {
           printExternalLinkReport(view,outputFile);
           progressBar.progressProperty().unbind();
        });
        task.setOnFailed((failedEvent) -> {
            System.out.println(failedEvent.toString());
            progressBar.progressProperty().unbind();
        });
        thread.start();

    }

    void printExternalLinkReport(PaligoUtilityView view, String outputFileName) {
        System.out.println("here");
        String reportTitle = view.listViewUtility.getSelectionModel().getSelectedItem().getLabel();
        String firstPart = "<html>\n" +
                "            <head>\n" +
                "                <title>" + reportTitle + "</title>\n" +
                "                <link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" />\n" +
                "\n" +
                "            </head>\n" +
                "            <body>\n" +
                "                <h1>" + reportTitle + "</h1>\n" +
                "                <table>\n" +
                "                    <thead>\n" +
                "                        <tr>\n" +
                "                            <th class=\"topicid\">ID</th><th class=\"topictitle\">Title</th><th class=\"url\">External URL</th><th class=\"alignright\">Status code</th>\n" +
                "                        </tr>\n" +
                "                    </thead>\n" +
                "                    <tbody>";
        String lastPart = "</tbody>\n" +
                "                </table>\n" +
                "            </body>\n" +
                "        </html>";

        String middlePart = externalLinksList.stream().map((e) -> String.format("<tr><td>%s</td><td><a href=\"%s\">%s</a></td><td>%s</td><td class=\"alignright\">%d</td></tr>"
                , e.getTopicID()
                , e.getTopicURL()
                , e.getTopicTitle()
                , e.getExternalURL()
                , this.urlIntegerHashMap.get(e.getExternalURL()))).collect(Collectors.joining());

        try {
            Files.write(Paths.get(outputFileName), (firstPart + middlePart + lastPart).getBytes());
                Desktop.getDesktop().browse(new URI("file://" + outputFileName));
        } catch (URISyntaxException | IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}