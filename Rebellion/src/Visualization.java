public class Visualization extends Thread{

    @Override
    public void run(){

        while (true){
            Main.model.passTurn();
            System.out.println("active agent: " + Main.model.checkSum() + "  jail count: " + Main.model.jailCount);
        }
    }

}
