package ca.masseyhacks.servereastereggs.structures;

import ca.masseyhacks.servereastereggs.util.AES;

public class ClaimToken {
    String seed = "";
    String player = "";
    double value = 0.0;
    double locationX = 0.0;
    double locationY = 0.0;
    double locationZ = 0.0;

    public ClaimToken(String seed, String player,  double value, double locationX, double locationY, double locationZ){
        this.seed = seed;
        this.player = player;
        this.value = value;
        this.locationX = locationX;
        this.locationY = locationY;
        this.locationZ = locationZ;
    }

    public String getClaimString(){
        return AES.encrypt(player + "," + value + "," + locationX + "," + locationY + "," + locationZ, seed);
    }
}
