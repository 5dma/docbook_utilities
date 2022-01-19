package com.paligoutilities;

import javafx.scene.Node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.Properties;

public class Utilities {
    public String lastResourceFile;
    public String paligoEditorialURL;
    public String lastSearchText;
    public String lastElement;
    public String lastElementAttributeElement;
    public String lastElementAttributeAttribute;
    public Node nodeCurrentlyVisibleOptions;



    private final String PROP_KEY_LAST_SEARCH_TEXT = "lastSearchText";
    private final String PROP_KEY_LAST_ELEMENT = "lastElement";
    private final String PROP_KEY_LAST_RESOURCE_FILE = "lastResourceFile";
    private final String PROP_KEY_PALIGO_EDITORIAL_URL = "paligoEditorialURL";
    private final String PROP_KEY_LAST_ELEMENT_ATTRIBUTE_ELEMENT = "lastElementAttributeElement";
    private final String PROP_KEY_LAST_ELEMENT_ATTRIBUTE_ATTRIBUTE = "lastElementAttributeAttribute";
    private final String PROPERTIES_FILENAME = "app.properties";

    public Utilities() {
        Properties properties = getProperties();
        this.lastResourceFile = properties.getProperty(PROP_KEY_LAST_RESOURCE_FILE);
        this.paligoEditorialURL = properties.getProperty(PROP_KEY_PALIGO_EDITORIAL_URL);
        this.lastElement = properties.getProperty(PROP_KEY_LAST_ELEMENT);
        this.lastSearchText = Normalizer.normalize(properties.getProperty(PROP_KEY_LAST_SEARCH_TEXT), Normalizer.Form.NFC);
        this.lastElementAttributeElement = properties.getProperty(PROP_KEY_LAST_ELEMENT_ATTRIBUTE_ELEMENT);
        this.lastElementAttributeAttribute = properties.getProperty(PROP_KEY_LAST_ELEMENT_ATTRIBUTE_ATTRIBUTE);
        this.nodeCurrentlyVisibleOptions = null;

    }

    public String getLastResourceFile() {
        return this.lastResourceFile;
    }

    public String getLastElement() {
        return lastElement;
    }

    public String getLastSearchText() {return lastSearchText; }

    public String getPaligoEditorialURL() {
        return paligoEditorialURL;
    }

    public String getLastElementAttributeElement() {
        return lastElementAttributeElement;
    }

    public String getLastElementAttributeAttribute() {
        return lastElementAttributeAttribute;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream("app.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + PROPERTIES_FILENAME + "' not found in the classpath");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }

    public void setLastResourceFile(String lastResourceFile) {
        this.lastResourceFile = lastResourceFile;
    }

    public void setLastSearchText(String lastSearchText) {
        this.lastSearchText = lastSearchText;
    }

    void saveProperties() {

        try {
            FileOutputStream out = new FileOutputStream("app.properties");
            Properties prop = new Properties();
            prop.setProperty(PROP_KEY_LAST_SEARCH_TEXT, getLastSearchText());
            prop.setProperty(PROP_KEY_LAST_RESOURCE_FILE, getLastResourceFile());
            prop.setProperty(PROP_KEY_PALIGO_EDITORIAL_URL, getPaligoEditorialURL());
            prop.setProperty(PROP_KEY_LAST_ELEMENT, getLastElement());
            prop.setProperty(PROP_KEY_LAST_ELEMENT_ATTRIBUTE_ELEMENT, getLastElementAttributeElement());
            prop.setProperty(PROP_KEY_LAST_ELEMENT_ATTRIBUTE_ATTRIBUTE,getLastElementAttributeAttribute());
            prop.store(out, null);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public Node getNodeCurrentlyVisibleOptions() {
        return nodeCurrentlyVisibleOptions;
    }

    public void setNodeCurrentlyVisibleOptions(Node nodeCurrentlyVisibleOptions) {
        this.nodeCurrentlyVisibleOptions = nodeCurrentlyVisibleOptions;
    }

    public void setLastElement(String lastElement) {
        this.lastElement = lastElement;
    }

    public void setLastElementAttributeElement(String lastElementAttributeElement) {
        this.lastElementAttributeElement = lastElementAttributeElement;
    }

    public void setLastElementAttributeAttribute(String lastElementAttributeAttribute) {
        this.lastElementAttributeAttribute = lastElementAttributeAttribute;
    }
}
