package esquie.ui;

import esquie.Esquie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Esquie esquie;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/versoProfile.png"));
    private Image esquieImage = new Image(this.getClass().getResourceAsStream("/images/esquieProfile.png"));

    /**
     * Initializes the program
     */
    @FXML
    public void initialize() {
        // This method runs automatically when the window starts.
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        String welcomeText = "Bonjour mon ami! I'm Esquie\uD83D\uDE00 " + "\nWhat can I do for you?";
        dialogContainer.getChildren().add(
                DialogBox.getEsquieDialog(welcomeText, esquieImage)
        );
    }

    /** Injects the Esquie instance */
    public void setEsquie(Esquie e) {
        this.esquie = e;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Esquie's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = esquie.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getEsquieDialog(response, esquieImage)
        );
        userInput.clear();
    }
}
