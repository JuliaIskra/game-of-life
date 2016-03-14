import java.util.Arrays;
import java.util.List;
import julia.nemtseva.game.of.life.Coordinates;
import julia.nemtseva.game.of.life.Grid;
import org.junit.Test;

import static julia.nemtseva.game.of.life.CellState.ALIVE;
import static julia.nemtseva.game.of.life.CellState.DEAD;
import static org.junit.Assert.assertEquals;

/**
 * @author Julia Nemtseva
 */
public class GridTest {
    private Grid grid = new Grid(5);

    @Test
    public void testUnderPopulation() {
        //**-*-
        List<Coordinates> coordinates = Arrays.asList(
                new Coordinates(0, 0),
                new Coordinates(0, 1),
                new Coordinates(0, 3));
        grid.markAsAlive(coordinates.toArray(new Coordinates[coordinates.size()]));
        Grid newGrid = grid.tick();
        for (Coordinates c : coordinates) {
            assertEquals(DEAD, newGrid.get(c));
        }
    }

    @Test
    public void testNextGeneration() {
        //**---
        //*----
        //-----
        //---**
        //---**
        grid.markAsAlive(
                new Coordinates(0, 0),
                new Coordinates(0, 1),
                new Coordinates(1, 0),
                new Coordinates(3, 3),
                new Coordinates(3, 4),
                new Coordinates(4, 3),
                new Coordinates(4, 4));
        Grid newGrid = grid.tick();
        assertEquals(ALIVE, newGrid.get(new Coordinates(0, 0)));
        assertEquals(ALIVE, newGrid.get(new Coordinates(3, 3)));
    }

    @Test
    public void testOverPopulation() {
        //***
        //**
        grid.markAsAlive(
                new Coordinates(1, 1),
                new Coordinates(1, 2),
                new Coordinates(1, 3),
                new Coordinates(2, 1),
                new Coordinates(2, 2));
        Grid newGrid = grid.tick();
        assertEquals(DEAD, newGrid.get(new Coordinates(2, 2)));
    }

    @Test
    public void testReproduction() {
        //**
        //*
        grid.markAsAlive(
                new Coordinates(1, 1),
                new Coordinates(1, 2),
                new Coordinates(2, 1));
        Grid newGrid = grid.tick();
        assertEquals(ALIVE, newGrid.get(new Coordinates(2, 2)));
    }
}
