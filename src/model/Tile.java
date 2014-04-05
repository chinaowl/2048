package model;

import java.util.Random;

/**
 * Created by chinana523 on 4/4/14.
 */
public class Tile {

    TileState state;
    public int number;
    int r;
    int c;

    public Tile(int r, int c) {
        this.r = r;
        this.r = c;
        this.state = TileState.EMPTY;
    }

    public void init() {
        this.state = TileState.ALIVE;
        Random random = new Random();
        if (random.nextInt(5) < 2) {
            this.number = 4;
        } else {
            this.number = 2;
        }
    }

}
