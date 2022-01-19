package com.paligoutilities;

import javafx.scene.control.Alert;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.RawDestination;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.Xslt30Transformer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Transform {
    private String stylesheet;
    private String xmlSource;
    private String outputFile;

    public Transform(String stylesheet, String xmlSource, String outputFile) {
        this.stylesheet = stylesheet;
        this.xmlSource = xmlSource;
        this.outputFile = outputFile;
    }

    void transform(UtilityId utilityId, PaligoUtilityView view, Utilities utilities) {
        try {
            Processor processor = new Processor(false);
            XsltCompiler compiler = processor.newXsltCompiler();
            /* Get target search text for utility 3 */
            compiler.setParameter(new QName("editorial_url"), new XdmAtomicValue(utilities.getPaligoEditorialURL()));
            compiler.setParameter(new QName("report_title"), new XdmAtomicValue(view.listViewUtility.getSelectionModel().getSelectedItem().getLabel()));

            switch (utilityId) {
                case FIND_TEXT:
                    String searchText = ((FindTextOptions) view.getOptions()).getTfSearchText();
                    compiler.setParameter(new QName("target"), new XdmAtomicValue(searchText));
                    break;
                case LIST_ELEMENT:
                    String searchElement = ((FindElement) view.getOptions()).getSearchText();
                    compiler.setParameter(new QName("target"), new XdmAtomicValue(searchElement));
                    break;
                case LIST_ELEMENT_ATTRIBUTE:
                    String element = ((FindElementAttribute) view.getOptions()).getTargetElement();
                    String attribute = ((FindElementAttribute) view.getOptions()).getTargetAttribute();
                    compiler.setParameter(new QName("element"), new XdmAtomicValue(element));
                    compiler.setParameter(new QName("attribute"), new XdmAtomicValue(attribute));
                    break;
            }

            InputStream stylesheetInputStream = getClass().getClassLoader().getResourceAsStream(this.stylesheet);
            File resourceFile = new File(this.xmlSource);
            StreamSource resourceStreamSource = new StreamSource(resourceFile);
            XsltExecutable stylesheet = compiler.compile(new StreamSource(stylesheetInputStream));

            if (utilityId == UtilityId.EXTERNAL_LINKS) {
                /* Processing for the external links report */
                RawDestination rawDestination = new RawDestination();
                Xslt30Transformer transformer = stylesheet.load30();
                transformer.transform(resourceStreamSource, rawDestination);
                List<ExternalLinks> externalLinksList  = rawDestination
                        .getXdmValue()
                        .stream()
                        .map(e -> new ExternalLinks(e.getStringValue()))
                        .collect(Collectors.toList());

                ExternalURLHashmap externalURLHashMap = new ExternalURLHashmap(externalLinksList);
                externalURLHashMap.getStatusCodes(view, this.outputFile);

            } else {
                Serializer out = processor.newSerializer(new File(this.outputFile));
                out.setOutputProperty(Serializer.Property.METHOD, "html");
                out.setOutputProperty(Serializer.Property.INDENT, "yes");
                Xslt30Transformer transformer = stylesheet.load30();
                transformer.transform(resourceStreamSource, out);
            }
        } catch (SaxonApiException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());

            if (e.getMessage().contains(".xsl")) {
                Path p = Paths.get(this.stylesheet);
                String stylesheetFileName = p.getFileName().toString();
                alert.setHeaderText("No such stylesheet");
                alert.setContentText(String.format("The stylesheet %s does not exist.",stylesheetFileName));
            }
            if (e.getMessage().contains(".xml")) {
                Path p = Paths.get(this.xmlSource);
                String resourceFileName = p.getFileName().toString();
                alert.setHeaderText("No such resource file");
                alert.setContentText(String.format("The resource file %s does not exist.",resourceFileName));
            }
            alert.showAndWait();
           System.out.println(e.getMessage());
        }
    }
}
