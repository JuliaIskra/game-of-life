package julia.nemtseva.game.of.life;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static julia.nemtseva.game.of.life.CellState.ALIVE;
import static julia.nemtseva.game.of.life.CellState.DEAD;

/**
 * @author Julia Nemtseva
 */
public class Grid {
    private final int size;
    private final CellState[] array;

    public Grid(int size) {
        this.size = size;
        array = new CellState[size * size];
        Arrays.fill(array, DEAD);
    }

    /**
     * Marks cells in given coordinates as alive
     */
    public void markAsAlive(Coordinates... coordinates) {
        for (Coordinates c : coordinates) {
            Array.set(array, calculateIndex(c), ALIVE);
        }
    }

    public CellState get(Coordinates c) {
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
            Array.set(newGrid.array, index, newState);
        }
        return newGrid;
    }

    private CellState evaluateNextState(int index) {
        CellState cell = array[index];
        int aliveNeighbours = calculateAliveNeighbours(index);
        if (cell.equals(ALIVE)) {
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
        List<CellState> neighbours = getNeighbours(index);
        for (CellState neighbour : neighbours) {
            if (neighbour.equals(ALIVE)) {
                aliveNeighbours++;
            }
        }
        return aliveNeighbours;
    }

    private List<CellState> getNeighbours(int index) {
        Coordinates c = calculateCoordinates(index);
        int x = c.getX();
        int y = c.getY();

        return Arrays.asList(
                get(x, increaseCoordinate(y)),
                get(increaseCoordinate(x), increaseCoordinate(y)),
                get(increaseCoordinate(x), y),
                get(increaseCoordinate(x), decreaseCoordinate(y)),
                get(x, decreaseCoordinate(y)),
                get(decreaseCoordinate(x), decreaseCoordinate(y)),
                get(decreaseCoordinate(x), y),
                get(decreaseCoordinate(x), increaseCoordinate(y)));
    }

    private CellState get(int x, int y) {
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
        return c.getY() * size + c.getX();
    }

    private Coordinates calculateCoordinates(int index) {
        int x = index % size;
        int y = index / size;
        return new Coordinates(x, y);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int index = 0;
        while (index < array.length) {
            for (int x = 0; x < size; x++) {
                output.append(array[index]).append(" ");
                index++;
            }
            output.append("\n");
        }
        return output.toString();
    }
}
