public class Visualization extends Thread{

    private boolean paused = false;

    @Override
    public void run(){

        while (!interrupted()){

            if (paused){
                while(paused){
                    try{
                        sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            Main.model.passTurn();
            System.out.println("active agent: " + Main.model.checkSum() + "  jail count: " + Main.model.jailCount);

        }
    }

    public void pause(){
        paused = true;
    }

    public void getResume(){
        paused = false;
    }

}
