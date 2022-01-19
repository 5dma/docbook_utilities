package com.paligoutilities;

import java.net.MalformedURLException;
import java.net.URL;

public class ExternalLinks {
    private String topicID;
    private String topicTitle;
    private URL topicURL;
    private URL externalURL;

    public ExternalLinks(String delimitedString) {
        String[] components = delimitedString.split("\t");
        this.topicID = components[0];
        this.topicTitle = components[2];
        try {
            this.topicURL = new URL(components[1]);
            this.externalURL = new URL(components[3]);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    public URL getTopicURL() {
        return topicURL;
    }

    public String getTopicID() {
        return topicID;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public URL getExternalURL() {
        return externalURL;
    }

   }
