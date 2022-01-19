package com.paligoutilities;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;

public class PaligoUtility {
    private UtilityId id;
    private String label;
    private String description;
    private String transformFile;
    private String outputFile;
    private Node nodeOptions;

    public PaligoUtility(UtilityId id, String label,
                         String description,
                         String transformFile,
                         String outputFile,
                         Class optionsClass,
                         Utilities utilities) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.transformFile = transformFile;
        this.outputFile = outputFile;

        if (optionsClass == null) {
            this.nodeOptions = new VBox();
        } else {
            switch (optionsClass.getCanonicalName()) {
                case "com.paligoutilities.NoOptionsText":
                    this.nodeOptions = new VBox();
                    break;
                case "com.paligoutilities.AnalysisProgress":
                    try {
                        this.nodeOptions = (Node) optionsClass.getConstructor(null).newInstance();
                    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                        System.out.println(ex.getMessage());
                        System.exit(-1);
                    }
                    break;
                case "com.paligoutilities.FindTextOptions":
                case "com.paligoutilities.FindElement":
                case "com.paligoutilities.FindElementAttribute":
                    try {
                        Class[] paramTypes = {Utilities.class};
                        this.nodeOptions = (Node) optionsClass.getConstructor(paramTypes).newInstance(utilities);
                    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                        System.out.println(ex.getMessage());
                        System.exit(-1);
                    }
                    break;
                    default:
                    this.nodeOptions = new VBox();
                    break;
            }
        }

    }

    public UtilityId getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getTransformFile() {
        return transformFile;
    }

    public String getOutputFile() {
        return currentDirectory() + outputFile;
    }

    private String currentDirectory() {
        String pathToJar = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return pathToJar.substring(0, pathToJar.lastIndexOf("/") + 1);
    }

    public Node getOptions() {
        return nodeOptions;
    }
}
