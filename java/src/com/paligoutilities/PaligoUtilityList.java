package com.paligoutilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

public class PaligoUtilityList  {

    ObservableList<PaligoUtility> data = FXCollections.observableArrayList();

    final static String externalLinkFilename = "outputs/external_links.html";
    final static String acronymFilename = "outputs/empty_acronyms.html";
    final static String textSearch = "outputs/find_text.html";
    final static String elementSearch = "outputs/find_element.html";
    final static String elementAttributeSearch = "outputs/find_element_attribute.html";


    public PaligoUtilityList(Utilities utilities) {

        this.data.add(new PaligoUtility(UtilityId.WELCOME,"Welcome",
                "Welcome to Paligo Utilities.\n\nThese utilities can help detect errors and optimize your Paligo project.",
                null,null,NoOptionsText.class,utilities));
        this.data.add(new PaligoUtility(UtilityId.EXTERNAL_LINKS, "External links",
                "Lists links to external web sites and checks HTTP status code. Does not check status code for links to localhost.",
                "external_links.xsl",
                externalLinkFilename,AnalysisProgress.class,utilities));
        this.data.add(new PaligoUtility(UtilityId.EMPTY_ACRONYMS, "Empty acronyms",
                "Lists glossary entries with empty acronym elements.",
                "empty_glossary_acronyms.xsl",
                acronymFilename,null,utilities));
        this.data.add(new PaligoUtility(UtilityId.FIND_TEXT, "Find text",
                "Searches topics for text.",
                "find_text_usage.xsl",
                textSearch,FindTextOptions.class,utilities));
        this.data.add(new PaligoUtility(UtilityId.LIST_ELEMENT, "List element",
                "Lists the text associated with a DocBook element.",
                "find_element.xsl",
                elementSearch,FindElement.class,utilities));
        this.data.add(new PaligoUtility(UtilityId.LIST_ELEMENT_ATTRIBUTE, "List element-attribute combination",
                "Lists the text associated with an element-attribute combination.",
                "find_element_attribute.xsl",
                elementAttributeSearch,FindElementAttribute.class,utilities));

    }
}
