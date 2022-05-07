/**
 * Runs the model
 */

public class Main {

    public static Model model;
    public static void main(String[] args) {
	    // TODO
        model = new Model(30, 30);
        model.setup();
        while (true){
            model.passTurn();
            System.out.println("active agent: " + model.checkSum() + "  jail count: " + model.jailCount);
        }
    }
}
