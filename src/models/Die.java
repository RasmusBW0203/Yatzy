package models;

import java.util.Random;

public class Die {
    private int eyes = 0;
    private final Random random = new Random();


    public Die(int eyes) {
        this.eyes = eyes;
    }
    public Die() {

    }



    public void roll() {
        this.eyes = this.random.nextInt(5) + 1;
    }

    public int getEyes() {
        return eyes;
    }
}
