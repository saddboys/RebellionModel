import java.util.ArrayList;
import java.util.List;

public class Visualization{

    private boolean paused = false;

    private List<Integer> passiveAgents;
    private List<Integer> jailAgents;

    private int turn = 0;


    public void run(){

        int i = 0;
        while (i <turn){

            i+=1;
            Main.model.passTurn();
            int[] res = Main.model.checkSum();
            System.out.println("passive agents: " + res[0] + " rebel agents: " + res[1] + "  jailed agents: " + Main.model.jailCount);

            passiveAgents.add(res[0]);
            jailAgents.add(Main.model.jailCount);

        }


    }

    public void init(int numturn){
        passiveAgents = new ArrayList<>();
        jailAgents = new ArrayList<>();
        this.turn = numturn;

    }

    public List<Integer> getActive(){
        return passiveAgents;
    }

    public List<Integer> getJail(){
        return jailAgents;
    }



}
