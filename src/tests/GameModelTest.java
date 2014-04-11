package tests;

import model.GameModel;
import model.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by chinana523 on 4/9/14.
 */
public class GameModelTest {
    GameModel model;

    @Before
    public void initGameModel() {
        model = new GameModel(2048, true);
    }

    @Test
    public void testUp() {

    }

    @Test
    public void testDownOneTileInOneCol() {
        model.dropTile(0, 0, 2);

        model.down();

        assertTrue(model.getTile(3, 0).isAlive());
        assertEquals(2, model.getTile(3, 0).getNumber());
    }

    @Test
    public void testDownTwoTilesInOneCol() {
        model.dropTile(0, 0, 2);
        model.dropTile(1, 0, 2);
        assertTrue(model.getTile(0, 0).isAlive());
        assertTrue(model.getTile(1, 0).isAlive());

        model.down();

        assertTrue(model.getTile(3, 0).isAlive());
        assertEquals(4, model.getTile(3, 0).getNumber());
    }

    @Test
    public void testLeft() {

    }

    @Test
    public void testRight() {

    }

    @Test
    public void testMoveIsPossible() {
        int i = 0;
        for (int r = 0; r < model.getBoardLen(); r++) {
            for (int c = 0; c < model.getBoardLen(); c++) {
                model.dropTile(r, c, i);
                i++;
            }
        }
        assertFalse(model.isMovePossible());

        model.dropTile(0, 1, 0);
        assertTrue(model.isMovePossible());

        model.dropTile(0, 1, 1);
        assertFalse(model.isMovePossible());

        model.dropTile(1, 0, 0);
        assertTrue(model.isMovePossible());

        model.dropTile(1, 0, 4);
        assertFalse(model.isMovePossible());

        model.dropTile();
        assertTrue(model.getHasLost());
    }
}
