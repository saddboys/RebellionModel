public class Agent extends Turtle{

    private AgentState state = AgentState.PASSIVE;
    private double perceivedHardship;

    // grievance = perceived-hardship * (1 - government-legitimacy)
    private double grievance;

    // The agent's tolerance for risk of revolt
    private double riskAversion;

    // The agent’s estimated arrest probability based on the police in vision range.
    private double arrestProbability;

    //the risk of revolt for the agent based on risk aversion, and the arrest probability of the agent.
    private double netRisk;

    private int jailTerm;

    public Agent(int vision, int x, int y,
                 double perceivedHardship) {
        super(vision, x, y);
        this.perceivedHardship = perceivedHardship;

    }

    public void revoltOrNot(){

    }

    public void arrest(int term){
        this.jailTerm = term;
        this.state = AgentState.IMPRISONED;
    }

    public void spendTimeInJail(){
        if (jailTerm == 0){
            this.state = AgentState.PASSIVE;
        } else {
            this.jailTerm--;
        }
    }
}
