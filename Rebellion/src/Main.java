import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Model model;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize(primaryStage);
    }


    public void initialize(Stage primaryStage) throws IOException {



        // load the layout from the fxml file
        FXMLLoader loader = new FXMLLoader(
                (getClass().getResource("layoutfx.fxml")));
        Parent root = loader.load();

        primaryStage.setTitle("Rebellion");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

