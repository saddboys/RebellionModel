
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.List;


public class Controller {

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
    private Button setUpButton;
    @FXML
    private Button startButton;


    @FXML
    private LineChart lineChart;

    XYChart.Series activeAgent;
    XYChart.Series jailAgent;


    Visualization vis;

    public void setUp(ActionEvent event){
        try {
            //setting the parameter
            Parameter.setInitialDensityCops(Double.parseDouble(initCopDensityField.getText()));
            Parameter.setInitialDensityAgent(Double.parseDouble(initAgentDensityField.getText()));
            Parameter.setLegitimacy(Double.parseDouble(governmentLegitimacy.getText()));
            Parameter.setVision(Integer.parseInt(vision.getText()));
            Parameter.setMaxJailTerm(Integer.parseInt(maxJailTerm.getText()));

            // initialize the model
            Main.model =  new Model(30, 30);
            Main.model.setup();
            // initialize the line chart
            initVis();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void run(){

        vis = new Visualization();
        vis.init(Integer.parseInt(numTurns.getText()));
        vis.run();
        setVis();

    }

    public void setVis(){

        List<Integer> actives = vis.getActive();
        List<Integer> jails = vis.getJail();

        for (int i = 0; i< actives.size();i++){
            activeAgent.getData().add(new XYChart.Data(Integer.toString(i),actives.get(i)));
            jailAgent.getData().add(new XYChart.Data(Integer.toString(i),jails.get(i)));
        }

        lineChart.getData().add(activeAgent);
        lineChart.getData().add(jailAgent);

    }

    private void initVis(){
        activeAgent = new XYChart.Series();
        jailAgent= new XYChart.Series();

        activeAgent.setName("activeAgent");
        jailAgent.setName("jailAgent");
    }




}
