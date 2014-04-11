package model;

import java.util.Random;

/**
 * Created by chinana523 on 4/4/14.
 */
public class Tile {

    TileState state;
    int number;

    public Tile() {
        this.state = TileState.DEAD;
    }

    public Tile(int number) {
        this.number = number;
        this.state = TileState.ALIVE;
    }

    public void init() {
        this.state = TileState.ALIVE;
        Random random = new Random();
        if (random.nextInt(5) < 1) {
            this.number = 4;
        } else {
            this.number = 2;
        }
    }

    public boolean isAlive() {
        return this.state == TileState.ALIVE;
    }

    public boolean isDead() {
        return this.state == TileState.DEAD;
    }

    public int getNumber() { return this.number; }

}
