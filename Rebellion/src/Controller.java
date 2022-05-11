
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;


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
    private Button setUpButton;
    @FXML
    private Button startButton;

    @FXML
    private Button endButton;

    @FXML
    private Button continueButton;

    double initCopDensity;

    Visualization vis;

    public void setUp(ActionEvent event){
        try {
            initCopDensity = Double.parseDouble(initCopDensityField.getText());
            System.out.println(initCopDensity);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void run(){

        try{
            Main.model.setup();

            vis = new Visualization();
            vis.start();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void stop() throws InterruptedException {
        vis.pause();
    }

    public void resume(){
        vis.getResume();
    }




}
