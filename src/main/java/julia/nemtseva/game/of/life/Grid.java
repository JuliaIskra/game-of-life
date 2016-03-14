package julia.nemtseva.game.of.life;

import java.lang.reflect.Array;
import java.util.Arrays;

import static julia.nemtseva.game.of.life.CellState.ALIVE;
import static julia.nemtseva.game.of.life.CellState.DEAD;

/**
 * @author Julia Nemtseva
 */
public class Grid {
    // todo remove class Cell and use CellState instead?
    private final int size;
    private final Cell[] array;

    public Grid(int size) {
        this.size = size;
        array = new Cell[size * size];
        Arrays.fill(array, new Cell());
    }

    /**
     * Marks cells in given coordinates as alive
     */
    public void markAsAlive(Coordinates... coordinates) {
        for (Coordinates c : coordinates) {
            Array.set(array, calculateIndex(c), new Cell(ALIVE));
        }
    }

    public Cell get(Coordinates c) {
        return array[calculateIndex(c)];
    }

    /**
     * Changes states of all cells in the grid and returns a new grid with a new
     * state.
     */
    public Grid tick() {
        Grid newGrid = new Grid(size);
        for (int index = 0; index < array.length; index++) {
            CellState newState = evaluateNextState(index);
            Array.set(newGrid.array, index, new Cell(newState));
        }
        return newGrid;
    }

    private CellState evaluateNextState(int index) {
        Cell cell = array[index];
        int aliveNeighbours = calculateAliveNeighbours(index);
        if (cell.getState().equals(ALIVE)) {
            if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                return DEAD;
            } else {
                return ALIVE;
            }
        } else {
            if (aliveNeighbours == 3) {
                return ALIVE;
            } else {
                return DEAD;
            }
        }
    }

    private int calculateAliveNeighbours(int index) {
        int aliveNeighbours = 0;
        Cell[] neighbours = getNeighbours(index);
        for (Cell neighbour : neighbours) {
            if (neighbour.getState().equals(ALIVE)) {
                aliveNeighbours++;
            }
        }
        return aliveNeighbours;
    }

    private Cell[] getNeighbours(int index) {
        Coordinates c = calculateCoordinates(index);
        int x = c.getX();
        int y = c.getY();

        Cell[] neighbours = new Cell[8];
        neighbours[0] = get(x, increaseCoordinate(y));
        neighbours[1] = get(increaseCoordinate(x), increaseCoordinate(y));
        neighbours[2] = get(increaseCoordinate(x), y);
        neighbours[3] = get(increaseCoordinate(x), decreaseCoordinate(y));
        neighbours[4] = get(x, decreaseCoordinate(y));
        neighbours[5] = get(decreaseCoordinate(x), decreaseCoordinate(y));
        neighbours[6] = get(decreaseCoordinate(x), y);
        neighbours[7] = get(decreaseCoordinate(x), increaseCoordinate(y));
        return neighbours;
    }

    private Cell get(int x, int y) {
        return get(new Coordinates(x, y));
    }

    /**
     * Increases coordinate cycling over boundary
     */
    private int increaseCoordinate(int c) {
        if (c == size - 1) {
            return 0;
        } else {
            return c + 1;
        }
    }

    /**
     * Decreases coordinate cycling over boundary
     */
    private int decreaseCoordinate(int c) {
        if (c == 0) {
            return size - 1;
        } else {
            return c - 1;
        }
    }

    private int calculateIndex(Coordinates c) {
        return c.getX() * size + c.getY();
    }

    private Coordinates calculateCoordinates(int index) {
        int x = index / size;
        int y = index % size;
        return new Coordinates(x, y);
    }
}
