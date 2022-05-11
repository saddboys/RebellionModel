
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
            vis.interrupt();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void stop() throws InterruptedException {
        vis.interrupt();
        System.out.println("STOP!");
    }




}
