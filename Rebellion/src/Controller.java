
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.List;

/**
 * Controller for visualisation components
 */
public class Controller {

    // all fields correspond to different parameters in visualisation
    @FXML
    private TextField initCopDensityField;

    @FXML
    private TextField initAgentDensityField;
    @FXML
    private TextField vision;
    @FXML
    private TextField governmentLegitimacy;
    @FXML
    private TextField maxJailTerm;

    @FXML
    private TextField numTurns;
    @FXML
    private Button getAverageButton;
    @FXML
    private Button startButton;


    @FXML
    private LineChart lineChart;

    XYChart.Series passiveAgent;
    XYChart.Series jailAgent;

    XYChart.Series rebelAgent;


    Visualization vis;

    /**
     * setup visualisation with parameters for model
     * @param event
     */
    public void setUp(ActionEvent event){
        try {
            //setting the parameter
            Parameter.setInitialDensityCops(
                    Double.parseDouble(initCopDensityField.getText()));
            Parameter.setInitialDensityAgent(
                    Double.parseDouble(initAgentDensityField.getText()));
            Parameter.setLegitimacy(
                    Double.parseDouble(governmentLegitimacy.getText()));
            Parameter.setVision(
                    Integer.parseInt(vision.getText()));
            Parameter.setMaxJailTerm(
                    Integer.parseInt(maxJailTerm.getText()));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * runs visualisation
     */
    public void run(){

        this.simulate();
        this.setVis(vis.getActive(),vis.getJail(),vis.getPassive());
    }

    /**
     * Averages the results of multiple model executions
     */
    public void setAverageRunning(){
        int repeatTime = 5;

        //generate first sample
        Visualization first = this.simulate();

        List<Integer> activesAva = first.getActive();
        List<Integer> jailsAva = first.getJail();
        List<Integer> passivesAva = first.getPassive();

        //repeat server times
        for (int i =0; i< repeatTime-1;i++){
            Visualization sample = this.simulate();

            for (int j =0; j < activesAva.size();j++){
                activesAva.set(j, activesAva.get(j) +
                        sample.getActive().get(j));
                jailsAva.set(j, jailsAva.get(j) +
                        sample.getJail().get(j));
                passivesAva.set(j, passivesAva.get(j) +
                        sample.getPassive().get(j));
            }

        }

        //get the average number

        for (int k=0;k<activesAva.size(); k++){
            activesAva.set(k,activesAva.get(k)/repeatTime);
            jailsAva.set(k,jailsAva.get(k)/repeatTime);
            passivesAva.set(k,passivesAva.get(k)/repeatTime);
        }

        //set the visualization
        this.setVis(activesAva,jailsAva,passivesAva);
    }

    /**
     * updates the linechart with integers
     * @param actives
     * @param jails
     * @param passives
     */
    public void setVis(List<Integer> actives,
                       List<Integer> jails,
                       List<Integer> passives ){


        for (int i = 0; i< actives.size();i++){
            passiveAgent.getData().add(
                    new XYChart.Data(Integer.toString(i),passives.get(i)));
            jailAgent.getData().add(
                    new XYChart.Data(Integer.toString(i),jails.get(i)));
            rebelAgent.getData().add(
                    new XYChart.Data(Integer.toString(i),actives.get(i)));
        }

        lineChart.getData().add(rebelAgent);
        lineChart.getData().add(jailAgent);
        lineChart.getData().add(passiveAgent);

    }

    /**
     * initialises linechart
     */
    private void initVis(){
        passiveAgent = new XYChart.Series();
        jailAgent= new XYChart.Series();
        rebelAgent = new XYChart.Series();


        jailAgent.setName("jailAgent");
        rebelAgent.setName("rebelAgent");
        passiveAgent.setName("passiveAgent");
    }


    /**
     * runs the model within the visualisation
     * @return
     */
    private Visualization simulate(){

        // initialize the model
        Main.model =  new Model(40, 40);

        Main.model.setup();
        // initialize the line chart
        initVis();

        vis = new Visualization();
        vis.init(Integer.parseInt(numTurns.getText()));
        vis.run();

        return vis;
    }
}
