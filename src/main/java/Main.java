package src.main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    /**
     * Main method to start application.
     * @param primaryStage the stage everything is set on.
     * @throws IOException file exception if welcome screen .fxml file is not found.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/"
                + "screens/welcome.fxml"));
        primaryStage.setTitle("Dungeon Crawler");
        primaryStage.setScene(new Scene(root, 700, 600));
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * main method.
     * @param args any argument.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
