package com.paligoutilities;
/* Run command

Change to installation directory.
java -jar paligo_utilities.jar

INSTALLPATH=/Users/mlautman/Documents/programming/java/paligo_utilities/out/artifacts/paligo_utilities_jar
java -classpath $INSTALLPATH/javafx.base.jar:$INSTALLPATH/paligo_utilities.jar:$INSTALLPATH/saxon-he-10.2.jar com.paligoutilities.Main

Where /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/bin/java -version is
java version "1.8.0_111"
Java(TM) SE Runtime Environment (build 1.8.0_111-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.111-b14, mixed mode)

https://www.java.com/en/download/manual.jsp
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * To add a utility:
 * <ol>
 *     <li>Add it to the constructor {@link PaligoUtilityList#PaligoUtilityList(Utilities)}.</li>
 *     <li>Create a new class that extends Gridpane. See an example at {@link FindElement}.</li>
 *     <li>Add a case to the switch statement in the constructur {@link PaligoUtility}.</li>
 *     <li>Add an output file to the list of final properties in {@link PaligoUtilityList}.</li>
 *     <li>Add a case to the switch statement in {@link Transform#transform(int, PaligoUtilityView, Utilities)}.</li>
 *     <li>Update the saved .properties file, getters, and setters in {@link Utilities}.</li>
 *     <li>Put dummy values in {@code app.properties}.</li>
 *     <li>Save the user's last selections in {@link PaligoUtilityPresenter}, {@code view.btnRun.setOnAction}.</li>
 * </ol>
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Utilities utilities = new Utilities();
        PaligoUtilityList model = new PaligoUtilityList(utilities);

        PaligoUtilityView view = new PaligoUtilityView(model, utilities);

        PaligoUtilityPresenter presenter = new PaligoUtilityPresenter(model, view, utilities);
        Scene scene = new Scene(view);
        primaryStage.setTitle("Paligo Utilities");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
