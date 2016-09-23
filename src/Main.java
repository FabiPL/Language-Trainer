import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final double LWT_VERSION = 1.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("lwt.fxml"));
        primaryStage.setTitle("Language Trainer v"+LWT_VERSION);
        primaryStage.getIcons().add(new Image("file:icon.png"));

        primaryStage.setOnCloseRequest(e -> {
            TrainerFct trainer = new TrainerFct();
            primaryStage.setTitle("Saving LWT Import Files...");
            try {
                trainer.load();
                trainer.createImportFiles();
            } catch(IOException ex) {
                System.out.println("I/O error while shutting down.");
            }
        });

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
