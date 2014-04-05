package model;

import java.util.Random;

/**
 * Created by chinana523 on 4/4/14.
 */
public class GameModel {

    int goal;
    public int boardLen;
    public Tile[][] board;
    boolean boardFull;
    int biggest;

    public GameModel(int n) {
        this.goal = n;
        if (n == 2048) {
            boardLen = 4;
        }
        board = new Tile[boardLen][boardLen];
        for (int i = 0; i < boardLen; i++) {
            for (int j = 0; j < boardLen; j++) {
                board[i][j] = new Tile(i, j);
            }
        }
        boardFull = false;

        // Player starts with 2 tiles
        dropTile();
        dropTile();
    }

    // Precondition: boardFull is false
    private void dropTile() {
        Random random = new Random();
        boolean success = false;
        while (!success) {
            int r = random.nextInt(4);
            int c = random.nextInt(4);
            Tile tile = board[r][c];
            if (tile.state == TileState.EMPTY) {
                tile.init();
                if (tile.number > biggest) {
                    biggest = tile.number;
                }
                success = true;
            }
        }
    }

    public void up() {

    }

    public void down() {
        
    }

    public void left() {

    }

    public void right() {

    }

    // For debugging purposes
    public void printBoard() {
        for (int i = 0; i < boardLen; i++) {
            for (int j = 0; j < boardLen; j++) {
                System.out.print("[");
                Tile tile = board[i][j];
                if (tile.state == TileState.ALIVE) {
                    System.out.print(tile.number + getPadding(tile.number));
                } else {
                    System.out.print(getPadding(0));
                }
                System.out.print("]");
            }
            System.out.println();
        }
    }

    private String getPadding(int n) {
        String padding = "";
        if (n == 0) {
            for (int i = 0; i < String.valueOf(biggest).length(); i++) {
                padding += " ";
            }
        } else {
            for (int i = 0; i < String.valueOf(biggest).length() - String.valueOf(n).length(); i++) {
                padding += " ";
            }
        }
        return padding;
    }

}
