import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

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

        int[] turtles = model.checkSum();
        int passive = turtles[0];
        int rebelling = turtles[1];
        int imprisoned = turtles[2];
        String data = (0) + "," + passive + "," + rebelling
                + "," + imprisoned + "\n";
        myWriter.append(data);

        for (int i = 0; i < 500; i++) {
            model.passTurn();
            turtles = model.checkSum();
            passive = turtles[0];
            rebelling = turtles[1];
            imprisoned = turtles[2];
            data = (i+1) + "," + passive + "," + rebelling
                    + "," + imprisoned + "\n";
            myWriter.append(data);
        }
        myWriter.close();
    }
    public static void main(String[] args) throws IOException {
        runWithoutVisualisation();
    }
}

