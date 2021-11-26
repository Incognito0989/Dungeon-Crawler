package src.main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the InitialConfigScreen and Welcome screen.
 */
public class WelcomeScreenController {

    @FXML private Button startGame;

    /**
     * Event for the Welcome screen start button to go to next screen.
     * @param actionEvent event when startButton is clicked.
     * @throws IOException file exception if the screen .fxml file is not found.
     */
    public void clickStart(ActionEvent actionEvent) throws IOException {
        System.out.print("clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/"
                + "screens/initialConfigScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
