import java.util.ArrayList;
import java.util.List;

/**
 * Class for handling visualisation running and initialisation
 */
public class Visualization{

    private boolean paused = false;

    // lists of all agents of interest
    private List<Integer> passiveAgents;
    private List<Integer> jailAgents;

    private List<Integer> rebelAgents;

    // the time that it is currently
    private int turn = 0;


    /**
     * run visualisation
     */
    public void run(){

        int i = 0;
        while (i <turn){

            i+=1;
            Main.model.passTurn();
            int[] res = Main.model.checkSum();

            passiveAgents.add(res[0]);
            rebelAgents.add(res[1]);
            jailAgents.add(Main.model.jailCount);

        }
    }

    /**
     * initialise visualisation with empty lists
     * @param numturn
     */
    public void init(int numturn){
        passiveAgents = new ArrayList<>();
        jailAgents = new ArrayList<>();
        rebelAgents = new ArrayList<>();
        this.turn = numturn;

    }

    public List<Integer> getActive(){
        return rebelAgents;
    }

    public List<Integer> getJail(){
        return jailAgents;
    }

    public List<Integer> getPassive(){ return passiveAgents;}



}
