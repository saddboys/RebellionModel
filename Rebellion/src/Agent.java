import java.util.List;

/**
 * Represents a single agent on the model board
 */
public class Agent extends Turtle{
    // what the agent is currently doing
    private AgentState state = AgentState.PASSIVE;

    // How the agent views the world contributing to their revolt risk.
    private double perceivedHardship;

    // The agent's tolerance for risk of revolt
    private double riskAversion;

    private int jailTerm;

    private double revoltThreshold;

    public Agent(int vision, int x, int y,
                 double riskAversion,
                 double perceivedHardship,
                 double revoltThreshold) {
        super(vision, x, y);
        this.riskAversion = riskAversion;
        this.perceivedHardship = perceivedHardship;
        this.revoltThreshold = revoltThreshold;
    }

    /**
     * Determines whether or not the agent should revolt at this turn.
     * If so, then the agent will be changed to
     * revolting state. This is calculated based on net risk
     */
    public void revoltOrNot() {
        Turtle[][] map = Main.model.getTurtleMap();
        double numCops = 0;
        double numRebels = 1; // netlogo model has base number as 1

        List<int[]> visionPatches = getVision(map);
        for (int[] location : visionPatches){
            Turtle currentTurtle = map[location[0]][location[1]];
            // find number of police and rebels in vision range
            if (currentTurtle instanceof Police) {
                numCops = numCops + 1;
            }
            if (currentTurtle instanceof Agent &&
                    ((Agent) currentTurtle).state
                            .equals(AgentState.REBELLING)) {
                numRebels = numRebels + 1;
            }
        }
        // The agent’s estimated arrest probability based on
        // the police in vision range.
        double arrestProbability = 1 - Math.exp((-Model.K_ARREST) *
                Math.floor(numCops / numRebels));
        //the risk of revolt for the agent based on risk aversion,
        // and the arrest probability of the agent.
        double netRisk = riskAversion * arrestProbability;
        // grievance = perceived-hardship * (1 - government-legitimacy)
        double grievance = perceivedHardship *
                (1 - Main.model.getGovt().getLegitimacy());
        // rebel if values are over threshold
        if ((grievance - netRisk) > revoltThreshold){
            Main.model.addNewRebels(this);
        }
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
                Main.model.jailCount -= 1;
            } else {
                this.jailTerm--;
            }
        }
    }

    /**
     * gets the agent state (rebel, passive, imprisoned)
     * @return
     */
    public AgentState getState(){
        return this.state;
    }

    /**
     * sets the agent state  (rebel, passive, imprisoned)
     * @param state
     */
    public void setState(AgentState state) {
        this.state = state;
    }


}
