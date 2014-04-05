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
        for (int r = 0; r < boardLen; r++) {
            for (int c = 0; c < boardLen; c++) {
                board[r][c] = new Tile(r, c);
            }
        }
        boardFull = false;

        // Player starts with 2 tiles
        dropTile();
        dropTile();
    }

    private void dropTile() {
        if (boardFull) return;
        Random random = new Random();
        boolean success = false;
        while (!success) {
            int r = random.nextInt(4);
            int c = random.nextInt(4);
            Tile tile = board[r][c];
            if (tile.state == TileState.DEAD) {
                tile.init();
                if (tile.number > biggest) {
                    biggest = tile.number;
                }
                success = true;
            }
        }
    }

    public void up() {

        dropTile();
    }

    // Algorithm:
    // Starting from the bottom, check for adjacent matches in each column
    // If there's a match, the bottommost tile's number doubles and the other tile collapses
    // All tiles shift as far down as possible
    public void down() {
        for (int c = 0; c < boardLen; c++) {
            int r = boardLen - 1;
            while (r > -1) {
                Tile currentTile = board[r][c];
                Tile adjacentAliveTile;
                int y = r - 1;
                while (y > -1) {
                    if (board[y][c].state == TileState.DEAD) {
                        y--;
                    } else {
                        break;
                    }
                }
                if (y > -1) {
                    adjacentAliveTile = board[y][c];
                } else {
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    adjacentAliveTile.state = TileState.DEAD;
                    r = y - 1;
                } else {
                    r = y;
                }
            }
        }
        dropTile();
    }

    public void left() {

        dropTile();
    }

    public void right() {

        dropTile();
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
