package model;

import java.util.Random;

/**
 * Created by chinana523 on 4/4/14.
 */
public class GameModel {

    int goal;
    int boardLen;
    Tile[][] board;
    int biggest;
    boolean hasWon;
    boolean hasLost;

    public GameModel(int n) {
        this.goal = n;
        if (n == 2048) {
            boardLen = 4;
        }
        board = new Tile[boardLen][boardLen];
        for (int r = 0; r < boardLen; r++) {
            for (int c = 0; c < boardLen; c++) {
                board[r][c] = new Tile();
            }
        }
        hasWon = false;
        hasLost = false;

        // Player starts with 2 tiles
        dropTile();
        dropTile();
    }

    // For debugging
    public GameModel(int n, boolean debug) {
        this.goal = n;
        if (n == 2048) {
            boardLen = 4;
        }
        board = new Tile[boardLen][boardLen];
        for (int r = 0; r < boardLen; r++) {
            for (int c = 0; c < boardLen; c++) {
                board[r][c] = new Tile();
            }
        }
        hasWon = false;
        hasLost = false;

        if (!debug) {
            dropTile();
            dropTile();
        }
    }

    private void dropTile() {
        if (boardIsFull() && biggest != 2048) {
            System.out.println("YOU LOST!");
            hasLost = true;
            return;
        }
        Random random = new Random();
        boolean success = false;
        while (!success) {
            int r = random.nextInt(4);
            int c = random.nextInt(4);
            Tile tile = board[r][c];
            if (tile.isDead()) {
                tile.init();
                if (tile.number > biggest) {
                    biggest = tile.number;
                }
                success = true;
            }
        }
    }

    private boolean boardIsFull() {
        for (int r = 0; r < boardLen; r++) {
            for (int c = 0; c < boardLen; c++) {
                if (board[r][c].isDead())
                    return false;
            }
        }
        return true;
    }

    // For debugging purposes
    public void dropTile(int r, int c, int n) {
        Tile tile = new Tile(n);
        board[r][c] = tile;
    }

    public void up() {
        boolean movePossible = false;
        for (int c = 0; c < boardLen; c++) {
            int r = 0;
            int highestR = 0;
            while (r < boardLen) {
                Tile currentTile = board[r][c];
                if (currentTile.isDead()) {
                    r++;
                    continue;
                }
                Tile adjacentAliveTile;
                int adjacentR = r + 1;
                while (adjacentR < boardLen) {
                    if (board[adjacentR][c].isDead()) {
                        adjacentR++;
                    } else {
                        break;
                    }
                }
                if (adjacentR < boardLen) {
                    adjacentAliveTile = board[adjacentR][c];
                } else {
                    if (r > highestR) {
                        board[highestR][c] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    if (r > highestR) {
                        board[highestR][c] = new Tile(currentTile.number);
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    board[adjacentR][c].state = TileState.DEAD;
                    highestR++;
                    r = adjacentR + 1;
                } else {
                    if (r > highestR) {
                        board[highestR][c] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    highestR++;
                    r = adjacentR;
                }
            }
        }
        if (movePossible) {
            dropTile();
        }
    }

    // Algorithm:
    // Starting from the bottom, check for adjacent matches in each column
    // If there's a match, the bottommost tile's number doubles and the other tile collapses
    // All tiles shift as far down as possible
    public void down() {
        boolean movePossible = false;
        for (int c = 0; c < boardLen; c++) {
            int r = boardLen - 1;
            int lowestR = boardLen - 1;
            while (r > -1) {
                Tile currentTile = board[r][c];
                if (currentTile.isDead()) {
                    r--;
                    continue;
                }
                Tile adjacentAliveTile;
                int adjacentR = r - 1;
                while (adjacentR > -1) {
                    if (board[adjacentR][c].isDead()) {
                        adjacentR--;
                    } else {
                        break;
                    }
                }
                if (adjacentR > -1) {
                    adjacentAliveTile = board[adjacentR][c];
                } else {
                    if (r < lowestR) {
                        board[lowestR][c] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    if (r < lowestR) {
                        board[lowestR][c] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    board[adjacentR][c].state = TileState.DEAD;
                    lowestR--;
                    r = adjacentR - 1;
                } else {
                    if (r < lowestR) {
                        board[lowestR][c] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    lowestR--;
                    r = adjacentR;
                }
            }
        }
        if (movePossible) {
            dropTile();
        } 
    }

    public void left() {
        boolean movePossible = false;
        for (int r = 0; r < boardLen; r++) {
            int c = 0;
            int leftmostC = 0;
            while (c < boardLen) {
                Tile currentTile = board[r][c];
                if (currentTile.isDead()) {
                    c++;
                    continue;
                }
                Tile adjacentAliveTile;
                int adjacentC = c + 1;
                while (adjacentC < boardLen) {
                    if (board[r][adjacentC].isDead()) {
                        adjacentC++;
                    } else {
                        break;
                    }
                }
                if (adjacentC < boardLen) {
                    adjacentAliveTile = board[r][adjacentC];
                } else {
                    if (c > leftmostC) {
                        board[r][leftmostC] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    if (c > leftmostC) {
                        board[r][leftmostC] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    board[r][adjacentC].state = TileState.DEAD;
                    leftmostC++;
                    c = adjacentC + 1;
                } else {
                    if (c > leftmostC) {
                        board[r][leftmostC] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    leftmostC++;
                    c = adjacentC;
                }
            }
        }
        if (movePossible) {
            dropTile();
        }
    }

    public void right() {
        boolean movePossible = false;
        for (int r = 0; r < boardLen; r++) {
            int c = boardLen - 1;
            int rightmostC = boardLen - 1;
            while (c > -1) {
                Tile currentTile = board[r][c];
                if (currentTile.isDead()) {
                    c--;
                    continue;
                }
                Tile adjacentAliveTile;
                int adjacentC = c - 1;
                while (adjacentC > -1) {
                    if (board[r][adjacentC].isDead()) {
                        adjacentC--;
                    } else {
                        break;
                    }
                }
                if (adjacentC > -1) {
                    adjacentAliveTile = board[r][adjacentC];
                } else {
                    if (c < rightmostC) {
                        board[r][rightmostC] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    if (c < rightmostC) {
                        board[r][rightmostC] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    board[r][adjacentC].state = TileState.DEAD;
                    rightmostC--;
                    c = adjacentC - 1;
                } else {
                    if (c < rightmostC) {
                        board[r][rightmostC] = new Tile(currentTile.getNumber());
                        board[r][c].state = TileState.DEAD;
                        movePossible = true;
                    }
                    rightmostC--;
                    c = adjacentC;
                }
            }
        }
        if (movePossible) {
            dropTile();
        }
    }

    // For debugging purposes
    public void printBoard() {
        for (int r = 0; r < boardLen; r++) {
            for (int c = 0; c < boardLen; c++) {
                System.out.print("[");
                Tile tile = board[r][c];
                if (tile.isAlive()) {
                    System.out.print(tile.number + getPadding(tile.number));
                } else {
                    System.out.print(getPadding(0));
                }
                System.out.print("]");
            }
            System.out.println();
        }
        System.out.println();
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

    public Tile getTile(int r, int c) {
        return board[r][c];
    }

    public int getBoardLen() {
        return boardLen;
    }
}
