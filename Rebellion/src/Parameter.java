public class Parameter {

    private static double initialDensityAgent = 0.70;

    private static double initialDensityCops = 0.04;

    private static int vision = 7;

    private static double legitimacy = 0.82;

    private static int maxJailTerm = 30;


    public static double getInitialDensityAgent(){
        return initialDensityAgent;
    }

    public static void setInitialDensityAgent(double DensityAgent){
        initialDensityAgent = DensityAgent;
    }

    public static double getInitialDensityCops(){
        return initialDensityCops;
    }

    public static void setInitialDensityCops(double DensityCop){
        initialDensityCops = DensityCop;
    }

    public static int getVision(){
        return vision;
    }

    public static void setVision(int vis){
        vision = vis;
    }

    public static double getLegitimacy(){
        return legitimacy;
    }

    public static void setLegitimacy(double legit){
        legitimacy = legit;
    }

    public static int getMaxJailTerm(){
        return maxJailTerm;
    }

    public static void setMaxJailTerm(int jail){
        maxJailTerm = jail;
    }



}
