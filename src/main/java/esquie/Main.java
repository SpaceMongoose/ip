package esquie;

import java.io.IOException;

import esquie.exceptions.EsquieException;
import esquie.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Esquie using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // 1. Locate the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

            // 2. Load the FXML file into an AnchorPane object
            AnchorPane ap = fxmlLoader.load();

            // 3. Create the Scene (the content inside the window)
            Scene scene = new Scene(ap);

            // 4. Set the Scene to the Stage (the window frame)
            stage.setScene(scene);

            // 5. Give a Title and Icon
            stage.setTitle("Esquie Bot");
            Image image = new Image(this.getClass().getResourceAsStream("/images/e33_icon.jpg"));
            stage.getIcons().add(image);

            // 6. Inject Esquie
            Esquie esquie = new Esquie("./data/esquie.txt");
            fxmlLoader.<MainWindow>getController().setEsquie(esquie);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EsquieException e) {
            e.printStackTrace();
        }
    }
}
