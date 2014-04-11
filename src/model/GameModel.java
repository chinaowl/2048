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

    public void dropTile() {
        checkHasLost();
        Random random = new Random();
        boolean success = false;
        while (!success) {
            int r = random.nextInt(4);
            int c = random.nextInt(4);
            Tile tile = board[r][c];
            if (tile.isDead()) {
                tile.init();
                checkBiggest(tile.number);
                success = true;
            }
        }
        checkHasLost();
    }

    // For debugging purposes
    public void dropTile(int r, int c, int n) {
        Tile tile = new Tile(n);
        board[r][c] = tile;
    }

    public boolean isMovePossible() {
        if (!boardIsFull())
            return false;
        for (int r = 0; r < boardLen; r++) {
            for (int c = 0; c < boardLen - 1; c++) {
                if (board[r][c].number == board[r][c+1].number)
                    return true;
            }
        }
        for (int c = 0; c < boardLen; c++) {
            for (int r = 0; r < boardLen - 1; r++) {
                if (board[r][c].number == board[r+1][c].number)
                    return true;
            }
        }
        return false;
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

    private void checkHasLost() {
        if (boardIsFull() && !isMovePossible()) {
            hasLost = true;
            return;
        }
    }

    private void checkBiggest(int number) {
        if (number > biggest)
            biggest = number;
        if (biggest == goal)
            hasWon = true;
    }

    private void moveTile(int oldR, int newR, int oldC, int newC, int number) {
        board[newR][newC] = new Tile(number);
        board[oldR][oldC].state = TileState.DEAD;
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
                int adjacentR = findAdjacentUp(r, c);
                if (adjacentR < boardLen) {
                    adjacentAliveTile = board[adjacentR][c];
                } else {
                    if (r > highestR) {
                        moveTile(r, highestR, c, c, currentTile.number);
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    checkBiggest(currentTile.number);
                    if (r > highestR) {
                        moveTile(r, highestR, c, c, currentTile.number);
                    }
                    movePossible = true;
                    board[adjacentR][c].state = TileState.DEAD;
                    highestR++;
                    r = adjacentR + 1;
                } else {
                    if (r > highestR) {
                        moveTile(r, highestR, c, c, currentTile.number);
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

    private int findAdjacentUp(int r, int c) {
        int adjacentR = r + 1;
        while (adjacentR < boardLen) {
            if (board[adjacentR][c].isDead()) {
                adjacentR++;
            } else {
                return adjacentR;
            }
        }
        return adjacentR;
    }

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
                int adjacentR = findAdjacentDown(r, c);
                if (adjacentR > -1) {
                    adjacentAliveTile = board[adjacentR][c];
                } else {
                    if (r < lowestR) {
                        moveTile(r, lowestR, c, c, currentTile.number);
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    checkBiggest(currentTile.number);
                    if (r < lowestR) {
                        moveTile(r, lowestR, c, c, currentTile.number);
                    }
                    movePossible = true;
                    board[adjacentR][c].state = TileState.DEAD;
                    lowestR--;
                    r = adjacentR - 1;
                } else {
                    if (r < lowestR) {
                        moveTile(r, lowestR, c, c, currentTile.number);
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

    private int findAdjacentDown(int r, int c) {
        int adjacentR = r - 1;
        while (adjacentR > -1) {
            if (board[adjacentR][c].isDead()) {
                adjacentR--;
            } else {
                return adjacentR;
            }
        }
        return adjacentR;
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
                int adjacentC = findAdjacentLeft(r, c);
                if (adjacentC < boardLen) {
                    adjacentAliveTile = board[r][adjacentC];
                } else {
                    if (c > leftmostC) {
                        moveTile(r, r, c, leftmostC, currentTile.number);
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    checkBiggest(currentTile.number);
                    if (c > leftmostC) {
                        moveTile(r, r, c, leftmostC, currentTile.number);
                    }
                    movePossible = true;
                    board[r][adjacentC].state = TileState.DEAD;
                    leftmostC++;
                    c = adjacentC + 1;
                } else {
                    if (c > leftmostC) {
                        moveTile(r, r, c, leftmostC, currentTile.number);
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

    private int findAdjacentLeft(int r, int c) {
        int adjacentC = c + 1;
        while (adjacentC < boardLen) {
            if (board[r][adjacentC].isDead()) {
                adjacentC++;
            } else {
                return adjacentC;
            }
        }
        return adjacentC;
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
                int adjacentC = findAdjacentRight(r, c);
                if (adjacentC > -1) {
                    adjacentAliveTile = board[r][adjacentC];
                } else {
                    if (c < rightmostC) {
                        moveTile(r, r, c, rightmostC, currentTile.number);
                        movePossible = true;
                    }
                    break;
                }
                if (currentTile.number == adjacentAliveTile.number) {
                    currentTile.number *= 2;
                    checkBiggest(currentTile.number);
                    if (c < rightmostC) {
                        moveTile(r, r, c, rightmostC, currentTile.number);
                    }
                    movePossible = true;
                    board[r][adjacentC].state = TileState.DEAD;
                    rightmostC--;
                    c = adjacentC - 1;
                } else {
                    if (c < rightmostC) {
                        moveTile(r, r, c, rightmostC, currentTile.number);
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

    private int findAdjacentRight(int r, int c) {
        int adjacentC = c - 1;
        while (adjacentC > -1) {
            if (board[r][adjacentC].isDead()) {
                adjacentC--;
            } else {
                return adjacentC;
            }
        }
        return adjacentC;
    }

    public Tile getTile(int r, int c) {
        return board[r][c];
    }

    public int getBoardLen() {
        return boardLen;
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public boolean getHasLost() {
        return hasLost;
    }

    public void endGame() {
        System.exit(0);
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

    public void setHasWon() {
        hasWon = true;
    }

    public void setHasLost() {
        hasLost = true;
    }

}
