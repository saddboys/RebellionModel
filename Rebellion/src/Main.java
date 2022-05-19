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
        try {
            readArgs(args);
            runWithoutVisualisation();
            System.out.println("Completed! Output at output.csv");
        } catch (NumberFormatException e) {
            printHelp();
        } catch (IllegalArgumentException e){
            printHelp();
            System.out.println(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void readArgs(String[] args){
        for (int i = 0; i < args.length - 1; i = i + 2){
            String[] param = new String[]{args[i], args[i+1]};
            readParam(param);
        }
    }

    private static void readParam(String[] param){

        switch (param[0]) {
            case "-cops":
                Parameter.setInitialDensityCops(Double.parseDouble(param[1]));
                break;
            case "-agents":
                Parameter.setInitialDensityAgent(Double.parseDouble(param[1]));
                break;
            case "-legit":
                Parameter.setLegitimacy(Double.parseDouble(param[1]));
                break;
            case "-jail":
                Parameter.setMaxJailTerm(Integer.parseInt(param[1]));
                break;
            case "-vision":
                Parameter.setVision(Integer.parseInt(param[1]));
                break;
            case "-h":
                printHelp();
                break;
        }
    }

    public static void printHelp(){
        System.out.println(
                """
                    Parameters must be in the following format:\s
                    -cops <double> : Density of Cops in map, default 0.04
                    -agents <double> : Density of Agents in map, default 0.7
                    -legit <double> : Government legitimacy, default 0.82
                    -jail <int> : Max jail term for agents, default 30
                    -vision <int> : Vision for all agents, default 7
                    
                    Separate all inputs with a empty space
                    cops + agents must be <= 1.0!
                    
                    e.g. Java Main -cops 0.05 -legit 0.7 -jail 30  -vision 3
                    
                    """
        );
    }
}

