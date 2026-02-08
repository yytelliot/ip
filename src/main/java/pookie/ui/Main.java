package pookie.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pookie.Pookie;

/**
 * A GUI for Pookie using FXML.
 */
public class Main extends Application {

    private Pookie pookie = new Pookie("data/pookie.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setPookie(pookie);  // inject the Pookie instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
