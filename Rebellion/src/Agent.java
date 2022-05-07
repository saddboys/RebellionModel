/**
 * Represents a single agent on the model board
 */
public class Agent extends Turtle{
    // what the agent is currently doing
    private AgentState state = AgentState.PASSIVE;

    // How the agent views the world contributing to their revolt risk.
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

    /**
     * DAVID
     * Determines whether or not the agent should revolt at this turn. If so, then the agent will be changed to
     * revolting state. This is calculated based on net risk
     */
    public void revoltOrNot(){
        // TODO
    }

    /**
     * Arrests the agent for a specified term
     * @param term the term that the agent is arrested for
     */
    public void arrest(int term){
        this.jailTerm = term;
        this.state = AgentState.IMPRISONED;
    }

    /**
     * The agent spends time in jail, and is released if their term is up
     */
    public void spendTimeInJail(){
        if (this.state == AgentState.IMPRISONED) {
            if (jailTerm == 0) {
                this.state = AgentState.PASSIVE;
            } else {
                this.jailTerm--;
            }
        }
    }
}
