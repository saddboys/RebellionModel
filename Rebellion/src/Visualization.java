import java.util.ArrayList;
import java.util.List;

public class Visualization{

    private boolean paused = false;

    private List<Integer> activeAgents;
    private List<Integer> jailAgents;

    private int turn = 0;


    public void run(){

        int i = 0;
        while (i <turn){

            i+=1;
            Main.model.passTurn();
            System.out.println("active agent: " + Main.model.checkSum() + "  jail count: " + Main.model.jailCount);

            activeAgents.add(Main.model.checkSum());
            jailAgents.add(Main.model.jailCount);

        }


    }

    public void init(int numturn){
        activeAgents = new ArrayList<>();
        jailAgents = new ArrayList<>();
        this.turn = numturn;

    }

    public List<Integer> getActive(){
        return activeAgents;
    }

    public List<Integer> getJail(){
        return jailAgents;
    }



}
