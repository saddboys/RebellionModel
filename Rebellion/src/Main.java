import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {

    public static Model model;

    public static void runWithoutVisualisation() throws IOException {
        File csvOutputFile = new File("output.csv");
        FileWriter myWriter = new FileWriter(csvOutputFile);
        myWriter.write( "Iteration,Passive,Rebelling,Imprisoned\n" );


        model = new Model(40, 40);
        model.setInitialDensityCops(0.04);
        model.setInitialDensityAgent(0.7);
        model.setLegitimacy(0.82);
        model.setMaxJailTerm(30);
        model.setVision(7);
        model.setup();

        for (int i = 0; i < 500; i++) {
            model.passTurn();
            int[] turtles = model.checkSum();
            int passive = turtles[0];
            int rebelling = turtles[1];
            int imprisoned = turtles[2];
            String data = i + "," + passive + "," + rebelling
                    + "," + imprisoned + "\n";
            myWriter.append(data);
        }
        myWriter.close();
    }
    public static void main(String[] args) throws IOException {
        //runWithoutVisualisation();
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

