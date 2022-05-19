/**
 * Representing the government which the cops and agents belong to
 */
public class Government {

    // government legitimacy for rebel calculations
    private double legitimacy;

    public Government(double legitimacy){
        this.legitimacy = legitimacy;
    }

    /**
     * returns govt legitimacy
     * @return
     */
    public double getLegitimacy() {
        return legitimacy;
    }
}
